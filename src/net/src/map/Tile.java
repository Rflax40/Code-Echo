package net.src.map;

import shade.src.render.Texture;
import shade.src.resource.Resource;

public class Tile {

    public static final int tileSize = 32;
    public static final Texture spriteSheet = Texture.getTexture(Resource.getResource("img/tiles.png"));
    public static final Tile[] tileList = new Tile[] {
            //TODO Make tiles pls
            new Tile(0, "concrete"), new Tile(1, "grass"), new Tile(2, "dirt"), new Tile(3, "sand"),};
    public final String name;
    public final int tileID;

    public Tile(int id, String n) {
        tileID = id;
        name = n;
    }

    public void update(int x, int y, int delta) {
    }

    public void render(int x, int y, int width, int height, int delta) {
        int ux = tileSize * tileID;
        int uy = ux / spriteSheet.width;
        ux %= spriteSheet.width;
        //TODO Decide who is responsible for tile rendering placement
        spriteSheet.drawCenteredModalRect(ux, uy, tileSize, tileSize, x, y, 0, tileSize, tileSize);
    }
}
