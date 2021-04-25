package io.cqrs.core.furniture.sofa;

import io.cqrs.core.process.AggregateMutationResult;
import io.cqrs.core.process.CommandHandlingResult;
import io.cqrs.core.event.EventFactory;
import io.cqrs.core.event.EventRepository;
import io.cqrs.core.furniture.commands.AddLegs;
import io.cqrs.core.furniture.commands.CreateNewSofa;
import io.cqrs.core.process.CommandProcessor;

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
        return new CommandProcessor<>(eventRepository, command)
                .handle(new Sofa(new SofaId(command.getSku())), sofa ->
                    sofa.assemble(
                            command.getDescription(),
                            command.getNumLegs(),
                            command.getNumSeats()
                    )
                );
//        Sofa sofa = new Sofa(new SofaId(command.getSku()))
//                .loadCurrentState(eventRepository);
//        AggregateMutationResult<Sofa> result = sofa.assemble(
//                command.getDescription(),
//                command.getNumLegs(),
//                command.getNumSeats()
//        );
//        if (result.maybeError().isPresent()) {
//            return new CommandHandlingResult<>(result.maybeError().get());
//        }
//
//        EventFactory<CreateNewSofa, Sofa> eventFactory = new EventFactory<>(command,
//                result.getCapturedAggregate());
//        result.getUncommittedEvents().forEach(eventFactory::addNext);
//        eventRepository.write(eventFactory.getEventEnvelopes());
//        return new CommandHandlingResult<>(sofa, new ArrayList<>());
    }

    public CommandHandlingResult<Sofa> addLegsToSofa(final SofaId sofaId, final AddLegs command ) {
        return new CommandProcessor<>(eventRepository, command)
                .handle(new Sofa(sofaId), sofa ->
                        sofa.addMoreLegs(command.getRequestedLegs())
                );
//        Sofa sofa = new Sofa(sofaId)
//                .loadCurrentState(eventRepository);
//        AggregateMutationResult<Sofa> result = sofa.addMoreLegs(
//                command.getRequestedLegs()
//        );
//        if (result.maybeError().isPresent()) {
//            return new CommandHandlingResult<>(result.maybeError().get());
//        }
//
//        EventFactory<AddLegs, Sofa> eventFactory = new EventFactory<>(command, sofa);
//        result.getUncommittedEvents().forEach(eventFactory::addNext);
//        eventRepository.write(eventFactory.getEventEnvelopes());
//        return new CommandHandlingResult<>(sofa, new ArrayList<>());
    }


}
