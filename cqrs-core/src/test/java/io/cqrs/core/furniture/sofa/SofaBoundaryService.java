package io.cqrs.core.furniture.sofa;

import io.cqrs.core.CommandHandlingResult;
import io.cqrs.core.event.EventFactory;
import io.cqrs.core.furniture.commands.CreateNewSofa;

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

    public CommandHandlingResult<Sofa> createNewSofa(final CreateNewSofa command ) {
        // are we allowed to create a new Sofa? do we have enough room in the house?
        // assume yes!
        // but we also cannot create a sofa if it has events against it
//        if (false) {
//            return new CommandHandlingResult<>(new RuntimeException("Can not be bare"));
//        }
//        // Use the EventFactory to make creating new events easier.
//
//        EventFactory<CreateNewSofa, SofaId, Sofa> factory = new EventFactory<>(command, root);
//        factory.addNext(new EntityCreated())
//                .addNext(new LegsAdded(command.getNumLegs()));
//        for (int i =0; i < command.getNumSeats(); i++) {
//            factory.addNext(new SeatsAdded());
//        }
//        return factory.toUncommittedEventsResult();
        return new CommandHandlingResult<>(new RuntimeException("Whoops"));
    }


}
