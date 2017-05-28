package com.mygdx.game.view;

import com.badlogic.gdx.ScreenAdapter;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.controller.LevelController;

/**
 * Created by Tiago Neves on 28/05/2017.
 */

public class GameView extends ScreenAdapter{
    SpaceBallsGame game;
    public GameView(SpaceBallsGame game) {
        super();
        this.game = game;
    }

    protected void backToMenu(){
        LevelController.getInstance().delete();
        game.setScreen(new MenuView(game));
    }
}
