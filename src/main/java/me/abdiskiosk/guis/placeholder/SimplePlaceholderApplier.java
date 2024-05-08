package me.abdiskiosk.guis.placeholder;

import me.abdiskiosk.guis.state.NamedState;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class SimplePlaceholderApplier implements PlaceholderApplier {

    @Override
    public @NotNull String replace(@NotNull String text, @NotNull Collection<NamedState<?>> states) {
        for(NamedState<?> state : states) {
            text = text.replaceAll("{" + state.getName() + "}", state.get().toString());
        }
        return text;
    }
}