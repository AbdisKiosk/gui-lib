package me.abdiskiosk.guis.gui.view;

import me.abdiskiosk.guis.GUIManager;
import me.abdiskiosk.guis.event.GUIClickEventHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ListenerItemStack implements GUIClickEventHandler {

    private final ArrayList<@NotNull Consumer<@NotNull InventoryClickEvent>> clickListeners = new ArrayList<>(1);
    private final ArrayList<@NotNull Consumer<@NotNull InventoryDragEvent>> dragListeners = new ArrayList<>(1);

    public synchronized void onDrag(@NotNull Consumer<@NotNull InventoryDragEvent> listener) {
        dragListeners.add(listener);
    }

    public synchronized void onClick(@NotNull Consumer<@NotNull InventoryClickEvent> listener) {
        clickListeners.add(listener);
    }

    public synchronized void onClickAsync(@NotNull Consumer<@NotNull InventoryClickEvent> listener) {
        clickListeners.add(event -> Bukkit.getScheduler().runTaskAsynchronously(GUIManager.getPlugin(),
                () -> listener.accept(event)));
    }

    public synchronized void handleDrag(@NotNull InventoryDragEvent event) {
        dragListeners.forEach(listener -> listener.accept(event));
    }

    public synchronized void handleClick(@NotNull InventoryClickEvent event) {
        clickListeners.forEach(listener -> listener.accept(event));
    }

}