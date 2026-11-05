package com.onyxleroy.sevendemiurges.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.onyxleroy.sevendemiurges.Globals.TILE_SIZE;

public class MapLayer {
    private final int[][] mapData;
    private final Tileset tileset;

    public MapLayer(int[][] mapData, Tileset tileset) {
        this.mapData = mapData;
        this.tileset = tileset;
    }

    public void render(SpriteBatch batch) {
        for (int row = 0; row < mapData.length; row++) {
            for (int col = 0; col < mapData[row].length; col++) {
                int tileType = mapData[row][col];
                if (tileType < 0) continue;

                TextureRegion regionToDraw = tileset.getTile(tileType);

                float x = col * TILE_SIZE;
                float y = (mapData.length - 1 - row) * TILE_SIZE;

                batch.draw(regionToDraw, x, y);
            }
        }
    }
}
