package me.abdiskiosk.guis.pagination.item;

import lombok.Getter;
import me.abdiskiosk.guis.item.GUIItem;
import me.abdiskiosk.guis.state.StaticNamedState;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

public class PaginationItem {

    @Getter
    @NotNull
    private final ItemStack item;
    @Getter
    @NotNull
    private final Consumer<InventoryClickEvent> clickListener;
    @Getter
    @NotNull
    private final Collection<StaticNamedState<?>> states;

    public PaginationItem(@NotNull ItemStack item) {
        this.item = item;
        this.clickListener = event -> {};
        this.states = Collections.emptySet();
    }

    public PaginationItem(@NotNull ItemStack item, @NotNull Consumer<InventoryClickEvent> clickListener) {
        this.item = item;
        this.clickListener = clickListener;
        this.states = Collections.emptySet();
    }

    public PaginationItem(@NotNull ItemStack item, @NotNull Collection<StaticNamedState<?>> states) {
        this.item = item;
        this.clickListener = event -> {};
        this.states = states;
    }

    public PaginationItem(@NotNull ItemStack item, @NotNull Consumer<InventoryClickEvent> clickListener,
                          @NotNull Collection<StaticNamedState<?>> states) {
        this.item = item;
        this.clickListener = clickListener;
        this.states = states;
    }
}
