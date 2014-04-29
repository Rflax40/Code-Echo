package net.src.map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import shade.src.resource.Resource;
import shade.src.resource.ResourceBased;

import java.awt.*;
import java.io.*;
import java.util.*;

public class Layout extends ResourceBased {

    public static Gson gson = new GsonBuilder().create();
    public static Factory layoutFactory = new Factory() {
        @Override
        public ResourceBased construct(Resource resource) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getAsStream()))) {
                return gson.fromJson(reader, Layout.class);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public Class getClassType() {
            return Layout.class;
        }
    };
    public HashMap<Integer, ArrayList<int[]>> data;

    private Layout(Resource resource) {
        super(resource);
        data = new HashMap<>();
    }

    public void write(Resource resource) {
        clean();
        try (BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(resource.getAsOutputStream()))) {
            buff.write(gson.toJson(this));
            buff.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(Point point) {
        for (Map.Entry<Integer, ArrayList<int[]>> entry : data.entrySet())
            for (int i = 0; i < entry.getValue().size(); i++)
                if (entry.getValue().get(i)[0] == point.x && entry.getValue().get(i)[1] == point.y)
                    entry.getValue().remove(i--);
    }

    public void add(int id, Point... points) {
        if (!data.containsKey(id))
            data.put(id, new ArrayList<int[]>());
        ArrayList<int[]> tiles = data.get(id);
        for (Point point : points)
            tiles.add(new int[] {point.x, point.y});
    }

    public void clean() {
        for (int i = 0; i < Tile.tileList.length; i++)
            if (data.containsKey(i))
                if (data.get(i).isEmpty())
                    data.remove(i);
    }

    @Override
    public void unload() {
        for (int i : data.keySet())
            data.get(i).clear();
        data.clear();
        super.unload();
    }

    public static Layout readLayout(Resource resource) {
        return (Layout) ResourceBased.getResourceBased(resource, layoutFactory);
    }

    public static Layout emptyLayout() {
        return new Layout(null);
    }
}
