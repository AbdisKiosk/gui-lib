package me.abdiskiosk.guis.gui.view.bukkit;

import me.abdiskiosk.guis.event.GUIEventHandler;
import me.abdiskiosk.guis.gui.view.GUIView;
import me.abdiskiosk.guis.gui.view.ListenerItemStack;
import me.abdiskiosk.guis.item.GUIItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BukkitGUIView implements GUIView, InventoryHolder {

    private @NotNull Inventory inventory;
    private final @NotNull Map<@NotNull Integer, @NotNull ListenerItemStack> slotToListener = new HashMap<>();

    private final GUIEventHandler eventHandler;

    public BukkitGUIView(@NotNull String name, int sizeSlots, @NotNull GUIEventHandler eventHandler) {
        this.inventory = Bukkit.createInventory(this, sizeSlots, name);
        this.eventHandler = eventHandler;
    }

    @Override
    public void open(@NotNull Player player) {
        player.openInventory(inventory);
    }

    @Override
    public @NotNull ListenerItemStack setItem(@NotNull GUIItem item) {
        ListenerItemStack listen = new ListenerItemStack();

        for(int slot : item.getSlots()) {
            inventory.setItem(slot, item.getItem());
            slotToListener.put(slot, listen);
        }

        return listen;
    }

    @Override
    public void updateItem(@NotNull GUIItem item) {
        for(int slot : item.getSlots()) {
            inventory.setItem(slot, item.getItem());
        }
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
        Set<HumanEntity> oldViewers = new HashSet<>(oldInventory.getViewers()); //Avoid concurrent modification
        for(HumanEntity viewer : oldViewers) {
            viewer.openInventory(newInventory);
        }

        this.inventory = newInventory;
    }

    @Override
    public boolean isOpen() {
        return !inventory.getViewers().isEmpty();
    }

    @Override
    public void handleDrag(@NotNull InventoryDragEvent event) {
        for(int slot : event.getInventorySlots()) {
            ListenerItemStack listener = slotToListener.get(slot);
            if(listener != null) {
                listener.handleDrag(event);
            }
        }
        eventHandler.handleDrag(event);
    }

    @Override
    public void handleClick(@NotNull InventoryClickEvent event) {
        ListenerItemStack listener = slotToListener.get(event.getSlot());
        if(listener != null) {
            listener.handleClick(event);
        }
        eventHandler.handleClick(event);
    }

    @Override
    @Deprecated
    //Only used to implement InventoryHolder
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void handleOpen(@NotNull InventoryOpenEvent event) {
        eventHandler.handleOpen(event);
    }

    @Override
    public void handleClose(@NotNull InventoryCloseEvent event) {
        eventHandler.handleClose(event);
    }
}