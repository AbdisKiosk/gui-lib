package me.abdiskiosk.guis.state;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    public void set(T value) {
        T oldValue;
        List<BiConsumer<T, T>> biSubsCopy;
        List<Consumer<T>> subsCopy;
        synchronized (this) {
            oldValue = this.value;
            this.value = value;
            biSubsCopy = new ArrayList<>(biSubscribers);
            subsCopy = new ArrayList<>(subscribers);
        }
        for (BiConsumer<T, T> subscriber : biSubsCopy) {
            subscriber.accept(oldValue, value);
        }
        subsCopy.forEach(subscriber -> subscriber.accept(value));
    }

    @Override
    public void update() {
        T currentValue;
        List<Consumer<T>> subsCopy;
        synchronized (this) {
            currentValue = this.value;
            subsCopy = new ArrayList<>(subscribers);
        }
        subsCopy.forEach(subscriber -> subscriber.accept(currentValue));
    }

    @Override
    public void subscribe(@NotNull Consumer<T> subscriber) {
        T currentValue;
        synchronized (this) {
            subscribers.add(subscriber);
            currentValue = this.value;
        }
        subscriber.accept(currentValue);
    }

    @Override
    public void subscribe(@NotNull BiConsumer<T, T> subscriber) {
        T currentValue;
        synchronized (this) {
            biSubscribers.add(subscriber);
            currentValue = this.value;
        }
        subscriber.accept(currentValue, currentValue);
    }

    @Override
    public synchronized void unsubscribe(@NotNull Consumer<T> subscriber) {
        subscribers.remove(subscriber);
    }

}