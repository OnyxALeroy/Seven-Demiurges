package com.onyxleroy.sevendemiurges.world.editor;

import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import com.onyxleroy.sevendemiurges.data.MapData;

import static com.onyxleroy.sevendemiurges.Globals.MAP_FOLDER_LOCATION;

public class MapDataSaver {
    private static void saveMap(MapData data, String nomFichier) {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        FileHandle file = Gdx.files.local(MAP_FOLDER_LOCATION + nomFichier + ".json");
        file.writeString(json.prettyPrint(data), false);
        System.out.println("Carte sauvegardée dans : " + file.file().getAbsolutePath());
    }

    public static void openSaveDialog(Stage stage, Skin skin, MapData data, String defaultName, Consumer<String> onValidation) {
        TextField nameField = new TextField(defaultName != null ? defaultName : "", skin);
        nameField.setMessageText("Nom de la carte");

        Dialog dialog = new Dialog("Sauvegarder la carte", skin) {
            @Override
            protected void result(Object object) {
                boolean isSaving = (Boolean) object;

                if (isSaving) {
                    String finalName = nameField.getText();
                    if (finalName.trim().isEmpty()) {
                        finalName = "carte_sans_nom"; // Sécurité si l'utilisateur ne tape rien
                    }

                    System.out.println("Ordre de sauvegarde lancé pour : data/maps/" + finalName + ".json");
                    saveMap(data, finalName);
                    if (onValidation != null) {
                        onValidation.accept(finalName);
                    }
                }
            }
        };

        dialog.getContentTable().add(new Label("Nom du fichier :", skin)).pad(10);
        dialog.getContentTable().add(nameField).width(200).pad(10);
        dialog.button("Valider", true);
        dialog.button("Annuler", false);

        dialog.show(stage);
        stage.setKeyboardFocus(nameField);
        nameField.selectAll();
    }
}
