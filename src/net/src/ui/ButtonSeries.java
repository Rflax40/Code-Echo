package net.src.ui;

public class ButtonSeries extends Component {

    public static final int buttonPadding = 3;
    private Button[] buttons;

    public ButtonSeries(Menu parent, int id, String... texts) {
        super(parent, -1);
        buttons = new Button[texts.length];
        for (int i = 0; i < texts.length; i++)
            buttons[i] = new Button(parent, id + i).setText(texts[i]);
    }

    @Override
    public Component setPosition(int x, int y) {
        super.setPosition(x, y);
        for (int i = 0; i < buttons.length; i++)
            buttons[i].setPosition(x, y + (int) ((fnt.lineHeight() + buttonPadding) * i));
        return this;
    }

    @Override
    public boolean onMouseEvent(MouseEvent event) {
        boolean b = false;
        for (Button button : buttons)
            if (button.onMouseEvent(event))
                b = true;
        return b;
    }

    @Override
    public void update(int delta) {
        for (Button button : buttons)
            button.update(delta);
    }

    @Override
    public void render(int width, int height, int delta) {
        for (Button button : buttons)
            button.render(width, height, delta);
    }
}
