package me.abdiskiosk.guis;

import lombok.Getter;
import lombok.Setter;
import me.abdiskiosk.guis.listener.BukkitListener;
import me.abdiskiosk.guis.placeholder.PlaceholderApplier;
import me.abdiskiosk.guis.placeholder.SimplePlaceholderApplier;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class GUIManager {

    private static GUIManager instance;

    @Getter @Setter
    private @NotNull PlaceholderApplier placeholderApplier = new SimplePlaceholderApplier();

    GUIManager() {


    }

    public static GUIManager getInstance() {
        if(instance == null) {
            instance = new GUIManager();
        }
        return instance;
    }

    public static void registerListeners(@NotNull Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new BukkitListener(plugin), plugin);
    }
}