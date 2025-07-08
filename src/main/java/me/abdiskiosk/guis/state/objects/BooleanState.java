package me.abdiskiosk.guis.state.objects;

import me.abdiskiosk.guis.state.DefaultState;

public class BooleanState extends DefaultState<Boolean> {

    public BooleanState(boolean value) {
        super(value);
    }

    public void toggle() {
        this.set(!this.get());
    }

}
