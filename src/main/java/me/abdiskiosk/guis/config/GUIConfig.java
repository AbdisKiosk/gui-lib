package me.abdiskiosk.guis.config;

import me.abdiskiosk.guis.gui.GUI;
import me.abdiskiosk.guis.item.GUIItem;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Function;

public interface GUIConfig {

    int getSlots();
    @NotNull String getName();

    @NotNull Collection<@NotNull Function<@NotNull ConfigGUI, @NotNull GUIItem>> getDecoration();

}