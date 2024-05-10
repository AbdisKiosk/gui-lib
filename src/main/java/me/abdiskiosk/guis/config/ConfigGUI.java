package me.abdiskiosk.guis.config;

import lombok.Getter;
import me.abdiskiosk.guis.gui.AutoUpdatingGUI;
import org.jetbrains.annotations.NotNull;

public class ConfigGUI extends AutoUpdatingGUI {

    @Getter
    protected final GUIConfig config;

    public ConfigGUI(@NotNull GUIConfig config) {
        super(config.getName(), config.getSlots());
        this.config = config;

        config.getDecoration().forEach(decoration -> {
            set(decoration.apply(this));
        });
    }

}