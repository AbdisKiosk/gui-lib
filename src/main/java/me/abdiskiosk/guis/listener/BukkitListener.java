package me.abdiskiosk.guis.listener;

import me.abdiskiosk.guis.gui.view.GUIView;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class BukkitListener implements Listener {

    private final Plugin plugin;

    public BukkitListener(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDrag(@NotNull InventoryDragEvent event) {
        handleEvent(event);
    }

    @EventHandler
    public void onClick(@NotNull InventoryClickEvent event) {
        handleEvent(event);
    }

    @EventHandler
    public void onOpen(@NotNull InventoryOpenEvent event) {
        handleEvent(event);
    }

    public void onClose(@NotNull InventoryCloseEvent event) {
        handleEvent(event);
    }

    protected void handleEvent(@NotNull InventoryEvent event) {
        Inventory inventory = event.getView().getTopInventory();
        if(!(inventory.getHolder() instanceof GUIView)) {
            return;
        }
        if((event instanceof InventoryClickEvent || event instanceof InventoryDragEvent)) {
            ((Cancellable) event).setCancelled(true);
        }
        GUIView view = (GUIView) inventory.getHolder();
        if(event instanceof InventoryDragEvent) {
            view.handleDrag((InventoryDragEvent) event);
        } else if(event instanceof InventoryClickEvent) {
            view.handleClick((InventoryClickEvent) event);
        }
    }

    @EventHandler
    public void onPluginDisable(@NotNull PluginDisableEvent event) {
        if(!event.getPlugin().equals(plugin)) {
            return;
        }
        for(Player player : Bukkit.getOnlinePlayers()) {
            InventoryView view = player.getOpenInventory();
            if(view == null) {
                continue;
            }
            Inventory gui = view.getTopInventory();
            if(gui == null || !(gui.getHolder() instanceof GUIView)) {
                continue;
            }

            player.closeInventory();
        }
    }
}
