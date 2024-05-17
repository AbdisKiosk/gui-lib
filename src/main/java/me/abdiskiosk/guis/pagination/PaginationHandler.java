package me.abdiskiosk.guis.pagination;

import lombok.Getter;
import me.abdiskiosk.guis.gui.GUI;
import me.abdiskiosk.guis.item.GUIItem;
import me.abdiskiosk.guis.pagination.item.PaginationItem;
import me.abdiskiosk.guis.pagination.item.PaginationItemConverter;
import me.abdiskiosk.guis.pagination.object.PaginationObjectProvider;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.List;

public class PaginationHandler<T> {

    @Getter
    @NotNull
    private final GUI gui;

    @Getter
    @NotNull
    private final PaginationObjectProvider<T> objectProvider;
    @Getter
    @NotNull
    private final PaginationItemConverter<T> itemConverter;

    @Getter
    @NotNull
    private final List<Integer> slots;
    @Getter
    @Nullable
    private final ItemStack nullItem;

    public PaginationHandler(@NotNull GUI gui,
                             @NotNull List<Integer> slots,
                             @NotNull PaginationObjectProvider<T> objectProvider,
                             @NotNull PaginationItemConverter<T> itemConverter,
                             @Nullable ItemStack nullItem) {
        this.gui = gui;
        this.objectProvider = objectProvider;
        this.itemConverter = itemConverter;
        this.slots = slots;
        this.nullItem = nullItem;
    }

    public void setPage(@Range(from = 0, to = Integer.MAX_VALUE) int page) {
        clearSlots();
        List<T> objects = objectProvider.get(getRangeMin(page), getRangeMax(page));

        for(int i = 0; i < slots.size(); i++) {
            int slot = slots.get(i);
            if(i < objects.size()) {
                PaginationItem item = itemConverter.convert(objects.get(i));
                gui.set(new GUIItem(slot, item.getItem()))
                        .onClick(item.getClickListener());
                continue;
            }
            gui.set(new GUIItem(slot, nullItem));
        }
    }

    protected void clearSlots() {
        gui.remove(slots);
    }

    protected int getRangeMin(int page) {
        return (page - 1) * slots.size();
    }

    protected int getRangeMax(int page) {
        return getRangeMin(page) + slots.size() - 1;
    }
}