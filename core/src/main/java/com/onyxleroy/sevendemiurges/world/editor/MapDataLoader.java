package com.onyxleroy.sevendemiurges.world.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Json;
import com.onyxleroy.sevendemiurges.data.MapData;

import java.util.function.Consumer;

import static com.onyxleroy.sevendemiurges.Globals.MAP_FOLDER_LOCATION;

public class MapDataLoader {

    public static MapData loadMap(String nomFichier) {
        // Ajout de l'extension .json pour que la recherche sur le disque soit exacte
        FileHandle file = Gdx.files.local(MAP_FOLDER_LOCATION + nomFichier + ".json");

        if (!file.exists()) {
            System.err.println("Fichier de sauvegarde introuvable : " + file.path());
            return null;
        }

        Json json = new Json();
        MapData data = json.fromJson(MapData.class, file.readString());
        System.out.println("Carte chargée avec succès depuis : " + file.path());
        return data;
    }

    public static void openLoadDialog(Stage stage, Skin skin, Consumer<MapData> onValidation) {
        TextField nameField = new TextField("", skin);
        nameField.setMessageText("Nom de la carte");

        Dialog dialog = new Dialog("Importer une carte", skin) {
            @Override
            protected void result(Object object) {
                boolean isSaving = (Boolean) object;
                if (isSaving) {
                    String finalName = nameField.getText().trim();
                    System.out.println("Ordre d'importation lancé pour : " + finalName);

                    MapData loaded = loadMap(finalName);

                    if (loaded != null) {
                        // Succès : on transmet les données
                        if (onValidation != null) {
                            onValidation.accept(loaded);
                        }
                    } else {
                        // Échec : UI - Affichage d'une fenêtre d'erreur
                        Dialog errorDialog = new Dialog("Erreur", skin);
                        errorDialog.text("Le fichier '" + finalName + "' est introuvable.\nVerifiez le nom et reessayez.");
                        errorDialog.button("OK");
                        errorDialog.show(stage);
                    }
                }
            }
        };

        dialog.getContentTable().add(new Label("Nom du fichier :", skin)).pad(10);
        dialog.getContentTable().add(nameField).width(200).pad(10);

        TextButton btnValider = new TextButton("Valider", skin);
        btnValider.setDisabled(nameField.getText().trim().isEmpty());
        nameField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                btnValider.setDisabled(nameField.getText().trim().isEmpty());
            }
        });

        dialog.button(btnValider, true);
        dialog.button("Annuler", false);

        dialog.show(stage);
        stage.setKeyboardFocus(nameField);
        nameField.selectAll();
    }
}

