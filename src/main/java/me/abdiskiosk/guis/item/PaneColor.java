package me.abdiskiosk.guis.item;

public enum PaneColor {

    WHITE(0),
    ORANGE(1),
    MAGENTA(2),
    LIGHT_BLUE(3),
    YELLOW(4),
    LIME(5),
    PINK(6),
    GRAY(7),
    LIGHT_GRAY(8),
    CYAN(9),
    PURPLE(10),
    BLUE(11),
    BROWN(12),
    GREEN(13),
    RED(14),
    BLACK(15);

    private final int data;

    PaneColor(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

}
