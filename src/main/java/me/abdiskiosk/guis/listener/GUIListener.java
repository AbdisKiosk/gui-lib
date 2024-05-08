package me.abdiskiosk.guis.listener;

import me.abdiskiosk.guis.gui.view.GUIView;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class GUIListener implements Listener {

    @EventHandler
    public void onDrag(@NotNull InventoryDragEvent event) {
        handleEvent(event);
    }

    @EventHandler
    public void onClick(@NotNull InventoryClickEvent event) {
        handleEvent(event);
    }

    protected void handleEvent(@NotNull InventoryEvent event) {
        Inventory inventory = event.getView().getTopInventory();
        if(!(inventory.getHolder() instanceof GUIView)) {
            return;
        }
        if(event instanceof Cancellable) {
            ((Cancellable) event).setCancelled(true);
        }
        GUIView view = (GUIView) inventory.getHolder();
        if(event instanceof InventoryDragEvent) {
            view.handleDrag((InventoryDragEvent) event);
        } else if(event instanceof InventoryClickEvent) {
            view.handleClick((InventoryClickEvent) event);
        }
    }
}
