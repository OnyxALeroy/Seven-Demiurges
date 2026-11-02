package com.onyxleroy.sevendemiurges.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Map;
import java.util.EnumMap;

public class WorldMap {
    private final EnumMap<MapLayerType, MapLayer> layers;

    public WorldMap(EnumMap<MapLayerType, MapLayer> layers) {
        this.layers = layers.clone();
    }

    public void render(SpriteBatch batch) {
        for (Map.Entry<MapLayerType, MapLayer> entry : layers.entrySet()) {
            entry.getValue().render(batch);

            // MapLayerType type = entry.getKey();
        }
    }
}
