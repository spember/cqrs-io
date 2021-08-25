package io.cqrs.core.failure;

import io.cqrs.core.event.Event;
import io.cqrs.core.event.EventAlias;

@EventAlias(alias = "failure_event")
public class FailureEvent1 implements Event {
}
