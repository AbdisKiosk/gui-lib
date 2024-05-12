package me.abdiskiosk.guis.state.objects;

import me.abdiskiosk.guis.state.DefaultState;

public class IntState extends DefaultState<Integer> {

    public IntState(Integer value) {
        super(value);
    }

    public void increment() {
        this.set(this.get() + 1);
    }

    public void decrement() {
        this.set(this.get() - 1);
    }

    public void add(int value) {
        this.set(this.get() + value);
    }

    public void subtract(int value) {
        this.set(this.get() - value);
    }
}