package com.onyxleroy.sevendemiurges.world.editor.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.onyxleroy.sevendemiurges.data.MapData;
import com.onyxleroy.sevendemiurges.world.editor.MapDataLoader;
import com.onyxleroy.sevendemiurges.world.editor.MapDataSaver;
import com.onyxleroy.sevendemiurges.world.editor.TilesetsManager;

public class EditorScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private Table tileGrid;
    private TilesetsManager tilesetsManager;

    private int selectedTileId = 0;
    private String currentName = "";
    private MapData mapData = new MapData();

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

        TextButton btnTilesetLoad = new TextButton("Tileset Suivant", skin);
        topBanner.add(btnTilesetLoad).pad(10);

        // Load map
        TextButton btnMapLoad = new TextButton("Load Map", skin);
        topBanner.add(btnMapLoad).pad(10);
        btnMapLoad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MapDataLoader.openLoadDialog(stage, skin, (MapData loadedMap) -> {
                    mapData = loadedMap;
                });
            }
        });

        // Save map
        TextButton btnMapSave = new TextButton("Save Map", skin);
        topBanner.add(btnMapSave).pad(10);
        btnMapSave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MapDataSaver.openSaveDialog(stage, skin, mapData, currentName, (String nomSaisi) -> {
                    currentName = nomSaisi;
                    System.out.println("Le nom récupéré est : " + nomSaisi);
                });
            }
        });

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
        tilesetsManager = new TilesetsManager(this, btnTilesetLoad);
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

    // --------------------------------------------------------------------------------------------

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

        // Style pour les textes simples (Labels)
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        labelStyle.fontColor = Color.WHITE;
        skin.add("default", labelStyle);

        // Style pour le champ texte (TextField)
        TextField.TextFieldStyle tfStyle = new TextField.TextFieldStyle();
        tfStyle.font = skin.getFont("default");
        tfStyle.fontColor = Color.WHITE;
        tfStyle.background = createColorDrawable(new Color(0.15f, 0.15f, 0.15f, 1));
        tfStyle.cursor = createColorDrawable(Color.WHITE); // Le curseur clignotant
        tfStyle.selection = createColorDrawable(Color.ROYAL); // La couleur quand on surligne le texte
        skin.add("default", tfStyle);

        // Style pour la fenêtre pop-up (Dialog)
        Window.WindowStyle winStyle = new Window.WindowStyle();
        winStyle.titleFont = skin.getFont("default");
        winStyle.background = createColorDrawable(new Color(0.2f, 0.2f, 0.25f, 0.95f)); // Fond semi-transparent
        skin.add("default", winStyle);
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
