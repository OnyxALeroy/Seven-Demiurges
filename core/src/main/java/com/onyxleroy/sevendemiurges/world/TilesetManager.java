package com.onyxleroy.sevendemiurges.world;

import java.util.HashMap;
import java.util.Map;

public class TilesetManager {
    private final Map<String, Tileset> tilesets = new HashMap<>();

    public Tileset get(String path) {
        return tilesets.computeIfAbsent(path, Tileset::new);
    }

    public void dispose() {
        for (Tileset tileset : tilesets.values()) {
            tileset.dispose();
        }
        tilesets.clear();
    }
}
