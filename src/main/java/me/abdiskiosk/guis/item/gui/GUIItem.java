package me.abdiskiosk.guis.item.gui;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class GUIItem {

    @NotNull
    @Getter
    private final ItemStack item;
    @NotNull
    @Getter
    private final Collection<Integer> slots;

    public GUIItem(@NotNull ItemStack item, @NotNull Collection<Integer> slots) {
        this.item = item;
        this.slots = slots;
    }

}