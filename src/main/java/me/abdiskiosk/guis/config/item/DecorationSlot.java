package me.abdiskiosk.guis.config.item;

import me.abdiskiosk.guis.config.ConfigGUI;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum DecorationSlot implements Function<@NotNull ConfigGUI, @NotNull Collection<@NotNull Integer>> {

    TOP,
    BOTTOM,
    LEFT,
    RIGHT;

    @Override
    public @NotNull Collection<@NotNull Integer> apply(@NotNull ConfigGUI configGUI) {
        int size = configGUI.getConfig().getSlots();


        switch(this) {
            case TOP: {
                return IntStream.range(0, 9).boxed().collect(Collectors.toList());
            }
            case BOTTOM: {
                return IntStream.range(size - 9, size).boxed().collect(Collectors.toList());
            }
            case LEFT: {
                return IntStream.range(0, size).filter(i -> i % 9 == 0).boxed().collect(Collectors.toList());
            }
            case RIGHT: {
                return IntStream.range(0, size).filter(i -> i % 9 == 8).boxed().collect(Collectors.toList());
            }
        }

        return Collections.emptySet();
    }
}
