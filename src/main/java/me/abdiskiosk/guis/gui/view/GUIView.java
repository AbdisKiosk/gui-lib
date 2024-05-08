package me.abdiskiosk.guis.gui.view;

import me.abdiskiosk.guis.event.GUIClickEventHandler;
import me.abdiskiosk.guis.item.gui.GUIItem;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface GUIView extends GUIClickEventHandler {
    void open(@NotNull Player player);
    @NotNull ListenerItemStack setItem(@NotNull GUIItem item);
    void removeItems(@NotNull Collection<@NotNull Integer> slots);
    void setName(@NotNull String name);

}