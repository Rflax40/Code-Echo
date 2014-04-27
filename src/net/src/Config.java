package net.src;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import shade.src.resource.Resource;

import java.io.*;

public class Config {

    public static final File configFile = new File("options.json");
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static Elements config = new Elements();

    public static void load() throws Exception {
        if (configFile.createNewFile())
            loadDefaults();
        Resource res = Resource.getResource(configFile);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(res.getAsStream()))) {
            config = gson.fromJson(reader, Elements.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadDefaults() {
        config = new Elements();
        try (PrintStream prnt = new PrintStream(Resource.getResource(configFile).getAsOutputStream())) {
            prnt.print(gson.toJson(config));
            prnt.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Elements {

        //TODO Add the rest of the options here
        public int width;
        public int height;
        public boolean fullscreen;

        private Elements() {
            width = 1024;
            height = 768;
            fullscreen = false;
        }
    }
}
