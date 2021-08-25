package io.cqrs.core.event;

import io.cqrs.core.exceptions.RegistryCollisionException;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * One common problem when working with events: how to know which class to hydrate the event data into when loading the
 * events out of your data store.
 *
 * A typical implementation is where the event data exists as a row (or equivalent) in some datastore, with a field
 * representing which class / object to cast the data into. With java, this could be the .getClass().getName().
 * However, then one needs to cast the string into a Class using a classLoader. Or, what happens if the name of an event
 * file changes over time?
 *
 * So, for convenience, this library provides this class, the EventRegistry. It is meant to act as a mapping of
 * String key to Class, and one copy (Singleton) should exist. It can scan your classpath (run this on startup of your
 * application) searching for subclasses of {@link Event}. In addition, if your event class file changes over time
 * (e.g. typo discovered after launch, package moves, more meaningful name chosen), one can use the {@link EventAlias}
 * annotation to mark it as such automatically.
 *
 */
public class EventRegistry {

    private final Map<String, Class<? extends Event>> aliasToClassLookup = new HashMap<>();
    private final Map<Class<? extends Event>, List<String>> classToAliasLookup = new HashMap<>();

    /**
     * Scans the classpath for files of subclass {@link Event}. By default it will use the 'shortname' of the class.
     * Will raise an exception on key collision, so keep event aliases unique.
     *
     * Be very wary on updating the key/alias for an event: Your app will only be aware of the current state of all
     * aliases, while your system will hold the historical record; meaning that one could 'swap' event aliases and break
     * the system.
     *
     * One thing to consider in the future is an EventAliasProvider, sourced from some datastore.
     *
     * @throws RegistryCollisionException if an alias or name collides - no duplicates!
     * @throws InvalidObjectException unable to scan classpath for Events or the path is missing
     */
    public void scan(String packageName) throws RegistryCollisionException, InvalidObjectException {
        findEventClassesInPackageHierarchy(packageName.replaceAll("[.]", "/"))
                .forEach( eventClass -> {
                    addToAliasLookup(eventClass);
                    addToClassLookup(eventClass);
                });
    }

    private Set<Class<? extends Event>> findEventClassesInPackageHierarchy(@Nonnull String packagePath) {
        return new Reflections(packagePath).getSubTypesOf(Event.class);
    }

    private void ifEventClass(String packageName, String className, Consumer<Class<? extends Event>> handler) {
        String path = packageName.replaceAll("/", ".") + "."
                + className.substring(0, className.lastIndexOf('.'));
        try {
            // load class, stripping out the actual 'class text'
            Class<?> loadedClass = Class.forName(path);
            if (Event.class.isAssignableFrom(loadedClass)) {
                handler.accept((Class<? extends Event>) loadedClass);
            }
        } catch (ClassNotFoundException e) {
            // throw?
        }
    }


    private void addToClassLookup(Class<? extends Event> eClass) throws RegistryCollisionException {

        String alias = calculateAlias(eClass);
        if (aliasToClassLookup.containsKey(alias)) {
            throw  new RegistryCollisionException("There is an already a Class entry for alias " + alias);
        }
        aliasToClassLookup.put(alias, eClass);
    }

    private String calculateAlias(Class<? extends Event> eClass) {
        if (eClass.isAnnotationPresent(EventAlias.class)) {
            return eClass.getAnnotation(EventAlias.class).alias();
        } else {
            return eClass.getSimpleName();
        }
    }

    private void addToAliasLookup(Class<? extends Event> eClass) {
        List<String> aliases = new ArrayList<>();
        aliases.add(calculateAlias(eClass));
        classToAliasLookup.put(eClass, aliases);
    }



    /**
     *
     * @param eventClass
     * @return Event Class, if found
     */
    public Optional<String> aliasForEventClass(@Nonnull Class<? extends Event> eventClass) {
        if (!classToAliasLookup.containsKey(eventClass)) {
            return Optional.empty();
        } else {
            return classToAliasLookup.get(eventClass).stream().findFirst();
        }
    }

    /**
     *
     * @param alias
     * @return return first alias to use for the event, if found
     */
    public Optional<Class<? extends Event>> classFromAlias(@Nonnull String alias) {
        return Optional.ofNullable(aliasToClassLookup.get(alias));
    }
}
