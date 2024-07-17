package me.abdiskiosk.guis.state;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class NamedState<T> implements State<T> {

    @NotNull
    private final State<T> state;
    @NotNull
    @Getter
    private final String name;

    public NamedState(@NotNull State<T> state, @NotNull String name) {
        this.state = state;
        this.name = name;
    }


    @Override
    public T get() {
        return state.get();
    }

    @Override
    public void set(T value) {
        state.set(value);
    }

    @Override
    public void update() {
        state.update();
    }

    @Override
    public void subscribe(@NotNull Consumer<T> subscriber) {
        state.subscribe(subscriber);
    }

    @Override
    public void subscribe(@NotNull BiConsumer<T, T> subscriber) {
        state.subscribe(subscriber);
    }

    @Override
    public void unsubscribe(@NotNull Consumer<T> subscriber) {
        state.unsubscribe(subscriber);
    }
}