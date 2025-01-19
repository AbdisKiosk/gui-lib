package me.abdiskiosk.guis.gui;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.abdiskiosk.guis.GUIManager;
import me.abdiskiosk.guis.event.GUIEventHandler;
import me.abdiskiosk.guis.gui.view.GUIView;
import me.abdiskiosk.guis.gui.view.ListenerItemStack;
import me.abdiskiosk.guis.gui.view.bukkit.BukkitGUIView;
import me.abdiskiosk.guis.item.GUIItem;
import me.abdiskiosk.guis.placeholder.PlaceholderUtils;
import me.abdiskiosk.guis.state.NamedState;
import me.abdiskiosk.guis.state.StaticNamedState;
import me.abdiskiosk.guis.util.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

public class GUI implements GUIEventHandler {

    @Getter(AccessLevel.PROTECTED)
    private final @NotNull GUIView view;

    @Setter @Getter
    private @Nullable Consumer<@NotNull InventoryDragEvent> dragAction;
    @Setter @Getter
    private @Nullable Consumer<@NotNull InventoryClickEvent> clickAction;
    @Setter @Getter
    private @Nullable Consumer<@NotNull InventoryCloseEvent> closeAction;
    @Setter @Getter
    private @Nullable Consumer<@NotNull InventoryOpenEvent> openAction;
    @Setter @Getter
    private @Nullable Consumer<@NotNull Event> otherAction;

    @Getter
    private @Nullable Runnable runWhileOpen;
    @Getter
    private int runWhileOpenWaitTicks;
    private @Nullable BukkitTask lastTaskWhileOpen;

    protected final @NotNull Set<NamedState<?>> placeholders = new HashSet<>();
    protected final @NotNull Map<GUIItem, Set<StaticNamedState<?>>> itemToPlaceholders = new HashMap<>();

    @Getter
    protected final int sizeSlots;

    public GUI(@NotNull String name, int sizeSlots) {
        this.view = createView(parseName(name), sizeSlots);
        this.sizeSlots = sizeSlots;
    }

    public synchronized ListenerItemStack set(@NotNull GUIItem item) {
        return view.setItem(parse(item));
    }

    public synchronized ListenerItemStack set(@NotNull GUIItem item,
                                              @NotNull Collection<@NotNull StaticNamedState<?>> placeholders) {
        itemToPlaceholders.put(item, new HashSet<>(placeholders));
        return view.setItem(parse(item));
    }

    public void update(@NotNull GUIItem item) {
        view.updateItem(parse(item));
    }

    protected @NotNull GUIItem parse(@NotNull GUIItem item) {
        Set<NamedState<?>> states = new HashSet<>(placeholders);
        if(itemToPlaceholders.containsKey(item)) {
            states.addAll(itemToPlaceholders.get(item));
        }
        return PlaceholderUtils.withPlaceholders(item,
                GUIManager.getInstance().getPlaceholderApplier(), states);
    }

    public void open(@NotNull HumanEntity player) {
        view.open(player);
    }

    public void remove(@NotNull GUIItem item) {
        view.removeItems(item.getSlots());
    }

    public void remove(@NotNull Collection<Integer> slots) {
        view.removeItems(slots);
    }

    public void remove(@NotNull Integer... slots) {
        view.removeItems(Arrays.asList(slots));
    }

    public synchronized void registerPlaceholder(@NotNull NamedState<?> placeholder) {
        registerPlaceholders(Collections.singleton(placeholder));
    }

    public synchronized void registerPlaceholders(@NotNull Collection<@NotNull NamedState<?>> placeholders) {
        this.placeholders.addAll(placeholders);
    }

    protected @NotNull GUIView createView(@NotNull String name, int sizeSlots) {
        return new BukkitGUIView(parseName(name), sizeSlots, this);
    }

    protected void setName(@NotNull String name) {
        view.setName(parseName(name));
    }

    protected String parseName(@NotNull String name) {
        return GUIManager.getInstance().getPlaceholderApplier().replace(name, placeholders);
    }

    public boolean isOpen() {
        return view.isOpen();
    }

    public synchronized void whileOpenAsync(@NotNull Runnable run, int waitTicks) {
        this.runWhileOpen = run;
        this.runWhileOpenWaitTicks = waitTicks;

        if(isRunningWhileOpenTask() && lastTaskWhileOpen != null) {
            lastTaskWhileOpen.cancel();
            startRunningWhileOpenTask();
        }

    }

    @Override
    public void handleDrag(@NotNull InventoryDragEvent event) {
        handleIfPresent(dragAction, event);
    }

    @Override
    public void handleClick(@NotNull InventoryClickEvent event) {
        handleIfPresent(clickAction, event);
    }

    @Override
    public void handleOpen(@NotNull InventoryOpenEvent event) {
        handleIfPresent(openAction, event);
        startRunningWhileOpenIfNotRunning();
    }

    @Override
    public void handleClose(@NotNull InventoryCloseEvent event) {
        handleIfPresent(closeAction, event);
    }

    private synchronized void startRunningWhileOpenIfNotRunning() {
        if(!isRunningWhileOpenTask()) {
            startRunningWhileOpenTask();
        }
    }

    private synchronized void startRunningWhileOpenTask() throws IllegalStateException {
        if(isRunningWhileOpenTask()) {
            throw new IllegalStateException("Already running while open task");
        }
        if(runWhileOpen == null) {
            return;
        }
        lastTaskWhileOpen = Scheduler.untilClosedAsync(this, runWhileOpen, runWhileOpenWaitTicks);
    }

    private synchronized boolean isRunningWhileOpenTask() {
        if(lastTaskWhileOpen == null) {
            return false;
        }

        return Bukkit.getScheduler().isCurrentlyRunning(lastTaskWhileOpen.getTaskId())
                || Bukkit.getScheduler().isQueued(lastTaskWhileOpen.getTaskId());
    }

    private <T> void handleIfPresent(Consumer<@NotNull T> action, @NotNull T event) {
        if(action != null) {
            action.accept(event);
        }
    }

}