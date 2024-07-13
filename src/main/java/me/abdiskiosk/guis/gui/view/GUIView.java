package me.abdiskiosk.guis.gui.view;

import me.abdiskiosk.guis.event.GUIClickEventHandler;
import me.abdiskiosk.guis.event.GUIEventHandler;
import me.abdiskiosk.guis.item.GUIItem;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface GUIView extends GUIEventHandler {
    void open(@NotNull HumanEntity player);
    @NotNull ListenerItemStack setItem(@NotNull GUIItem item);
    void updateItem(@NotNull GUIItem item);
    void removeItems(@NotNull Collection<@NotNull Integer> slots);
    void setName(@NotNull String name);
    boolean isOpen();

}