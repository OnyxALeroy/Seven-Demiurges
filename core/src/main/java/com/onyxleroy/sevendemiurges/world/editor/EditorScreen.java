package com.onyxleroy.sevendemiurges.world.editor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class EditorScreen implements Screen {
    private Stage stage;

    @Override
    public void show() {
        // 1. Initialisation de la scène (UI)
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage); // Permet à l'UI de recevoir les clics

        // 2. Création de la table principale qui prend tout l'écran
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        // rootTable.setDebug(true); // Décommentez pour voir les lignes de la grille !

        // 3. Création des bandeaux avec des couleurs pour les repérer
        Table topBanner = new Table();
        topBanner.setBackground(createColorDrawable(new Color(0.2f, 0.2f, 0.2f, 1))); // Gris foncé

        Table leftSidebar = new Table();
        leftSidebar.setBackground(createColorDrawable(new Color(0.3f, 0.3f, 0.3f, 1))); // Gris un peu plus clair

        // 4. Assemblage de la grille (La syntaxe est chaînée)

        // Ligne 1 : Le bandeau du haut prend les 2 colonnes, hauteur fixe de 60px
        rootTable.add(topBanner).colspan(2).fillX().height(60).row();

        // Ligne 2 : La barre latérale à gauche (largeur 250px) et la zone vide au centre
        rootTable.add(leftSidebar).width(250).fillY();
        rootTable.add().expand().fill(); // expand() pousse les autres éléments sur les bords

        // 5. Ajout de l'interface à la scène
        stage.addActor(rootTable);
    }

    @Override
    public void render(float delta) {
        // 1. On efface l'écran (avec une couleur de fond pour la zone centrale)
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1); // Bleu très sombre pour la map

        // 2. ICI PLUS TARD : Dessiner la map (worldMap.render(batch))

        // 3. Dessiner l'interface par-dessus tout le reste
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    // ... (pause, resume, hide vides) ...

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }

    /**
     * Helper indispensable pour générer une couleur de fond sans avoir à charger d'image.
     * Crée une texture d'un pixel de la couleur demandée, étirable à l'infini.
     */
    private TextureRegionDrawable createColorDrawable(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegionDrawable(new TextureRegion(texture));
    }
}
