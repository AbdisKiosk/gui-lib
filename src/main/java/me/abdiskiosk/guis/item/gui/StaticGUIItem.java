package me.abdiskiosk.guis.item.gui;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Supplier;

public class StaticGUIItem implements Supplier<GUIItem> {

    protected final Supplier<ItemStack> item;
    protected final Supplier<Collection<Integer>> slots;

    public StaticGUIItem(@NotNull Supplier<@NotNull ItemStack> item,
                         @NotNull Supplier<@NotNull Collection<@NotNull Integer>> slots) {
        this.item = item;
        this.slots = slots;
    }

    @Override
    public GUIItem get() {
        return new GUIItem(item.get(), slots.get());
    }
}