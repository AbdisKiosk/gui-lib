package me.abdiskiosk.guis.gui;

import me.abdiskiosk.guis.gui.view.ListenerItemStack;
import me.abdiskiosk.guis.item.gui.GUIItem;
import me.abdiskiosk.guis.placeholder.PlaceholderUtils;
import me.abdiskiosk.guis.state.NamedState;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AutoUpdatingGUI extends GUI {


    private @NotNull String name;
    private final @NotNull Map<@NotNull GUIItem, @NotNull Set<@NotNull String>> itemToUsedPlaceholders = new HashMap<>();

    public AutoUpdatingGUI(@NotNull String name, int sizeSlots) {
        super(name, sizeSlots);
        this.name = name;
    }

    @Override
    public void registerPlaceholder(@NotNull NamedState<?> state) {
        state.subscribe(value -> onUpdate(state));
    }

    @Override
    public void setName(@NotNull String name) {
        this.name = name;
        super.setName(name);
    }

    @Override
    public synchronized ListenerItemStack set(@NotNull GUIItem item) {
        Set<String> usedPlaceholders = PlaceholderUtils.getUsedPlaceholders(item.getItem());
        itemToUsedPlaceholders.put(item, new HashSet<>(usedPlaceholders));
        return super.set(item);
    }

    @Override
    protected void remove(@NotNull GUIItem item) {
        itemToUsedPlaceholders.remove(item);
        super.remove(item);
    }

    protected void onUpdate(@NotNull NamedState<?> state) {
        Set<GUIItem> toUpdate = getToUpdate(state);
        for(GUIItem item : toUpdate) {
            super.set(item);
        }

        if(shouldUpdateName(state)) {
            super.setName(name);
        }
    }

    protected @NotNull Set<@NotNull GUIItem> getToUpdate(@NotNull NamedState<?> state) {
        return itemToUsedPlaceholders.entrySet().stream()
                .filter(entry -> entry.getValue().contains(state.getName()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    protected boolean shouldUpdateName(@NotNull NamedState<?> state) {
        return name.contains("{" + state.getName() + "}");
    }

}