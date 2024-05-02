package me.abdiskiosk.guis.event.click;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class InventoryClickEvent {

    @Getter
    private final int slot;
    @Getter
    @NotNull
    private final ItemStack clicked;
    @Getter
    @NotNull
    private Collection<@NotNull Integer> slots;
    
}
