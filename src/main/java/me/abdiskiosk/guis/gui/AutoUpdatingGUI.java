package me.abdiskiosk.guis.gui;

import me.abdiskiosk.guis.gui.view.ListenerItemStack;
import me.abdiskiosk.guis.item.GUIItem;
import me.abdiskiosk.guis.placeholder.PlaceholderUtils;
import me.abdiskiosk.guis.reflection.StateFinder;
import me.abdiskiosk.guis.state.NamedState;
import me.abdiskiosk.guis.util.Scheduler;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class AutoUpdatingGUI extends GUI {

    private boolean registered = false;
    private @NotNull String name;
    private final @NotNull ConcurrentMap<@NotNull GUIItem, @NotNull Set<@NotNull String>> itemToUsedPlaceholders = new ConcurrentHashMap<>();

    public AutoUpdatingGUI(@NotNull String name, int sizeSlots) {
        super(name, sizeSlots);
        this.name = name;
    }

    @Override
    public void registerPlaceholders(@NotNull Collection<@NotNull NamedState<?>> states) {
        super.registerPlaceholders(states);

        for(NamedState<?> state : states) {
            state.subscribe(value -> onUpdate(state));
        }
    }

    @Override
    public void setName(@NotNull String name) {
        this.name = name;
        super.setName(name);
    }

    @Override
    public ListenerItemStack set(@NotNull GUIItem item) {
        Set<String> usedPlaceholders = PlaceholderUtils.getUsedPlaceholders(item.getItem());
        itemToUsedPlaceholders.put(item, new HashSet<>(usedPlaceholders));
        return super.set(item);
    }

    @Override
    public void remove(@NotNull GUIItem item) {
        itemToUsedPlaceholders.remove(item);
        super.remove(item);
    }

    protected void onUpdate(@NotNull NamedState<?> state) {
        Scheduler.sync(() -> {
            Set<GUIItem> toUpdate = getToUpdate(state);
            for (GUIItem item : toUpdate) {
                super.update(item);
            }

            if (shouldUpdateName(state)) {
                super.setName(name);
            }
        });
    }

    protected @NotNull Set<@NotNull GUIItem> getToUpdate(@NotNull NamedState<?> state) {
        return itemToUsedPlaceholders.entrySet().stream()
                .filter(entry -> entry.getValue().contains(state.getName()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    protected boolean shouldUpdateName(@NotNull NamedState<?> state) {
        return PlaceholderUtils.getUsedPlaceholders(name).contains(state.getName());
    }

    @Override
    public void open(@NotNull HumanEntity human) {
        if (!registered) {
            registerPlaceholders(StateFinder.findStates(this));
        }
        registered = true;
        super.open(human);
    }

}
