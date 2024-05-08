package me.abdiskiosk.guis.state;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public class State<T> {

    private T value;

    private final Collection<@NotNull Consumer<T>> subscribers = new ArrayList<>();

    public State(T value) {
        this.value = value;
    }

    public synchronized T get() {
        return value;
    }

    public synchronized void set(T value) {
        this.value = value;
        update();
    }

    public synchronized void update() {
        subscribers.forEach(subscriber -> subscriber.accept(value));
    }

    public synchronized void subscribe(@NotNull Consumer<T> subscriber) {
        subscribers.add(subscriber);
        subscriber.accept(value);
    }

    public synchronized void unsubscribe(@NotNull Consumer<T> subscriber) {
        subscribers.remove(subscriber);
    }

}