package net.src;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", new File("native").getAbsolutePath());
        EchoGame game = new EchoGame("Earth To Echo");
        game.start();
    }
}
