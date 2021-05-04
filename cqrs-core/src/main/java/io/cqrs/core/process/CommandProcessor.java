package io.cqrs.core.process;

import io.cqrs.core.Aggregate;
import io.cqrs.core.CqrsCommand;
import io.cqrs.core.event.Event;
import io.cqrs.core.event.EventEnvelope;
import io.cqrs.core.event.EventFactory;
import io.cqrs.core.event.EventRepository;
import io.cqrs.core.identifiers.EntityId;
import io.cqrs.core.identifiers.UserId;


import java.util.List;
import java.util.function.Function;

public class CommandProcessor<C extends CqrsCommand<? extends UserId<?>>> {

    private final EventRepository eventRepository;
    private final C capturedCommand;

    public CommandProcessor(final EventRepository eventRepository, final C command) {
        this.eventRepository = eventRepository;
        this.capturedCommand = command;
    }

    // processor loads the Aggregate current state, passes currentState aggs to the handler
    // collects responses, generates EventEnvelopes for the command and saves
    // if any of the AggregateMutationResults have an error, use that instead

    public <A extends Aggregate<?>> CommandHandlingResult<A> handle(
            final A aggregate,
            final Function<A, AggregateMutationResult<A>> logic
    ) {
        aggregate.loadCurrentState(eventRepository);
        AggregateMutationResult<A> result = logic.apply(aggregate);

        if (result.maybeError().isPresent()) {
            return new CommandError<>(result.maybeError().get());
        } else {
            eventRepository.write(generateEnvelopes(result));
            return new CommandSuccess<>(aggregate);
        }
    }


    private <BUNDLE> CommandHandlingResult<BUNDLE> processResults(
        Iterable<AggregateMutationResult<?>> results,
        Function<Iterable<AggregateMutationResult<?>>, BUNDLE> bundler ) {

        return new CommandError<>(new RuntimeException("Not yet"));
    }

    private <A extends Aggregate<?>> List<EventEnvelope<? extends Event, ? extends EntityId<?>>> generateEnvelopes(
        AggregateMutationResult<A> aggregateMutationResult
    ) {
        EventFactory<C, A> eventFactory = new EventFactory<>(capturedCommand, aggregateMutationResult.getCapturedAggregate());
        aggregateMutationResult.getUncommittedEvents().forEach(eventFactory::addNext);
        return eventFactory.getEventEnvelopes();
    }


}
