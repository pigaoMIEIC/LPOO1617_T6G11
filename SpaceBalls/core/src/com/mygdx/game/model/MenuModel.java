package com.mygdx.game.model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.controller.entities.BallBody;
import com.mygdx.game.model.entities.BallModel;

import java.awt.Menu;
import java.util.List;

/**
 * Created by Tiago Neves on 18/05/2017.
 */

public class MenuModel extends Stage{
    private static MenuModel instance;

    private BallModel ballModel;

    public MenuModel() {
        this.ballModel = new BallModel(0.11f,0.11f,0.11f,false,true,true);
    }

    public static MenuModel getInstance() {
        if (instance == null)
            instance = new MenuModel();
        return instance;
    }

    public BallModel getBall() {
        return ballModel;
    }

}
