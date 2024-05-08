package me.abdiskiosk.guis.event;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.jetbrains.annotations.NotNull;

public interface GUIClickEventHandler {

    void handleDrag(@NotNull InventoryDragEvent event);
    void handleClick(@NotNull InventoryClickEvent event);

}