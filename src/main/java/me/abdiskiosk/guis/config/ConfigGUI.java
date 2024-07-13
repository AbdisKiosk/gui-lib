package me.abdiskiosk.guis.config;

import lombok.Getter;
import me.abdiskiosk.guis.gui.AutoUpdatingGUI;
import org.jetbrains.annotations.NotNull;

public class ConfigGUI<T extends GUIConfig> extends AutoUpdatingGUI {

    @Getter
    protected final T config;

    public ConfigGUI(@NotNull T config) {
        super(config.getName(), config.getSlots());
        this.config = config;

        config.getDecoration().forEach(decoration -> {
            set(decoration.apply(this));
        });
    }

}