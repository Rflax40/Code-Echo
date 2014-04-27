package net.src;

import net.src.ui.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import shade.src.WindowController;
import shade.src.render.*;
import shade.src.resource.Resource;

import javax.imageio.ImageIO;
import java.nio.ByteBuffer;

public class EchoGame extends WindowController {

    public FontRenderer fontRenderer;
    public MusicManager music;
    public Menu currentMenu;

    public EchoGame(String t) {
        super(t);
    }

    @Override
    public void preStartup() {
        try {
            //CONFIG
            Config.load();
            //SOUND
            AL.create();
            music = new MusicManager();
            //WINDOW
            fpsCap = 200;
            updateInterval = 10;
            window.resize(1024, 768, false);
            ByteBuffer[] list = new ByteBuffer[2];
            list[0] = Texture.getImageData(ImageIO.read(Resource.getResource("img/icon16x.png").getAsStream()));
            list[1] = Texture.getImageData(ImageIO.read(Resource.getResource("img/icon32x.png").getAsStream()));
            Display.setIcon(list);
            super.preStartup();
            //INPUT
            Keyboard.create();
            //OPENGL
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glOrtho(0, window.getWidth(), 0, window.getHeight(), 1, -1);
            Texture.preferredMag = GL11.GL_LINEAR;
            Texture.preferredMin = GL11.GL_LINEAR_MIPMAP_LINEAR;
            Texture.generateMipMaps = true;
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            fontRenderer = new FontRenderer(new BitmapFont("font/terminus.fnt"));
            //OTHER
            changeMenu(new MenuIntro(this));
            //music.queueNextSong(0);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize properly", e);
        }
    }

    @Override
    public void render(int delta) {
        int height = window.getHeight();
        int width = window.getWidth();
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        currentMenu.render(width, height, delta);
    }

    @Override
    public void shutdown() {
        super.shutdown();
        AL.destroy();
        Keyboard.destroy();
        Display.destroy();
    }

    @Override
    public void update(int delta) {
        //Handle Input
        Keyboard.poll();
        Mouse.poll();
        while (Keyboard.next()) {
            KeyEvent event = new KeyEvent(Keyboard.getEventKey(), Keyboard.getEventCharacter(), Keyboard.getEventKeyState());
            currentMenu.onKeyEvent(event);
        }
        while (Mouse.next()) {
            MouseEvent event = new MouseEvent(Mouse.getEventButton(), Mouse.getEventButtonState(), Mouse.getEventDWheel(), Mouse.getEventDX(), Mouse.getEventDY(), Mouse.getEventX(), Mouse.getEventY());
            currentMenu.onMouseEvent(event);
        }
        currentMenu.update(delta);
    }

    public void changeMenu(Menu menu) {
        changeMenu(menu, true);
    }

    public void changeMenu(Menu menu, boolean resetOld) {
        if (menu != null) {
            if (currentMenu != null && resetOld) {
                currentMenu.deInit();
            }
            currentMenu = menu;
            currentMenu.init();
        }
    }
}
