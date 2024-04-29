package me.abdiskiosk.guis.state;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public class NamedState<T> extends State<T> {

    @NotNull
    @Getter
    private final String name;

    public NamedState(T value, @NotNull String name) {
        super(value);
        this.name = name;
    }

}