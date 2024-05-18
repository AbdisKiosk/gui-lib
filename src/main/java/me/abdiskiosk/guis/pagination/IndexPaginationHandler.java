package me.abdiskiosk.guis.pagination;

import me.abdiskiosk.guis.gui.GUI;
import me.abdiskiosk.guis.pagination.item.PaginationItemConverter;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class IndexPaginationHandler extends PaginationHandler<Integer> {
    public IndexPaginationHandler(@NotNull GUI gui,
                                  @NotNull List<Integer> slots,
                                  @NotNull PaginationItemConverter<Integer> itemConverter,
                                  @Nullable ItemStack nullItem) {
        super(gui, slots, IndexPaginationHandler::intList, itemConverter, nullItem);
    }

    protected static List<Integer> intList(int minIndexInclusive, int maxIndexInclusive) {
        List<Integer> list = new ArrayList<>();
        for(int i = minIndexInclusive; i <= maxIndexInclusive; i++) {
            list.add(i);
        }
        return list;
    }
}
