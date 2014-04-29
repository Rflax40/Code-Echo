package net.src.ui;


public class ToggleButton extends Button {

    public String enable;
    public String disable;
    public boolean toggle = true;

    public ToggleButton (Menu parent, int id, String on, String off){
        super(parent, id);
        enable = on;
        disable = off;
        setText(on);
    }

    @Override
    public void fire(){
        if(click){
            toggle = !toggle;
        setText(toggle? enable : disable);
        super.fire();
        }
    }
}
