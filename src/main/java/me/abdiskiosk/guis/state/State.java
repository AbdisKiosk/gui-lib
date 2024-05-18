package me.abdiskiosk.guis.state;

import me.abdiskiosk.guis.state.objects.IntState;
import me.abdiskiosk.guis.state.objects.WeakPlayerState;
import org.bukkit.entity.Player;
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

    static WeakPlayerState of(@NotNull Player player) {
        return new WeakPlayerState(player);
    }

    static IntState of(int value) {
        return new IntState(value);
    }

    static <T> StaticNamedState<T> immutable(T value, @NotNull String name) {
        return new StaticNamedState<>(of(value), name);
    }

    static <T> void subscribe(@NotNull State<T> state, @NotNull Consumer<T> subscriber) {
        state.subscribe(subscriber);
    }

    static <T1, T2> void subscribe(@NotNull State<T1> state1, @NotNull State<T2> state2, @NotNull BiConsumer<T1, T2> subscriber) {
        state1.subscribe(value -> subscriber.accept(value, state2.get()));
        state2.subscribe(value -> subscriber.accept(state1.get(), value));
    }

}