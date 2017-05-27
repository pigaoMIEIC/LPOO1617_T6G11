package com.mygdx.game.model;

import com.mygdx.game.LevelType;
import com.mygdx.game.LevelsModels;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.EnemyModel;
import com.mygdx.game.model.entities.EntityModel;
import com.mygdx.game.model.entities.StaticModel;

import java.util.Vector;

/**
 * Created by Tiago Neves on 26/05/2017.
 */

public class LevelModel {


    static LevelType.levelType currLevel = LevelType.levelType.ONE;

    private static LevelModel instance = new LevelModel();

    private BallModel playerModel;

    private Vector<EnemyModel> enemyModels = new Vector<EnemyModel>();

    private Vector<StaticModel> obstaclesModels =  new Vector<StaticModel>();


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
        Vector<EntityModel> tempEntities = LevelsModels.getInstance().getEntitiesModels(currLevel);
        Vector<StaticModel> tempStatics = LevelsModels.getInstance().getStaticModels(currLevel);

        this.playerModel = (BallModel)tempEntities.elementAt(0);

        for (int i = 1; i < tempEntities.size(); i++) {
            this.enemyModels.addElement((EnemyModel)tempEntities.elementAt(i));
        }

        for (int i = 0; i < tempStatics.size(); i++) {
            this.obstaclesModels.addElement(tempStatics.elementAt(i));
        }
    }


    /**
     * @param i index in the vector of enemies
     * @return
     */
    public EnemyModel getEnemyModel(int i) {
        return enemyModels.elementAt(i);
    }

    public BallModel getPlayerModel() {
        return playerModel;
    }

    public StaticModel getStaticModel(int i) {
        return obstaclesModels.elementAt(i);
    }

    public int getEnemySize() {
        return enemyModels.size();
    }

    public int getObstaclesSize() {
        return obstaclesModels.size();
    }

    public void delete(){
        instance=null;
    }
}
