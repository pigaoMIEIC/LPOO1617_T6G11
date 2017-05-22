package com.mygdx.game.model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.controller.MenuController;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.WallsModel;


/**
 * Created by Tiago Neves on 18/05/2017.
 */

public class MenuModel extends Stage{
    private static MenuModel instance;

    private BallModel[] ballModels = new BallModel[MenuController.RANDNR];
    private WallsModel wallsModel;

    public MenuModel() {
        for(int i = 0; i < ballModels.length;i++) {
            this.ballModels[i] = new BallModel(0.08f, 0.08f, 0.08f, false, true, true);
        }
        this.wallsModel = new WallsModel(0,0);
    }

    public static MenuModel getInstance() {
        if (instance == null)
            instance = new MenuModel();
        return instance;
    }

    /**
     * @param i index in the vector of balls
     * @return
     */
    public BallModel getBallModel(int i) {
        return ballModels[i];
    }

    public WallsModel getWallsModel() {
        return wallsModel;
    }

}
