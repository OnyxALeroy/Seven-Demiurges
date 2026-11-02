package com.onyxleroy.sevendemiurges;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.onyxleroy.sevendemiurges.world.MapLayer;
import com.onyxleroy.sevendemiurges.world.MapLayerType;
import com.onyxleroy.sevendemiurges.world.Tileset;
import com.onyxleroy.sevendemiurges.world.WorldMap;

import java.util.EnumMap;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;

    private Tileset mainTileset;
    private WorldMap worldMap;

    @Override
    public void show() {
        batch = new SpriteBatch();

        // 1. On initialise notre nouvel importateur
        mainTileset = new Tileset("tileset.png");

        // 2. On passe directement les tuiles découpées au layer
        MapLayer backgroundLayer = new MapLayer(mainTileset.getTiles());

        // 3. On construit la carte
        EnumMap<MapLayerType, MapLayer> layers = new EnumMap<>(MapLayerType.class);
        layers.put(MapLayerType.BACKGROUND, backgroundLayer);
        worldMap = new WorldMap(layers);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        worldMap.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        if(width <= 0 || height <= 0) return;
        // Adjust the camera if the player resizes the window
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        if (tiledMap != null) tiledMap.dispose();
        if (mainTileset != null) mainTileset.dispose();
        if (mapRenderer != null) mapRenderer.dispose();
    }
}
