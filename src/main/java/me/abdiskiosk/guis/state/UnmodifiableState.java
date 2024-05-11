package me.abdiskiosk.guis.state;

import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class UnmodifiableState<T> implements State<T> {

    private final T value;

    public UnmodifiableState(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void set(T value) {
        throw new UnsupportedOperationException("Cannot set value of an unmodifiable state");
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Cannot update an unmodifiable state");
    }

    @Override
    public void subscribe(@NotNull Consumer<T> subscriber) {
        subscriber.accept(value);
    }

    @Override
    public void subscribe(@NotNull BiConsumer<T, T> subscriber) {
        subscriber.accept(value, value);
    }

    @Override
    public void unsubscribe(@NotNull Consumer<T> subscriber) {
        throw new UnsupportedOperationException("Cannot unsubscribe from an unmodifiable state");
    }
}
