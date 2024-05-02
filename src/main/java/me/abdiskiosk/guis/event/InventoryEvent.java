package me.abdiskiosk.guis.event;

import lombok.Getter;
import me.abdiskiosk.guis.gui.view.GUIView;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public abstract class InventoryEvent extends PlayerEvent {

    @NotNull
    @Getter
    private final GUIView view;

    public InventoryEvent(@NotNull Player player, @NotNull GUIView view) {
        super(player);
        this.view = view;
    }

}