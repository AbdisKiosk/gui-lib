package me.abdiskiosk.guis.item;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

public class GUIItem {

    @NotNull
    @Getter
    private final ItemStack item;
    @NotNull
    @Getter
    private final Collection<Integer> slots;

    public GUIItem(@NotNull Collection<Integer> slots, @NotNull ItemStack item) {
        this.item = item.clone();
        this.slots = slots;
    }

    public GUIItem(int slot, @NotNull ItemStack item) {
        this.item = item.clone();
        this.slots = Collections.singleton(slot);
    }

    public GUIItem(int slot, @NotNull ItemBuilder builder) {
        this.item = builder.build();
        this.slots = Collections.singleton(slot);
    }

    public GUIItem(int row, int slot, @NotNull ItemStack item) {
        this.item = item.clone();
        this.slots = Collections.singleton(row * 9 + slot);
    }

}