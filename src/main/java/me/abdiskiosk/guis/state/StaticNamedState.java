package me.abdiskiosk.guis.state;

import org.jetbrains.annotations.NotNull;

public class StaticNamedState<T> extends NamedState<T> {

    public StaticNamedState(@NotNull State<T> state, @NotNull String name) {
        super(state, name);
    }

    @Override
    public void set(T value) {
        throw new UnsupportedOperationException("Cannot set value of a static state");
    }
}
