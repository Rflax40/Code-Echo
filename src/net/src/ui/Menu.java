package net.src.ui;

import net.src.EchoGame;

import java.util.ArrayList;

public class Menu extends UI {

    protected Menu parentMenu;
    protected ArrayList<Component> components;

    public Menu(Menu parent, EchoGame game) {
        super(parent, game);
        parentMenu = parent;
        components = new ArrayList<Component>();
    }

    public Menu(EchoGame game) {
        this(null, game);
    }

    @Override
    public void deInit() {
        super.deInit();
        components.clear();
    }

    @Override
    public boolean onMouseEvent(MouseEvent event) {
        boolean res = false;
        for(Component component : components)
            if (component.onMouseEvent(event))
                res = true;
        return res || super.onMouseEvent(event);
    }

    @Override
    public boolean onKeyEvent(KeyEvent event) {
        boolean used = false;
        for(Component component : components)
            if(component.onKeyEvent(event))
                used = true;
        return super.onKeyEvent(event) || used;
    }

    public void componentFired(Component component) {}

    @Override
    public void render(int width, int height, int delta) {
        for(Component component : components)
            component.render(width, height, delta);
    }

    @Override
    public void update(int delta) {
        for(Component component : components)
            component.update(delta);
    }

}
