package com.onyxleroy.sevendemiurges.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.onyxleroy.sevendemiurges.Globals.TILE_SIZE;

public class MapLayer {
    // 0 = grass, 1 = stone
    private final int[][] mapData = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 1, 1, 0, 0, 0, 0, 1},
        {1, 0, 0, 1, 1, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    // Le tableau qui contient les images.
    // L'index 0 sera l'herbe, l'index 1 sera la pierre.
    private final TextureRegion[] tiles;

    // Le constructeur force l'injection des textures
    public MapLayer(TextureRegion[] tiles) {
        this.tiles = tiles;
    }

    public void render(SpriteBatch batch) {
        for (int row = 0; row < mapData.length; row++) {
            for (int col = 0; col < mapData[row].length; col++) {
                int tileType = mapData[row][col];

                // MAGIE : Plus besoin de (tileType == 1) ? stone : grass.
                // On récupère directement l'image correspondant au numéro !
                TextureRegion regionToDraw = tiles[tileType];

                float x = col * TILE_SIZE;
                float y = (mapData.length - 1 - row) * TILE_SIZE;

                batch.draw(regionToDraw, x, y);
            }
        }
    }
}
