package me.abdiskiosk.guis.config.item;

import lombok.Getter;
import me.abdiskiosk.guis.config.ConfigGUI;
import me.abdiskiosk.guis.item.GUIItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Function;

public class ConfigItem implements Function<@NotNull ConfigGUI, @NotNull GUIItem> {

    @Getter
    @NotNull
    private final Function<@NotNull ConfigGUI, @NotNull Collection<Integer>> slots;
    @Getter
    @NotNull
    private final Function<@NotNull ConfigGUI, @NotNull ItemStack> item;

    public ConfigItem(@NotNull Function<@NotNull ConfigGUI, @NotNull Collection<Integer>> slots,
                      @NotNull Function<@NotNull ConfigGUI, @NotNull ItemStack> item) {
        this.slots = slots;
        this.item = item;
    }

    public ConfigItem(@NotNull Function<@NotNull ConfigGUI, @NotNull Collection<Integer>> slots,
                      @NotNull ItemStack item) {
        this(slots, configGUI -> item);
    }

    public ConfigItem(@NotNull Collection<Integer> slots,
                      @NotNull Function<@NotNull ConfigGUI, @NotNull ItemStack> item) {
        this(configGUI -> slots, item);
    }

    public ConfigItem(@NotNull Collection<Integer> slots,
                      @NotNull ItemStack item) {
        this(configGUI -> slots, configGUI -> item);
    }

    @Override
    public @NotNull GUIItem apply(@NotNull ConfigGUI configGUI) {
        return new GUIItem(slots.apply(configGUI), item.apply(configGUI));
    }

}