package me.abdiskiosk.guis.pagination;

import lombok.Getter;
import me.abdiskiosk.guis.gui.GUI;
import me.abdiskiosk.guis.item.GUIItem;
import me.abdiskiosk.guis.pagination.item.PaginationItem;
import me.abdiskiosk.guis.pagination.item.PaginationItemConverter;
import me.abdiskiosk.guis.pagination.object.PaginationObjectProvider;
import me.abdiskiosk.guis.util.Scheduler;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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

    private final AtomicInteger version = new AtomicInteger(0);

    //TODO: Function<Slot, PaginationItem> til nullItem samt waitingItem

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

    public CompletableFuture<Boolean> setPageIfNotEmpty(int page) {
        if(page < 1) {
            return CompletableFuture.completedFuture(false);
        }

        final int expectedVersion = version.incrementAndGet();
        CompletableFuture<Boolean> setPage = new CompletableFuture<>();
        objectProvider.get(getRangeMin(page), getRangeMax(page)).thenAccept(objects -> {
            if(version.get() != expectedVersion) {
                setPage.complete(false);
                return;
            }

            if(objects.isEmpty()) {
                setPage.complete(false);
                return;
            }
            setObjects(objects);
            setPage.complete(true);
        });

        return setPage;
    }

    public void setPage(int page) {
        final int expectedVersion = version.incrementAndGet();

        objectProvider.get(getRangeMin(page), getRangeMax(page)).thenAccept(objects -> {
            if(version.get() != expectedVersion) {
                return;
            }
            setObjects(objects);
        });
    }

    protected void setObjects(@NotNull List<T> objects) {
        Scheduler.sync(() -> {
            clearSlots();
            for(int i = 0; i < slots.size(); i++) {
                int slot = slots.get(i);
                if(i < objects.size()) {
                    PaginationItem item = itemConverter.convert(objects.get(i));
                    gui.set(new GUIItem(slot, item.getItem()), item.getStates())
                            .onClick(item.getClickListener());
                    continue;
                }
                setNullItem(slot);
            }
        });
    }

    protected void setNullItem(int slot) {
        if(nullItem == null) {
            return;
        }
        gui.set(new GUIItem(slot, nullItem));
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