package com.mygdx.game.model;

import com.mygdx.game.LevelType;
import com.mygdx.game.LevelsModels;
import com.mygdx.game.controller.SandBoxController;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.EnemyModel;
import com.mygdx.game.model.entities.EntityModel;
import com.mygdx.game.model.entities.WallsModel;

import java.util.Random;
import java.util.Vector;

import static com.mygdx.game.view.MenuView.RATIO;
import static com.mygdx.game.view.MenuView.VIEWPORT_WIDTH;

/**
 * Created by Tiago Neves on 26/05/2017.
 */

public class LevelModel {


    static LevelType.levelType currLevel = LevelType.levelType.ONE;

    private static LevelModel instance = new LevelModel();

    private EnemyModel ballModels;

    private BallModel playerModel;

    private WallsModel wallsModel;


    public static LevelModel getInstance(LevelType.levelType newLevel) {
        if (instance == null)
            instance = new LevelModel();
        if(currLevel!=newLevel){
            currLevel = newLevel;
            instance = new LevelModel();
        }
        return instance;
    }

    private LevelModel() {
        Vector<EntityModel> temp = LevelsModels.getInstance().getEntitiesModels(currLevel);
        this.ballModels= (EnemyModel)temp.elementAt(1);

        this.playerModel = (BallModel)temp.elementAt(2);

        this.wallsModel = (WallsModel)temp.elementAt(0);
    }


    /**
     * @param i index in the vector of balls
     * @return
     */
    public EnemyModel getEnemyModel() {
        return ballModels;
    }


    public BallModel getPlayerModel() {
        return playerModel;
    }

    public WallsModel getWallsModel() {
        return wallsModel;
    }


    public void delete(){
        instance=null;
    }
}
