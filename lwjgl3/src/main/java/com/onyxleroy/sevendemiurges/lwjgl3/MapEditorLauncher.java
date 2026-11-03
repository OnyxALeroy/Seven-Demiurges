package com.onyxleroy.sevendemiurges.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.onyxleroy.sevendemiurges.world.editor.EditorMain;

public class MapEditorLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Map Editor");
        config.setWindowedMode(1280, 720);
        config.setForegroundFPS(60);
        new Lwjgl3Application(new EditorMain(), config);
    }
}
