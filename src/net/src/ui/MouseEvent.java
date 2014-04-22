package net.src.ui;

public class MouseEvent {

    public final int button;
    public final boolean state;
    public final int deltaWheel;
    public final int deltaX;
    public final int deltaY;
    public final int x;
    public final int y;

    public MouseEvent(int b, boolean s, int dw, int dx, int dy, int x, int y) {
        button = b;
        state = s;
        deltaWheel = dw;
        deltaX = dx;
        deltaY = dy;
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return String.format("%d %s @(%d, %d)", button, state?"PRESSED":"RELEASED", x, y);
    }

}
