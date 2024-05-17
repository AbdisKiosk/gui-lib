package me.abdiskiosk.guis.pagination.item;

import lombok.Getter;
import me.abdiskiosk.guis.item.GUIItem;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class PaginationItem {

    @Getter
    @NotNull
    private final ItemStack item;
    @Getter
    @NotNull
    private final Consumer<InventoryClickEvent> clickListener;

    public PaginationItem(@NotNull ItemStack item) {
        this.item = item;
        this.clickListener = event -> {};
    }

    public PaginationItem(@NotNull ItemStack item, @NotNull Consumer<InventoryClickEvent> clickListener) {
        this.item = item;
        this.clickListener = clickListener;
    }
}
