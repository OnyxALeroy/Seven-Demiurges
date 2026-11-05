package com.onyxleroy.sevendemiurges.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.onyxleroy.sevendemiurges.data.MapData;

import java.util.Map;
import java.util.EnumMap;

public class WorldMap {
    private final EnumMap<MapLayerType, MapLayer> layers = new EnumMap<>(MapLayerType.class);
    private final TilesetManager tilesets;

    public WorldMap(TilesetManager tilesets) {
        this.tilesets = tilesets;
    }

    public void setData(MapData data) {
        layers.clear();
        if (data == null || data.tilesetPath == null) return;

        Tileset tileset = tilesets.get(data.tilesetPath);

        for (Map.Entry<MapLayerType, int[][]> entry : data.layers.entrySet()) {
            MapLayerType type = entry.getKey();
            int[][] layerData = entry.getValue();

            layers.put(type, new MapLayer(layerData, tileset));
        }
    }

    public void render(SpriteBatch batch, float xTopLeft, float yTopLeft) {
        for (MapLayer layer : layers.values()) {
            layer.render(batch);
        }
    }
}
