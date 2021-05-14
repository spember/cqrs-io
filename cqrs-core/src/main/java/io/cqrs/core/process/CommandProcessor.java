package io.cqrs.core.process;

import io.cqrs.core.Aggregate;
import io.cqrs.core.CqrsCommand;
import io.cqrs.core.event.Event;
import io.cqrs.core.event.EventEnvelope;
import io.cqrs.core.event.EventFactory;
import io.cqrs.core.event.EventRepository;
import io.cqrs.core.identifiers.EntityId;
import io.cqrs.core.identifiers.UserId;


import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
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

    // this is all some straight-forward, boring code. On purpose

    /**
     *
     * @param aggregate
     * @param logic
     * @param <A>
     * @return
     */
    public <A extends Aggregate<?>> CommandHandlingResult<A> handle(
        @Nonnull final A aggregate,
        @Nonnull final Function<A, AggregateMutationResult<?, A>> logic
    ) {
        aggregate.loadCurrentState(eventRepository);
        AggregateMutationResult<?, A> result = logic.apply(aggregate);

        if (result.maybeError().isPresent()) {
            return new CommandError<>(result.maybeError().get());
        } else {
            eventRepository.write(result.getUncommittedEventEnvelopes());
            return new CommandSuccess<>(aggregate);
        }
    }

    /**
     *
     * @param first
     * @param second
     * @param logic
     * @param <A>
     * @param <B>
     * @return
     */
    public <A extends Aggregate<?>, B extends Aggregate<?>> CommandHandlingResult<List<? extends Aggregate<?>>> handle(
        @Nonnull final A first,
        @Nonnull final B second,
        @Nonnull final BiFunction<A, B, Iterable<AggregateMutationResult<?, A>>> logic
        ) {

        first.loadCurrentState(eventRepository);
        second.loadCurrentState(eventRepository);

        List<EventEnvelope<? extends Event, ? extends EntityId<?>>> eventEnvelopes = new ArrayList<>();
        for (AggregateMutationResult<?, A> result : logic.apply(first, second)) {
            if (result.maybeError().isPresent()) {
                return new CommandError<>(result.maybeError().get());
            }
            eventEnvelopes.addAll(result.getUncommittedEventEnvelopes());
        }
        eventRepository.write(eventEnvelopes);
        return new CommandSuccess<>(Arrays.asList(first, second));
    }

}
