package me.abdiskiosk.guis.placeholder;

import me.abdiskiosk.guis.item.GUIItem;
import me.abdiskiosk.guis.state.NamedState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceholderUtils {

    private static final Pattern PATTERN = Pattern.compile("\\{([^}]*)}");

    public static @NotNull GUIItem withPlaceholders(@NotNull GUIItem item, @NotNull PlaceholderApplier applier,
                                  @NotNull Collection<@NotNull ? extends NamedState<?>> states) {
        return new GUIItem(item.getSlots(), withPlaceholders(item.getItem(), applier, states));
    }

    public static @NotNull ItemStack withPlaceholders(@NotNull ItemStack item, @NotNull PlaceholderApplier applier,
                                  @NotNull Collection<@NotNull ? extends NamedState<?>> states) {
        ItemStack clone = item.clone();
        ItemMeta meta = clone.getItemMeta();
        if(meta == null) {
            return clone;
        }

        if(meta.hasDisplayName()) {
            meta.setDisplayName(applier.replace(item.getItemMeta().getDisplayName(), states));
        }

        if(meta.hasLore()) {
            meta.setLore(applier.replace(item.getItemMeta().getLore(), states));
        }

        clone.setItemMeta(meta);
        return clone;
    }

    public static Set<String> getUsedPlaceholders(@NotNull ItemStack item) {
        if(!item.hasItemMeta()) {
            return Collections.emptySet();
        }

        Set<String> strings = new HashSet<>();
        if(item.getItemMeta().hasDisplayName()) {
            strings.addAll(getUsedPlaceholders(item.getItemMeta().getDisplayName()));
        }
        if(item.getItemMeta().hasLore()) {
            for(String lore : item.getItemMeta().getLore()) {
                strings.addAll(getUsedPlaceholders(lore));
            }
        }

        Set<String> placeholders = new HashSet<>();
        for(String string : strings) {
            placeholders.addAll(getUsedPlaceholders(string));
        }

        return placeholders;
    }

    public static Set<String> getUsedPlaceholders(@NotNull String text) {
        Set<String> placeholders = new HashSet<>();
        Matcher matcher = PATTERN.matcher(text);
        while (matcher.find()) {
            String placeholder = matcher.group();
            String[] parts = placeholder.split("\\.");
            if (parts.length > 0) {
                placeholders.add(parts[0]);
            }
        }
        return placeholders;
    }
}