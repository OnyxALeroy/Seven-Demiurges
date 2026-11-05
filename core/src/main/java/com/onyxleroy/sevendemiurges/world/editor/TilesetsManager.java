package com.onyxleroy.sevendemiurges.world.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.onyxleroy.sevendemiurges.world.Tileset;
import com.onyxleroy.sevendemiurges.world.TilesetManager;
import com.onyxleroy.sevendemiurges.world.editor.screen.EditorScreen;

public class TilesetsManager {
    private static final String tilesetsPath = "tilesets";
    private static final String tilesetsExtension = ".png";

    private Tileset currentTileset;
    private final FileHandle[] availableTilesets;
    private int tilesetIndex = 0;

    private final EditorScreen editorScreen;
    private final TilesetManager tilesetManager;

    public TilesetsManager(EditorScreen editorScreen, TextButton btnLoad, TilesetManager tilesetManager) {
        this.editorScreen = editorScreen;
        this.tilesetManager = tilesetManager;
        availableTilesets = Gdx.files.internal(tilesetsPath).list(tilesetsExtension);

        btnLoad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (availableTilesets.length > 0) {
                    tilesetIndex = (tilesetIndex + 1) % availableTilesets.length;
                    loadTilesetToSidebar(availableTilesets[tilesetIndex]);
                } else {
                    String cheminAbsolu = Gdx.files.internal(tilesetsPath).file().getAbsolutePath();
                    System.out.println("Aucun tileset trouvé dans : " + cheminAbsolu);
                }
            }
        });

        // Charge le premier tileset par défaut s'il y en a un
        if (availableTilesets.length > 0) {
            loadTilesetToSidebar(availableTilesets[0]);
        }
    }

    private void loadTilesetToSidebar(FileHandle fileHandle) {
        editorScreen.getTileGrid().clearChildren();

        // Chargement du nouveau via le manager partagé (cache + cycle de vie)
        currentTileset = tilesetManager.get(fileHandle.path());
        editorScreen.getMapDrawManager().setTilesetPath(fileHandle.path());
        TextureRegion[] tiles = currentTileset.getTiles();

        // Création de la grille d'images
        for (int i = 0; i < tiles.length; i++) {
            final int tileId = i;
            Image tileImage = new Image(tiles[i]);

            // Permet de cliquer sur l'image pour la sélectionner
            tileImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    editorScreen.setSelectedTileId(tileId);
                }
            });

            editorScreen.getTileGrid().add(tileImage).size(48, 48).pad(4);

            // Retour à la ligne toutes les 4 tuiles pour former la grille
            if ((i + 1) % 4 == 0) {
                editorScreen.getTileGrid().row();
            }
        }
    }
}
