package me.abdiskiosk.guis.item;

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

    public GUIItem(@NotNull Collection<Integer> slots, @NotNull ItemStack item) {
        this.item = item;
        this.slots = slots;
    }

}