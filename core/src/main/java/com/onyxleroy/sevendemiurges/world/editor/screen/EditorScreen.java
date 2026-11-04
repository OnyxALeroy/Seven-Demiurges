package com.onyxleroy.sevendemiurges.world.editor.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.onyxleroy.sevendemiurges.world.editor.TilesetsManager;

public class EditorScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private Table tileGrid;
    private TilesetsManager tilesetsManager;
    private int selectedTileId = 0;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        initBasicSkin(); // Crée le thème visuel pour le bouton

        Table rootTable = new Table();
        rootTable.setFillParent(true);

        // --- BANDEAU HAUT ---
        Table topBanner = new Table();
        topBanner.setBackground(createColorDrawable(new Color(0.2f, 0.2f, 0.2f, 1)));

        TextButton btnLoad = new TextButton("Tileset Suivant", skin);
        topBanner.add(btnLoad).pad(10);

        // --- BANDEAU GAUCHE ---
        Table leftSidebar = new Table();
        leftSidebar.setBackground(createColorDrawable(new Color(0.3f, 0.3f, 0.3f, 1)));
        tileGrid = new Table();
        leftSidebar.add(tileGrid).top().pad(10).expandY().fillY();

        // --- ASSEMBLAGE ---
        rootTable.add(topBanner).colspan(2).fillX().height(60).row();
        rootTable.add(leftSidebar).width(250).fillY();
        rootTable.add().expand().fill();
        stage.addActor(rootTable);

        // --- LOGIQUE DE CHARGEMENT ---
        tilesetsManager = new TilesetsManager(this, btnLoad);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        tilesetsManager.dispose();
    }

    // --- HELPERS GRAPHIQUES ---

    private TextureRegionDrawable createColorDrawable(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    private void initBasicSkin() {
        skin = new Skin();
        skin.add("default", new BitmapFont());

        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.up = createColorDrawable(Color.DARK_GRAY);
        btnStyle.down = createColorDrawable(Color.GRAY);
        btnStyle.font = skin.getFont("default");

        skin.add("default", btnStyle);
    }

    // --------------------------------------------------------------------------------------------

    public void setSelectedTileId(int newTileId) {
        selectedTileId = newTileId;
    }

    public int getSelectedTileId() {
        return selectedTileId;
    }

    public Table getTileGrid() {
        return tileGrid;
    }
}
