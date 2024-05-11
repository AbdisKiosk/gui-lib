package me.abdiskiosk.guis.pagination;

import lombok.Getter;
import me.abdiskiosk.guis.gui.GUI;
import me.abdiskiosk.guis.item.GUIItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public class PaginationHandler<T> {

    private final @NotNull BiFunction<Integer, Integer, T> pageSupplier;
    private final @NotNull BiFunction<Integer, Integer, @NotNull ItemStack> itemSupplier;
    private final @NotNull Function<Integer, @Nullable ItemStack> nullItemSupplier;
    private final @NotNull BiFunction<Integer, Integer, T> slotProvider;

    private final @NotNull GUI gui;

    private final Set<GUIItem> currentItems = new HashSet<>();

    @Getter
    @Range(from = 0, to = Integer.MAX_VALUE)
    private int page = 0;

    public PaginationHandler(@NotNull BiFunction<Integer, Integer, T> pageSupplier,
                             @NotNull BiFunction<Integer, Integer, @NotNull ItemStack> itemSupplier,
                             @NotNull Function<Integer, @Nullable ItemStack> nullItemSupplier,
                             @NotNull BiFunction<Integer, Integer, T> slotProvider,
                             @NotNull GUI gui) {
        this.pageSupplier = pageSupplier;
        this.itemSupplier = itemSupplier;
        this.nullItemSupplier = nullItemSupplier;
        this.slotProvider = slotProvider;
        this.gui = gui;
    }

    public synchronized void setPage(@Range(from = 0, to = Integer.MAX_VALUE) int page) {
        this.page = page;
        clearItems();




    }

    protected void clearItems() {
        //TODO: kun fjern items, hvis nullItemSupplier giver null på slottet
        currentItems.forEach(gui::remove);
        currentItems.clear();
    }





}
