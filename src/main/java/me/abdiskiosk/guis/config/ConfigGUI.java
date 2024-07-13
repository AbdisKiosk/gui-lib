package me.abdiskiosk.guis.config;

import lombok.Getter;
import me.abdiskiosk.guis.gui.AutoUpdatingGUI;
import me.abdiskiosk.guis.reflection.StateFinder;
import org.bukkit.entity.HumanEntity;
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

    @Override
    public void open(@NotNull HumanEntity player) {
        //TODO: dont rerender when player opens
        registerPlaceholders(StateFinder.findStates(this));
        super.open(player);
    }

}