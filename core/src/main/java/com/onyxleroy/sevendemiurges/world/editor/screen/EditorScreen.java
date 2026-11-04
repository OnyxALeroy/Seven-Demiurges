package com.onyxleroy.sevendemiurges.world.editor.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
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
    private SpriteBatch batch;
    private Skin skin;
    private Table tileGrid;
    private TilesetsManager tilesetsManager;
    private OrthographicCamera camera;
    private final Vector3 touchPos = new Vector3();

    private int selectedTileId = 0;
    private String currentName = "";
    private final MapDrawManager mapDrawManager = new MapDrawManager();

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        ClickListener consumeClick = new ClickListener();
        Gdx.input.setInputProcessor(stage);

        initBasicSkin(); // Crée le thème visuel pour le bouton
        initCameraAndMouse();// Consomme les clics pour éviter qu'ils ne passent à la carte en dessous

        Table rootTable = new Table();
        rootTable.setFillParent(true);

        // --- BANDEAU HAUT ---
        Table topBanner = new Table();
        topBanner.setBackground(createColorDrawable(new Color(0.2f, 0.2f, 0.2f, 1)));
        topBanner.addListener(consumeClick);

        TextButton btnTilesetLoad = new TextButton("Tileset Suivant", skin);
        topBanner.add(btnTilesetLoad).pad(10);

        // Load map
        TextButton btnMapLoad = new TextButton("Load Map", skin);
        topBanner.add(btnMapLoad).pad(10);
        btnMapLoad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MapDataLoader.openLoadDialog(stage, skin, (MapData loadedMap) -> {
                    mapDrawManager.setMapData(loadedMap);
                });
            }
        });

        // Save map
        TextButton btnMapSave = new TextButton("Save Map", skin);
        topBanner.add(btnMapSave).pad(10);
        btnMapSave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MapDataSaver.openSaveDialog(stage, skin, mapDrawManager.getMapData(), currentName, (String nomSaisi) -> {
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
        leftSidebar.addListener(consumeClick);

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
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.end();
        stage.act(delta);
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

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

    private void initCameraAndMouse(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false,1280,720);

        InputAdapter mapInputProcessor = new InputAdapter() {
            private final Vector3 startDragPos = new Vector3();
            private final Vector3 currentDragPos = new Vector3();

            // -1 signifie qu'aucun clic n'est en cours.
            // Sinon, il contient Input.Buttons.LEFT, RIGHT ou MIDDLE
            private int activeDragButton = -1;

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                touchPos.set(screenX, screenY, 0);
                camera.unproject(touchPos);
                float worldX = touchPos.x;
                float worldY = touchPos.y;

                if (activeDragButton == -1) {
                    activeDragButton = button;
                    startDragPos.set(worldX, worldY, 0);
                    currentDragPos.set(worldX, worldY, 0);
                    return true;
                }

                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                touchPos.set(screenX, screenY, 0);
                camera.unproject(touchPos);
                float worldX = touchPos.x;
                float worldY = touchPos.y;

                if (activeDragButton != -1) {
                    currentDragPos.set(worldX, worldY, 0);
                    // Si on veut conserver le déplacement de la caméra en temps réel sur la molette :
                    if (activeDragButton == Input.Buttons.MIDDLE) {
                        float deltaX = -Gdx.input.getDeltaX() * camera.zoom;
                        float deltaY = Gdx.input.getDeltaY() * camera.zoom;

                        camera.translate(deltaX, deltaY);
                        camera.update();
                    }

                    return true;
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (activeDragButton == button) {
                    touchPos.set(screenX, screenY, 0);
                    camera.unproject(touchPos);
                    currentDragPos.set(touchPos.x, touchPos.y, 0);

                    mapDrawManager.onAreaSelected(
                        startDragPos.x, startDragPos.y,
                        currentDragPos.x, currentDragPos.y,
                        button
                    );
                    activeDragButton = -1;
                    return true;
                }

                return false;
            }
        };

        // L'ordre est crucial : l'UI intercepte en premier
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(mapInputProcessor);
        Gdx.input.setInputProcessor(multiplexer);
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
