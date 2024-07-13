package me.abdiskiosk.guis.reflection;

import lombok.SneakyThrows;
import me.abdiskiosk.guis.state.NamedState;
import me.abdiskiosk.guis.state.State;
import me.abdiskiosk.guis.state.UnmodifiableState;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class StateFinder {

    public static @NotNull Set<@NotNull NamedState<?>> findStates(@NotNull Object object) {
        Set<NamedState<?>> states = new HashSet<>();
        for(Field field : getFields(object.getClass())) {
            try {
                states.add(new NamedState<>(stateOf(field, object), findName(field)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return states;
    }

    protected static @NotNull State<?> stateOf(@NotNull Field field, @NotNull Object object) throws IllegalAccessException {
        field.setAccessible(true);
        Object value = field.get(object);
        if(value.getClass().isAssignableFrom(State.class)) {
            return (State<?>) value;
        }
        return new UnmodifiableState<>(value);
    }

    protected static @NotNull Set<@NotNull Field> getFields(@NotNull Class<?> clazz) {
        Set<Field> fields = new HashSet<>();
        Class<?> currentClass = clazz;
        while (currentClass != null) {
            for(Field field : currentClass.getDeclaredFields()) {
                if(field.isAnnotationPresent(Placeholder.class)) {
                    field.setAccessible(true);
                    fields.add(field);
                }
            }
            currentClass = currentClass.getSuperclass();
        }

        return fields;
    }

    protected static @NotNull String findName(@NotNull Field field) {
        Placeholder placeholder = field.getAnnotation(Placeholder.class);
        if(placeholder == null) {
            return field.getName();
        }
        if(placeholder.name().isEmpty()) {
            return field.getName();
        }
        return placeholder.name();
    }
}
