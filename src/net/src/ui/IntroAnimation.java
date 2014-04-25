package net.src.ui;

import net.src.EchoGame;
import shade.src.render.GL;

public class IntroAnimation extends Menu {

    private long time;
    private int typeSpeed;
    private int flickerSpeed;
    private int lineLinger;
    private int line;
    private long timeSinceDone = -1;
    private String[] words;
    private boolean animationDone;

    public IntroAnimation(EchoGame game) {
        super(game);
        words = new String[] {"SYSTEM ONLINE", "SYSTEM ONLINE", "SYSTEM ONLINE", "SYSTEM ONLINE", "SYSTEM ONLINE", "SYSTEM ONLINE", "SYSTEM ONLINE", "SYSTEM ONLINE"};
        typeSpeed = 25;
        flickerSpeed = 250;
        lineLinger = 2000;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        animationDone = true;
        return true;
    }

    @Override
    public boolean mousePressed(MouseEvent event) {
        animationDone = true;
        return true;
    }

    @Override
    public void render(int width, int height, int delta) {
        if (!animationDone) {
            GL.color(0x8FFFFF);
            time += delta;
            int letters = (int) ((time - line * lineLinger) / typeSpeed);
            for (int i = 0; i < line; i++) {
                fnt.drawString(words[i], 0, height - fnt.lineHeight() * i, 0);
                letters -= words[i].length();
            }
            if (letters > words[line].length()) {
                letters = words[line].length();
            }
            boolean flicker = (int) (time / flickerSpeed) % 2 == 0 && letters == words[line].length();
            fnt.drawString(words[line].substring(0, letters) + (flicker? "|" : ""), 0, height - fnt.lineHeight() * line, 0);
            if (letters == words[line].length()) {
                if (timeSinceDone < 0) {
                    timeSinceDone = time;
                } else if (time - timeSinceDone > lineLinger) {
                    timeSinceDone = -1;
                    line++;
                    if (line >= words.length) {
                        animationDone = true;
                    }
                }
            }
            GL.color(0x8FFFFF, (float) Math.sin(time / 750D));
            fnt.drawCenteredString("[Press any key to continue]", width / 2, 50, 0);
            super.render(width, height, delta);
        }
    }

    @Override
    public void update(int delta) {
        if (animationDone) {
            ;//TODO Switch to Main Menu
        }
    }
}
