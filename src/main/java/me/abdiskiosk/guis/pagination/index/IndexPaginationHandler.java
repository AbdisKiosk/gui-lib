package me.abdiskiosk.guis.pagination.index;

import me.abdiskiosk.guis.gui.GUI;
import me.abdiskiosk.guis.pagination.PaginationHandler;
import me.abdiskiosk.guis.pagination.item.PaginationItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class IndexPaginationHandler extends PaginationHandler<PaginationItem> {

    public IndexPaginationHandler(@NotNull GUI gui,
                                  @NotNull List<Integer> slots,
                                  @NotNull IndexPaginationItemProvider itemProvider,
                                  @Nullable ItemStack nullItem) {
        super(gui, slots, (min, max) -> generateItems(min, max, itemProvider), item -> item, nullItem);
    }

    protected static List<PaginationItem> generateItems(int rangeMinInclusive, int rangeMaxInclusive,
                                                   IndexPaginationItemProvider itemProvider) {
        List<PaginationItem> items = new ArrayList<>();
        for(int i = rangeMinInclusive; i <= rangeMaxInclusive; i++) {
            try {
                items.add(itemProvider.provide(i));
            } catch(IndexOutOfBoundsException ex) {
                break;
            }
        }
        return items;
    }

}