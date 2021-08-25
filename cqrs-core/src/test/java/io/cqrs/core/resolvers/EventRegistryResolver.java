package io.cqrs.core.resolvers;

import io.cqrs.core.event.EventRegistry;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.io.InvalidObjectException;

public class EventRegistryResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == EventRegistry.class;
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
        EventRegistry registry = new EventRegistry();
        try {
            registry.scan("io.cqrs.core.furniture");
        } catch (InvalidObjectException e) {
            e.printStackTrace();
        }
        return registry;
    }
}
