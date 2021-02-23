/**
 * Some rules:
 * Entities - > have a lifecycle but not necessarily direct events
 * AggregateRoot -> comprises one or Entities. Commands applied to book, and returns events
 * Events -> attached to an AggregateRoot
 *
 * Command Handler -> akin to a service
 *
 * How to grow AggregateRoot boundaries? Aggregates can comprise other book
 *
 * Entities have an id... but book have the root id as their id. all events are keyed to the Root id, and their own
 */
package io.cqrs.core;
