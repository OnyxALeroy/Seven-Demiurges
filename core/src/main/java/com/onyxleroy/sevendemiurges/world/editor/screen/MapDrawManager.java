package com.onyxleroy.sevendemiurges.world.editor.screen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.onyxleroy.sevendemiurges.data.MapData;
import com.onyxleroy.sevendemiurges.world.TilesetManager;
import com.onyxleroy.sevendemiurges.world.WorldMap;

public class MapDrawManager {
    private final EditorScreen screen;
    private MapData mapData = new MapData();
    private final WorldMap worldMap;

    public MapDrawManager(EditorScreen editorScreen, TilesetManager tilesetManager) {
        screen = editorScreen;
        worldMap = new WorldMap(tilesetManager);
    }

    public MapData getMapData() { return mapData; }

    public void setMapData(MapData newMap) {
        mapData = newMap;
        worldMap.setData(mapData);
    }

    public void setTilesetPath(String path) {
        mapData.tilesetPath = path;
        worldMap.setData(mapData);
    }

    // --------------------------------------------------------------------------------------------

    public void renderMap(SpriteBatch batch) {
        worldMap.render(batch, 0f, 0f);
    }

    // --------------------------------------------------------------------------------------------

    public void onAreaSelected(float startX, float startY, float endX, float endY, int button) {
        if (button == Input.Buttons.LEFT) {
            // Logique pour peindre la zone avec selectedTileId
        }
        else if (button == Input.Buttons.RIGHT) {
            // Logique pour effacer la zone (mettre la tuile à -1 ou "vide")
        }
    }
}
