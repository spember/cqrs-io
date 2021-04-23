package io.cqrs.core.furniture.sofa;

import io.cqrs.core.AggregateMutationResult;
import io.cqrs.core.CommandHandlingResult;
import io.cqrs.core.event.EventFactory;
import io.cqrs.core.event.EventRepository;
import io.cqrs.core.furniture.commands.AddLegs;
import io.cqrs.core.furniture.commands.CreateNewSofa;

import java.util.ArrayList;

/**
 * Example implementation of a 'Boundary' Service to our Aggregate(s).
 *
 * Here it fronts the Sofa Aggregate, but it doesn't have to. One could imagine a whole
 * 'Furniture' Boundary that deals with different types of furniture aggregates, for example.
 *
 * The intent of these services is to sit at the 'edge' of your system and process commands, for example
 * this service class would be used by Http Controllers or Message Broker Handlers.
 */
public class SofaBoundaryService {

    private final EventRepository eventRepository;

    public SofaBoundaryService(final EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public CommandHandlingResult<Sofa> createNewSofa(final CreateNewSofa command ) {
        Sofa sofa = new Sofa(new SofaId(command.getSku()));
        sofa.loadCurrentState(eventRepository);
        AggregateMutationResult result = sofa.assemble(
                command.getDescription(),
                command.getNumLegs(),
                command.getNumSeats()
        );
        if (result.maybeError().isPresent()) {
            return new CommandHandlingResult<>(result.maybeError().get());
        }

        EventFactory<CreateNewSofa, SofaId, Sofa> eventFactory = new EventFactory<>(command, sofa);
        result.getUncommittedEvents().forEach(eventFactory::addNext);
        eventRepository.write(eventFactory.getEventEnvelopes());
        return new CommandHandlingResult<>(sofa, new ArrayList<>());
    }

    public CommandHandlingResult<Sofa> addLegsToSofa(final SofaId sofaId, final AddLegs command ) {
        Sofa sofa = new Sofa(sofaId);
        sofa.loadCurrentState(eventRepository);
        AggregateMutationResult result = sofa.addMoreLegs(
                command.getRequestedLegs()
        );
        if (result.maybeError().isPresent()) {
            return new CommandHandlingResult<>(result.maybeError().get());
        }

        EventFactory<AddLegs, SofaId, Sofa> eventFactory = new EventFactory<>(command, sofa);
        result.getUncommittedEvents().forEach(eventFactory::addNext);
        eventRepository.write(eventFactory.getEventEnvelopes());
        return new CommandHandlingResult<>(sofa, new ArrayList<>());
    }


}
