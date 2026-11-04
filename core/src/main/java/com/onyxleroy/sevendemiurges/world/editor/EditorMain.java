package com.onyxleroy.sevendemiurges.world.editor;

import com.badlogic.gdx.Game;
import com.onyxleroy.sevendemiurges.world.editor.screen.EditorScreen;

public class EditorMain extends Game {
    @Override
    public void create() {
        setScreen(new EditorScreen());
    }
}
