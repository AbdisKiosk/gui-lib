package me.abdiskiosk.guis.state.objects;

import me.abdiskiosk.guis.state.DefaultState;
import me.abdiskiosk.guis.state.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MapState<K, V> implements State<Map<K, V>>, Map<K, V> {

    private final State<Map<K, V>> state;

    public MapState(Map<K, V> value) {
        this.state = new DefaultState<>(new HashMap<>());
    }

    @Override
    public Map<K, V> get() {
        return state.get();
    }

    @Override
    public void set(Map<K, V> value) {
        state.set(value);
    }

    @Override
    public void update() {
        state.update();
    }

    @Override
    public void subscribe(@NotNull Consumer<Map<K, V>> subscriber) {
        state.subscribe(subscriber);
    }

    @Override
    public void subscribe(@NotNull BiConsumer<Map<K, V>, Map<K, V>> subscriber) {
        state.subscribe(subscriber);
    }

    @Override
    public void unsubscribe(@NotNull Consumer<Map<K, V>> subscriber) {
        state.unsubscribe(subscriber);
    }

    @Override
    public int size() {
        return state.get().size();
    }

    @Override
    public boolean isEmpty() {
        return state.get().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return state.get().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return state.get().containsValue(value);
    }

    @Override
    public V get(Object key) {
        return state.get().get(key);
    }

    @Nullable
    @Override
    public V put(K key, V value) {
        try {
            return state.get().put(key, value);
        } finally {
            state.update();
        }
    }

    @Override
    public V remove(Object key) {
        try {
            return state.get().remove(key);
        } finally {
            state.update();
        }
    }

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends V> m) {
        try {
            state.get().putAll(m);
        } finally {
            state.update();
        }
    }

    @Override
    public void clear() {
        try {
            state.get().clear();
        } finally {
            state.update();
        }
    }

    @NotNull
    @Override
    public Set<K> keySet() {
        return state.get().keySet();
    }

    @NotNull
    @Override
    public Collection<V> values() {
        return state.get().values();
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return state.get().entrySet();
    }
}