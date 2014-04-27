package net.src.ui;

import net.src.EchoGame;
import net.src.map.MenuMapBuilder;
import shade.src.render.GL;
import shade.src.render.Texture;
import shade.src.resource.Resource;

public class MenuMain extends Menu {

    private static Texture logo;

    public MenuMain(EchoGame game) {
        super(game);
    }

    @Override
    public void init() {
        super.init();
        if (logo == null)
            logo = Texture.getTexture(Resource.getResource("img/title.png"));
        components.add(new ButtonSeries(this, 0, "Exit", "Options", "Map Builder", "Play").setPosition(16, 20));
    }

    @Override
    public void render(int width, int height, int delta) {
        GL.color(Button.color);
        logo.drawModalRect(0, 0, logo.width, logo.height, (width - logo.width) / 2, height - 50, 0, logo.width, logo.height);
        super.render(width, height, delta);
    }

    @Override
    public void componentFired(Component component) {
        switch (component.id) {
            case 0: //Exit
                theGame.requestClose();
                break;
            case 1: //Options
                //TODO Make option menu
                break;
            case 2: //Map Builder
                theGame.changeMenu(new MenuMapBuilder(this, theGame));
                break;
            case 3: //Play
                //TODO Make rest of game
                break;
        }
    }
}
