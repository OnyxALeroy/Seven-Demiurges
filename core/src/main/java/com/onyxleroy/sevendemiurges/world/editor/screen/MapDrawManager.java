package com.onyxleroy.sevendemiurges.world.editor.screen;

import com.badlogic.gdx.Input;
import com.onyxleroy.sevendemiurges.data.MapData;

public class MapDrawManager {
    private MapData mapData = new MapData();

    public MapData getMapData() { return mapData; }

    public void setMapData(MapData newMap) { mapData = newMap; }

    // --------------------------------------------------------------------------------------------

    public void onAreaSelected(float startX, float startY, float endX, float endY, int button) {
        if (button == Input.Buttons.LEFT) {
            // Logique pour peindre la zone avec selectedTileId
        }
        else if (button == Input.Buttons.RIGHT) {
            // Logique pour effacer la zone (mettre la tuile à -1 ou "vide")
        }
        else if (button == Input.Buttons.MIDDLE) {
            return;
        }
    }
}
