package me.abdiskiosk.guis.util;

import me.abdiskiosk.guis.state.State;
import me.abdiskiosk.guis.state.StaticNamedState;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;

public class Placeholders {

    public static Collection<StaticNamedState<?>> of(StaticNamedState<?>... states) {
        return Arrays.asList(states);
    }

    public static Collection<StaticNamedState<?>> of(@NotNull String name1, @NotNull Object o1) {
        return of(State.immutable(o1, name1));
    }

    public static Collection<StaticNamedState<?>> of(@NotNull String name1, @NotNull Object o1,
                                                     @NotNull String name2, @NotNull Object o2) {
        return of(State.immutable(o1, name1), State.immutable(o2, name2));
    }

    public static Collection<StaticNamedState<?>> of(@NotNull String name1, @NotNull Object o1,
                                                     @NotNull String name2, @NotNull Object o2,
                                                     @NotNull String name3, @NotNull Object o3) {
        return of(State.immutable(o1, name1), State.immutable(o2, name2), State.immutable(o3, name3));
    }

    public static Collection<StaticNamedState<?>> of(@NotNull String name1, @NotNull Object o1,
                                                     @NotNull String name2, @NotNull Object o2,
                                                     @NotNull String name3, @NotNull Object o3,
                                                     @NotNull String name4, @NotNull Object o4) {
        return of(State.immutable(o1, name1), State.immutable(o2, name2), State.immutable(o3, name3), State.immutable(o4, name4));
    }

    public static Collection<StaticNamedState<?>> of(@NotNull String name1, @NotNull Object o1,
                                                     @NotNull String name2, @NotNull Object o2,
                                                     @NotNull String name3, @NotNull Object o3,
                                                     @NotNull String name4, @NotNull Object o4,
                                                     @NotNull String name5, @NotNull Object o5) {
        return of(State.immutable(o1, name1), State.immutable(o2, name2), State.immutable(o3, name3), State.immutable(o4, name4), State.immutable(o5, name5));
    }

    public static Collection<StaticNamedState<?>> of(@NotNull String name1, @NotNull Object o1,
                                                     @NotNull String name2, @NotNull Object o2,
                                                     @NotNull String name3, @NotNull Object o3,
                                                     @NotNull String name4, @NotNull Object o4,
                                                     @NotNull String name5, @NotNull Object o5,
                                                     @NotNull String name6, @NotNull Object o6) {
        return of(State.immutable(o1, name1), State.immutable(o2, name2), State.immutable(o3, name3), State.immutable(o4, name4), State.immutable(o5, name5), State.immutable(o6, name6));
    }
}