package me.abdiskiosk.guis;

import lombok.Getter;
import lombok.Setter;
import me.abdiskiosk.guis.placeholder.PlaceholderApplier;
import org.jetbrains.annotations.NotNull;

public class GUIManager {

    @Getter
    private static GUIManager instance;

    @Getter @Setter
    private @NotNull PlaceholderApplier placeholderApplier;

    GUIManager() {
    }

    public GUIManager getInstanec() {
        if(instance == null) {
            instance = new GUIManager();
        }
        return instance;
    }
}