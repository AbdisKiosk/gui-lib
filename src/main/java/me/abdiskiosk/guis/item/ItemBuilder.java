package me.abdiskiosk.guis.item;

import me.abdiskiosk.guis.state.State;
import me.abdiskiosk.guis.util.Skull;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public class ItemBuilder {

    private final ItemStack item;

    public static @NotNull ItemBuilder skull(@NotNull String texture) {
        return new ItemBuilder(Skull.fromTexture(texture));
    }

    public static @NotNull ItemBuilder head(@NotNull OfflinePlayer player) {
        return new ItemBuilder(Skull.from(player));
    }

    public static @NotNull ItemBuilder pane(@NotNull PaneColor color) {
        return new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) color.getData()));
    }

    public ItemBuilder(@NotNull ItemStack item) {
        this.item = item.clone();
    }

    public ItemBuilder(@NotNull Material material) {
        this(new ItemStack(material));
    }

    public @NotNull ItemBuilder setName(@NotNull String name) {
        return modifyMeta(meta -> meta.setDisplayName(name));
    }

    public @NotNull ItemBuilder setLore(@NotNull String... lore) {
        return modifyMeta(meta -> meta.setLore(java.util.Arrays.asList(lore)));
    }

    public @NotNull ItemBuilder setLore(@NotNull List<@NotNull String> lore) {
        return modifyMeta(meta -> meta.setLore(lore));
    }

    public @NotNull ItemBuilder addLore(@NotNull String... lore) {
        return addLore(Arrays.asList(lore));
    }

    public @NotNull ItemBuilder addLore(@NotNull List<@NotNull String> lore) {
        return modifyMeta(meta -> {
            List<String> currentLore = meta.getLore();
            if(currentLore == null) {
                currentLore = new java.util.ArrayList<>();
            }
            currentLore.addAll(lore);
            meta.setLore(currentLore);
        });
    }

    public @NotNull ItemBuilder removeEnchantments() {
        return modifyMeta(meta -> {
            Set<Enchantment> enchantments = new HashSet<>(meta.getEnchants().keySet());

            enchantments.forEach(meta::removeEnchant);
        });
    }

    public @NotNull ItemBuilder setShiny(boolean shiny) {
        if(shiny) {
            return setShiny();
        }
        return this;
    }

    public @NotNull ItemBuilder setShiny() {
        addFlag(ItemFlag.HIDE_ENCHANTS);
        return modifyMeta(meta -> meta.addEnchant(Enchantment.LUCK, 1, true));
    }

    public @NotNull ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public @NotNull ItemBuilder addFlag(@NotNull ItemFlag flag) {
        return modifyMeta(meta -> meta.addItemFlags(flag));
    }

    public @NotNull ItemBuilder modifyMeta(@NotNull Consumer<@NotNull ItemMeta> consumer) throws IllegalStateException {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            throw new IllegalStateException(item.getType() + "does not have itemmeta!");
        }
        consumer.accept(meta);
        item.setItemMeta(meta);
        return this;
    }
    public @NotNull ItemStack build() {
        return item.clone();
    }
}
