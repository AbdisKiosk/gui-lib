package me.abdiskiosk.guis.gui.view.bukkit;

import me.abdiskiosk.guis.event.GUIClickEventHandler;
import me.abdiskiosk.guis.gui.view.GUIView;
import me.abdiskiosk.guis.gui.view.ListenerItemStack;
import me.abdiskiosk.guis.item.gui.GUIItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class BukkitGUIView implements GUIView, InventoryHolder {

    private @NotNull Inventory inventory;

    public BukkitGUIView(@NotNull String name, int sizeSlots) {
        this.inventory = Bukkit.createInventory(this, sizeSlots, name);
    }

    @Override
    public void open(@NotNull Player player) {
        player.openInventory(inventory);
    }

    @Override
    public @NotNull ListenerItemStack setItem(@NotNull GUIItem item) {
        ListenerItemStack listen = new ListenerItemStack(item.getItem());

        for(int slot : item.getSlots()) {
            inventory.setItem(slot, listen);
        }

        return listen;
    }

    @Override
    public void removeItems(@NotNull Collection<@NotNull Integer> slots) {
        for(int slot : slots) {
            inventory.setItem(slot, null);
        }
    }

    @Override
    public synchronized void setName(@NotNull String name) {
        Inventory oldInventory = this.inventory;
        Inventory newInventory = Bukkit.createInventory(this, oldInventory.getSize(), name);

        newInventory.setContents(oldInventory.getContents());
        for(HumanEntity entity : oldInventory.getViewers()) {
            if(entity == null) {
                continue;
            }

            entity.openInventory(newInventory);
        }

        this.inventory = newInventory;
    }

    @Override
    public void handleDrag(@NotNull InventoryDragEvent event) {
        ItemStack item = event.getOldCursor();
        if(item instanceof GUIClickEventHandler) {
            ((GUIClickEventHandler) item).handleDrag(event);
        }
    }

    @Override
    public void handleClick(@NotNull InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if(item instanceof GUIClickEventHandler) {
            ((GUIClickEventHandler) item).handleClick(event);
        }
    }

    @Override
    @Deprecated
    //Only used to implement InventoryHolder
    public Inventory getInventory() {
        return inventory;
    }
}