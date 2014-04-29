package net.src.map;

import net.src.EchoGame;
import net.src.ui.Button;
import net.src.ui.Component;
import net.src.ui.*;
import net.src.ui.Menu;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import shade.src.render.GL;
import shade.src.resource.Resource;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class MenuMapBuilder extends Menu {

    private Layout workingLayout;
    private int currentTile;
    private Point focus;
    private float zoom;
    private boolean moving;

    public MenuMapBuilder(Menu parent, EchoGame game) {
        super(parent, game);
        workingLayout = Layout.readLayout(Resource.getResource("map/map.json"));
        focus = new Point(0, 0);
        zoom = 1;
    }

    @Override
    public void init() {
        super.init();
        components.add(new Button(this, 0).setText("Save and Exit").setPosition(10, 15));
    }

    @Override
    public void render(int width, int height, int delta) {
        int buff = (int) Math.round(fnt.lineHeight() * 3);
        GL.color(Button.color);
        fnt.drawString("Map Builder", 0, height, 0);
        fnt.drawString("Current Tile:", 0, height - fnt.lineHeight(), 0);
        GL.color(0xFFFFFF);
        GL11.glPushMatrix();
        GL11.glTranslated(Tile.tileSize / 2 + fnt.lineWidth("Current Tile:"), height - Tile.tileSize * 2, 0);
        //GL11.glScalef(.5f, .5f, 1);
        Tile.tileList[currentTile].render(0, 0, width, height, delta);
        GL11.glPopMatrix();
        GL11.glPushAttrib(GL11.GL_VIEWPORT_BIT);
        GL11.glPushMatrix();
        int nh = height - 2 * buff;
        GL11.glViewport(0, buff, width, nh);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, 0, nh, 1, -1);
        GL11.glTranslatef(focus.x + width / 2, focus.y + nh / 2, 0);
        GL11.glScalef(zoom, zoom, zoom);
        for (Map.Entry<Integer, ArrayList<int[]>> entry : workingLayout.data.entrySet())
            for (int[] point : entry.getValue())
                Tile.tileList[entry.getKey()].render(point[0] * Tile.tileSize, point[1] * Tile.tileSize, width, nh, delta);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2d(0, height - buff);
        GL11.glVertex2d(width, height - buff);
        GL11.glVertex2d(0, buff);
        GL11.glVertex2d(width, buff);
        GL11.glEnd();
        super.render(width, height, delta);
        //for(Layout.Section section : workingLayout.data)
        //for(int[] coord : section.coordinates)
    }

    @Override
    public void componentFired(Component component) {
        if (component.id == 0) {
            workingLayout.write(Resource.getResource("map/map.json"));
            workingLayout.unload();
            theGame.changeMenu(parentMenu);
        }
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        switch (event.keycode) {
            case Keyboard.KEY_ESCAPE:
                theGame.changeMenu(parentMenu);
                return true;
            case Keyboard.KEY_EQUALS:
                zoom /= 0.8f;
                return true;
            case Keyboard.KEY_MINUS:
                zoom *= 0.8f;
                return true;
            case Keyboard.KEY_LBRACKET:
                currentTile--;
                break;
            case Keyboard.KEY_RBRACKET:
                currentTile++;
        }
        while (currentTile < 0)
            currentTile += Tile.tileList.length;
        currentTile %= Tile.tileList.length;
        return false;
    }

    @Override
    public boolean mousePressed(MouseEvent event) {
        int x = (int) Math.round((event.x - theGame.window.getWidth() / 2d + focus.x) / (Tile.tileSize * zoom));
        int y = (int) Math.round((event.y - theGame.window.getHeight() / 2d + focus.y) / (Tile.tileSize * zoom));
        switch (event.button) {
            case 0:
                workingLayout.add(currentTile, new Point(x, y));
                break;
            case 1:
                workingLayout.remove(new Point(x, y));
                break;
            case 2:
                moving = true;
                break;
        }
        return event.button >= 0 && event.button <= 2;
    }

    @Override
    public boolean mouseReleased(MouseEvent event) {
        switch (event.button) {
            case 2:
                moving = false;
                return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(MouseEvent event) {
        if (event.deltaWheel > 0)
            zoom /= .8f * (event.deltaWheel / 120);
        else
            zoom *= .8f * (-event.deltaWheel / 120);
        return true;
    }
}
