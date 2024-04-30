package me.abdiskiosk.guis.item.gui;

import me.abdiskiosk.guis.component.Component;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class StaticGUIItem implements Component<GUIItem> {

    protected final Component<ItemStack> item;
    protected final Component<Collection<Integer>> slots;

    public StaticGUIItem(@NotNull Component<@NotNull ItemStack> item,
                         @NotNull Component<@NotNull Collection<@NotNull Integer>> slots) {
        this.item = item;
        this.slots = slots;
    }

    @Override
    public GUIItem create() {
        return new GUIItem(item.create(), slots.create());
    }
}