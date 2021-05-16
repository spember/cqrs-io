package io.cqrs.core.furniture.sofa;

import io.cqrs.core.Aggregate;
import io.cqrs.core.CqrsAggregate;
import io.cqrs.core.process.AggregateMutationResult;
import io.cqrs.core.process.CommandError;
import io.cqrs.core.process.CommandHandlingResult;
import io.cqrs.core.event.EventRepository;
import io.cqrs.core.furniture.commands.AddLegs;
import io.cqrs.core.furniture.commands.CreateNewSofa;
import io.cqrs.core.process.CommandSuccess;

import java.util.function.Function;

/**
 * Example implementation of a 'Boundary' Service to our Aggregate(s).
 *
 * Here it fronts the Sofa Aggregate, but it doesn't have to. One could imagine a whole
 * 'Furniture' Boundary that deals with different types of furniture aggregates, for example.
 *
 * The intent of these services is to sit at the 'edge' of your system and process commands, for example
 * this service class would be used by Http Controllers or Message Broker Handlers. It's main use case is to perform
 * necessary I/O - generally loading the current state of {@link Aggregate}(s) involved in the command and writing any events
 *  to the Journal / {@link EventRepository}. It is also the location for translating internal structures into
 *  structures suitable for outside consumption: for example you may have a public facing API that you should not change,
 *  though your internal objects may. These services would then be responsible for the translation.
 *
 */
public class SofaBoundaryService {

    private final EventRepository eventRepository;

    public SofaBoundaryService(final EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public CommandHandlingResult<Sofa> createNewSofa(final CreateNewSofa command ) {
        Sofa target = new Sofa(new SofaId(command.getSku()));
        // even though we're creating we should still load in order to see if we already have a sofa with this sku in our
        // database
        AggregateMutationResult<?, Sofa> result = target
            .loadCurrentState(eventRepository)
            .assemble(command);
        if (result.maybeError().isPresent()) {
            return new CommandError<>(result.maybeError().get());
        } else {
            eventRepository.write(result.getUncommittedEventEnvelopes());
            return new CommandSuccess<>(result.getCapturedAggregate());
        }
        // in the above block we lay out the general steps very pedantically, but for the most part this will be
        // repeated and boilerplate. We abstract it away in the other actions

    }

    public CommandHandlingResult<Sofa> addLegsToSofa(final SofaId sofaId, final AddLegs command ) {
        // without the repeated boilerplate, the actual wiring is pretty small. Can we go further?
        return processCommand(new Sofa(sofaId), sofa -> sofa.addMoreLegs(command));

    }

    private <A extends CqrsAggregate> CommandHandlingResult<A> processCommand(
        A aggregate,
        Function<A, AggregateMutationResult<?, A>> logic) {

        aggregate.loadCurrentState(eventRepository);
        AggregateMutationResult<?, A> result = logic.apply(aggregate);

        if (result.maybeError().isPresent()) {
            return new CommandError<>(result.maybeError().get());
        } else {
            eventRepository.write(result.getUncommittedEventEnvelopes());
            return new CommandSuccess<>(aggregate);
        }

    }
}
