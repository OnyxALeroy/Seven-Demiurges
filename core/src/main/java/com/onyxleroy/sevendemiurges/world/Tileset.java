package com.onyxleroy.sevendemiurges.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import static com.onyxleroy.sevendemiurges.Globals.TILE_SIZE;

public class Tileset {
    private final Texture texture;
    private final TextureRegion[] tiles;

    public Tileset(String imagePath) {
        // 1. Charge l'image globale
        this.texture = new Texture(imagePath);

        // 2. Découpe l'image en une grille 2D
        TextureRegion[][] grid = TextureRegion.split(texture, TILE_SIZE, TILE_SIZE);

        int rows = grid.length;
        int cols = grid[0].length;

        // 3. Aplatit la grille en un tableau 1D pour l'utiliser avec nos IDs (0, 1, 2...)
        this.tiles = new TextureRegion[rows * cols];

        int id = 0;
        for (TextureRegion[] textureRegions : grid) {
            for (int c = 0; c < cols; c++) {
                tiles[id] = textureRegions[c];
                id++;
            }
        }
    }

    // Fournit le tableau complet pour le MapLayer
    public TextureRegion[] getTiles() {
        return tiles;
    }

    // Fournit une tuile spécifique si besoin
    public TextureRegion getTile(int id) {
        return tiles[id];
    }

    // Essentiel : libère la mémoire vidéo
    public void dispose() {
        texture.dispose();
    }
}
