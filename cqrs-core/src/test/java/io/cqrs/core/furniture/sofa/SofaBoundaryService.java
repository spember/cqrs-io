package io.cqrs.core.furniture.sofa;

import io.cqrs.core.process.CommandHandlingResult;
import io.cqrs.core.event.EventRepository;
import io.cqrs.core.furniture.commands.AddLegs;
import io.cqrs.core.furniture.commands.CreateNewSofa;
import io.cqrs.core.process.CommandProcessor;

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
        return new CommandProcessor<>(eventRepository, command)
                .handle(new Sofa(new SofaId(command.getSku())), sofa -> sofa.assemble(command));
    }

    public CommandHandlingResult<Sofa> addLegsToSofa(final SofaId sofaId, final AddLegs command ) {
        return new CommandProcessor<>(eventRepository, command)
                .handle(new Sofa(sofaId), sofa ->
                        sofa.addMoreLegs(command)
                );
    }


}
