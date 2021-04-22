package io.cqrs.core.furniture.sofa;

import io.cqrs.core.Aggregate;
import io.cqrs.core.Command;
import io.cqrs.core.CommandHandlingResult;
import io.cqrs.core.event.EventFactory;
import io.cqrs.core.event.EventRepository;
import io.cqrs.core.furniture.commands.AddLegs;
import io.cqrs.core.furniture.commands.CreateNewSofa;

import javax.annotation.Nonnull;


public class SofaAggregate implements Aggregate {

    private final Sofa root;

    public SofaAggregate(final SofaId sofaId) {
        this.root = new Sofa(sofaId);
    }

    public Sofa getRoot() {
        return root;
    }

    @Nonnull
    @Override
    public SofaAggregate loadCurrentState(final EventRepository eventRepository) {
        eventRepository.listAllForEntity(root.getId())
                .forEach(root::apply);
        return this;
    }

    @Nonnull
    public CommandHandlingResult<Sofa> handle(final Command command) {
        if (command instanceof CreateNewSofa) {
            return createNewSofa((CreateNewSofa)command);
        } else if (command instanceof AddLegs) {
            return  addLegs((AddLegs)command);
        } else {
            return new CommandHandlingResult<>(new RuntimeException("No matching command"));
        }
    }

    public CommandHandlingResult<Sofa> createNewSofa(final CreateNewSofa command ) {
        // are we allowed to create a new Sofa? do we have enough room in the house?
        // assume yes!
        // but we also cannot create a sofa if it has events against it
        if (!getRoot().isBare()) {
            return new CommandHandlingResult<>(new RuntimeException("Can not be bare"));
        }
        // Use the EventFactory to make creating new events easier.

        EventFactory<CreateNewSofa, SofaId, Sofa> factory = new EventFactory<>(command, root);
        factory.addNext(new EntityCreated())
                .addNext(new LegsAdded(command.getNumLegs()));
        for (int i =0; i < command.getNumSeats(); i++) {
            factory.addNext(new SeatsAdded());
        }
        return factory.toUncommittedEventsResult();
    }

    public CommandHandlingResult<Sofa> addLegs(final AddLegs command) {
        if (getRoot().isBare()) {
            return new CommandHandlingResult<>(new RuntimeException("Cannot be applied on a bare sofa"));
        }
        //  A trivial check: ensure that legs are not odd. The idea is to show that a command should be
        // evaluated such that it doesn't put the Entity into an invalid state
        if ((getRoot().getNumLegs() + command.getRequestedLegs()) % 2 == 1) {
            return new CommandHandlingResult<>(new RuntimeException("Cannot have an odd number of legs!"));
        }
        return new EventFactory<>(command, root)
        .addNext(new LegsAdded(command.getRequestedLegs()))
        .toUncommittedEventsResult();
    }
}
