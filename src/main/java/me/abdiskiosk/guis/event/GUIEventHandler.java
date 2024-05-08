package me.abdiskiosk.guis.event;

import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;

public interface GUIEventHandler extends GUIClickEventHandler {

    void handleOpen(@NotNull InventoryOpenEvent event);
    void handleClose(@NotNull InventoryCloseEvent event);

}