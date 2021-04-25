package io.cqrs.core.process;

import io.cqrs.core.Aggregate;
import io.cqrs.core.CqrsCommand;
import io.cqrs.core.event.EventFactory;
import io.cqrs.core.event.EventRepository;
import io.cqrs.core.identifiers.EntityId;
import io.cqrs.core.identifiers.UserId;

import java.util.ArrayList;
import java.util.function.Function;

public class CommandProcessor<C extends CqrsCommand<? extends UserId<?>>> {

    private final EventRepository eventRepository;
    private final C capturedCommand;

    public CommandProcessor(final EventRepository eventRepository, final C command) {
        this.eventRepository = eventRepository;
        this.capturedCommand = command;
    }

    public <A extends Aggregate<? extends EntityId<?>>> CommandHandlingResult<A> handle(
            final A aggregate,
            final Function<A, AggregateMutationResult<A>> logic
    ) {
        aggregate.loadCurrentState(eventRepository);
        AggregateMutationResult<A> result = logic.apply(aggregate);
        if (result.maybeError().isPresent()) {
            return new CommandHandlingResult<>(result.maybeError().get());
        } else {
            EventFactory<C, A> eventFactory = new EventFactory<>(capturedCommand, aggregate);
            result.getUncommittedEvents().forEach(eventFactory::addNext);
            eventRepository.write(eventFactory.getEventEnvelopes());
            return new CommandHandlingResult<>(aggregate, new ArrayList<>());
        }
    }
}
