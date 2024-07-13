package me.abdiskiosk.guis.placeholder;

import me.abdiskiosk.guis.state.NamedState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface PlaceholderApplier {

    @NotNull String replace(@NotNull String text, @NotNull Collection<@NotNull ? extends NamedState<?>> states);

    default @NotNull List<@NotNull String> replace(@NotNull List<@NotNull String> text,
                                           @NotNull Collection<@NotNull? extends NamedState<?>> states) {
        List<String> replaced = new ArrayList<>();
        for(String str : text) {
            String replacedString = replace(str, states);
            String[] lines = replacedString.split("\\n");
            
            replaced.addAll(Arrays.asList(lines));
        }
        return replaced;
    }
    
}
