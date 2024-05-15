package me.abdiskiosk.guis.util;

import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.UUID;

public class Skull {

    public static @NotNull ItemStack fromTexture(@NotNull String texture) {
        ItemStack skull = skull();
        SkullMeta sm = (SkullMeta) skull.getItemMeta();
        Object profile = createGameProfile(texture);
        try {
            Field profileField = sm.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(sm, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        skull.setItemMeta(sm);

        return skull;
    }

    @SneakyThrows
    public static @NotNull Object createGameProfile(@NotNull String texture) {
        Object profile = Class.forName("com.mojang.authlib.GameProfile")
                .getConstructor(UUID.class, String.class).newInstance(UUID.randomUUID(), "");

        Object property = Class.forName("com.mojang.authlib.properties.Property")
                .getConstructor(String.class, String.class).newInstance("textures", texture);

        Object properties = profile.getClass().getMethod("getProperties").invoke(profile);
        properties.getClass().getMethod("put", Object.class, Object.class).invoke(properties, "textures", property);

        return profile;
    }

    public static @NotNull ItemStack from(@NotNull OfflinePlayer player) {
        ItemStack skull = skull();
        SkullMeta sm = (SkullMeta) skull.getItemMeta();
        sm.setOwner(player.getName());
        skull.setItemMeta(sm);

        return skull;

    }

    public static @NotNull ItemStack skull() {
        return new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
    }
}