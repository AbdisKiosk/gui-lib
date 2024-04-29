package me.abdiskiosk.guis.component;

import me.abdiskiosk.guis.state.State;

import java.util.Collection;

public interface Component<T> {

    T create(Collection<State<?>> states);

    boolean isUsed(State<?> state);

}