package me.abdiskiosk.guis.state;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DefaultState<T> implements State<T> {

    private T value;

    private final Collection<@NotNull Consumer<T>> subscribers = new ArrayList<>();
    private final Collection<@NotNull BiConsumer<T, T>> biSubscribers = new ArrayList<>();

    public DefaultState(T value) {
        this.value = value;
    }

    @Override
    public synchronized T get() {
        return value;
    }

    @Override
    public synchronized void set(T value) {
        T oldValue = this.value;
        for(BiConsumer<T, T> subscriber : biSubscribers) {
            subscriber.accept(oldValue, value);
        }
        this.value = value;
        update();
    }

    @Override
    public synchronized void update() {
        subscribers.forEach(subscriber -> subscriber.accept(value));
    }

    @Override
    public synchronized void subscribe(@NotNull Consumer<T> subscriber) {
        subscribers.add(subscriber);
        subscriber.accept(value);
    }

    @Override
    public void subscribe(@NotNull BiConsumer<T, T> subscriber) {
        biSubscribers.add(subscriber);
        subscriber.accept(value, value);
    }

    @Override
    public synchronized void unsubscribe(@NotNull Consumer<T> subscriber) {
        subscribers.remove(subscriber);
    }

}