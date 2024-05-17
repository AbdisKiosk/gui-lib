package me.abdiskiosk.guis.pagination;

import me.abdiskiosk.guis.gui.GUI;
import me.abdiskiosk.guis.pagination.item.PaginationItem;
import me.abdiskiosk.guis.pagination.object.StaticPaginationObjectProvider;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StaticPaginationHandler extends PaginationHandler<PaginationItem> {

    public StaticPaginationHandler(@NotNull GUI gui,
                                   @NotNull List<Integer> slots,
                                   @NotNull List<PaginationItem> items,
                                   @Nullable ItemStack nullItem) {
        super(gui, slots, new StaticPaginationObjectProvider<>(items), object -> object, nullItem);

    }

}
