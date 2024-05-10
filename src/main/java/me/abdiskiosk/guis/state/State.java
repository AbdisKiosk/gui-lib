package me.abdiskiosk.guis.state;

import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface State<T> {

    T get();

    void set(T value);

    void update();

    void subscribe(@NotNull Consumer<T> subscriber);
    void subscribe(@NotNull BiConsumer<T, T> subscriber);

    void unsubscribe(@NotNull Consumer<T> subscriber);

    static <T> @NotNull State<T> of(T value) {
        return new DefaultState<>(value);
    }

}