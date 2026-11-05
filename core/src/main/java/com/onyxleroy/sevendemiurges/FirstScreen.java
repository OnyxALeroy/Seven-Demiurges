package com.onyxleroy.sevendemiurges;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.onyxleroy.sevendemiurges.data.MapData;
import com.onyxleroy.sevendemiurges.world.MapLayerType;
import com.onyxleroy.sevendemiurges.world.TilesetManager;
import com.onyxleroy.sevendemiurges.world.WorldMap;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;

    private final TilesetManager tilesetManager = new TilesetManager();
    private WorldMap worldMap;

    private static final int[][] DEFAULT_BACKGROUND = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 1, 1, 0, 0, 0, 0, 1},
        {1, 0, 0, 1, 1, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    @Override
    public void show() {
        batch = new SpriteBatch();

        MapData mapData = new MapData();
        mapData.tilesetPath = "tilesets/red.png";
        mapData.layers.put(MapLayerType.BACKGROUND, DEFAULT_BACKGROUND);

        worldMap = new WorldMap(tilesetManager);
        worldMap.setData(mapData);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        worldMap.render(batch, 0.0f , 0.0f);
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
        tilesetManager.dispose();
        if (mapRenderer != null) mapRenderer.dispose();
    }
}
