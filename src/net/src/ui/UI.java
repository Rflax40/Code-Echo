package net.src.ui;

import net.src.EchoGame;
import shade.src.render.FontRenderer;

public class UI {

    public EchoGame theGame;
    public FontRenderer fnt;
    protected UI parentUI;
    private boolean showing;

    public UI(UI parent, EchoGame game) {
        parentUI = parent;
        theGame = game;
        fnt = theGame.fontRenderer;
    }

    public void init() {
        showing = true;
    }

    public void deInit() {
        showing = false;
    }

    public boolean isShowing() {
        return showing;
    }

    public boolean onKeyEvent(KeyEvent event) {
        if(event.state)
            return keyPressed(event);
        else
            return keyReleased(event);
    }

    public boolean onMouseEvent(MouseEvent event) {
        if(event.state) {
            if (mousePressed(event))
                return true;
        } else if(event.button >= 0) {
            if (mouseReleased(event))
                return true;
        } if(Math.abs(event.deltaWheel) > 0)
            if(mouseScrolled(event))
                return true;
        return false;
    }

    public boolean hasParent() {
        return parentUI != null;
    }

    public boolean keyPressed(KeyEvent event) {return false;}
    public boolean keyReleased(KeyEvent event) {return false;}
    public boolean mousePressed(MouseEvent event) {return false;}
    public boolean mouseReleased(MouseEvent event) {return false;}
    public boolean mouseScrolled(MouseEvent event) {return false;}
    public void update(int delta) {}
    public void render(int width, int height, int delta) {}
}
