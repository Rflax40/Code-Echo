package net.src.ui;

import net.src.EchoGame;
import shade.src.render.GL;

public class Button extends Component {

    public static final int hoverColor = 0xFFFFFF;
    public static final int clickColor = 0xFF8F8F;
    public static final int color = 0x8FFFFF;
    public static final int shadowColor = 0x222222;
    public static final int shadowOffset = 2;

    private String text;
    public boolean hover;
    public boolean click;

    public Button(Menu parent, EchoGame game, int id) {
        super(parent, game, id);
    }

    public Button setText(String s) {
        text = s;
        width = (int) Math.round(fnt.lineWidth(s));
        height = (int) Math.round(fnt.lineHeight());
        return this;
    }

    @Override
    public boolean mousePressed(MouseEvent event) {
        if(boundCheck(x, y, width, height, event.x, event.y)) {
            click = true;
            fire();
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(MouseEvent event) {
        if(click) {
            click = false;
            fire();
            return true;
        }
        return false;
    }

    @Override
    public boolean onMouseEvent(MouseEvent event) {
        if(boundCheck(x, y, width, height, event.x, event.y))
            hover = true;
        else
            hover = false;
        return super.onMouseEvent(event);
    }

    @Override
    public void render(int width, int height, int delta) {
        GL.color(shadowColor);
        fnt.drawString(text, x + shadowOffset, y + fnt.lineHeight() - shadowOffset, 0);
        int y = this.y;
        int x = this.x;
        if(click) {
            GL.color(clickColor);
            x += shadowOffset;
            y -= shadowOffset;
        }
        else if(hover) {
            GL.color(hoverColor);
            x += shadowOffset / 2;
            y -= shadowOffset / 2;
        } else
            GL.color(color);
        fnt.drawString(text, x, y + fnt.lineHeight(), 0);
    }

}
