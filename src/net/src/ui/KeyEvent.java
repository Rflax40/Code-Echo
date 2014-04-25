package net.src.ui;

public class KeyEvent {

    public final int keycode;
    public final char keychar;
    public final boolean state;

    public KeyEvent(int kc, char kch, boolean st) {
        keycode = kc;
        keychar = kch;
        state = st;
    }

    public String toString() {
        return String.format("'%c' [%d] %s", keychar, keycode, state? "DOWN" : "UP");
    }
}
