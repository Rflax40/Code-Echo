package net.src.ui;

import net.src.EchoGame;

public class Component extends UI {

    public int height;
    public int width;
    public int x;
    public int y;
    public int id;

    public Component(Menu parent, EchoGame game, int id) {
        super(parent, game);
        this.id = id;
    }

    public Menu getParentMenu() {
        return (Menu) parentUI;
    }

    public void fire() {
        if (hasParent()) {
            getParentMenu().componentFired(this);
        }
    }

    public Component setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public static boolean boundCheck(int x, int y, int w, int h, int ex, int ey) {
        return ex > x && ex < x + w && ey > y && ey < y + h;
    }
}
