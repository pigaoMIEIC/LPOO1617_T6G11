package com.mygdx.game.model;

import com.mygdx.game.LevelType;
import com.mygdx.game.LevelsModels;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.EndBallModel;
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

    private EndBallModel endBall;

    private Vector<EnemyModel> enemyModels = new Vector<EnemyModel>();

    private Vector<StaticModel> obstaclesModels =  new Vector<StaticModel>();


    /**
     * @return  instance of MevelModel
     */
    public static LevelModel getInstance(LevelType.levelType newLevel) {
        if (instance == null)
            instance = new LevelModel();
        if(currLevel!=newLevel){
            currLevel = newLevel;
            instance = new LevelModel();
        }
        return instance;
    }

    /**
     * LevelModel constructor
     */
    private LevelModel() {
        Vector<EntityModel> tempEntities = LevelsModels.getInstance().getEntitiesModels(currLevel);
        Vector<StaticModel> tempStatics = LevelsModels.getInstance().getStaticModels(currLevel);

        this.playerModel = (BallModel)tempEntities.elementAt(0);

        this.endBall = (EndBallModel)tempEntities.elementAt(1);

        for (int i = 2; i < tempEntities.size(); i++) {
            this.enemyModels.addElement((EnemyModel)tempEntities.elementAt(i));
        }

        for (int i = 0; i < tempStatics.size(); i++) {
            this.obstaclesModels.addElement(tempStatics.elementAt(i));
        }
    }


    /**
     * @param i index in the vector of enemies
     * @return EnemyModel at i position
     */
    public EnemyModel getEnemyModel(int i) {
        return enemyModels.elementAt(i);
    }

    /**
     * @return BallModel of the players ball
     */
    public BallModel getPlayerModel() {
        return playerModel;
    }

    /**
     * @return EndBallModel
     */
    public EndBallModel getEndBall() {
        return endBall;
    }

    /**
     * @param  i vector position
     * @return StaticModel of the obstacle at i position
     */
    public StaticModel getStaticModel(int i) {
        return obstaclesModels.elementAt(i);
    }

    /**
     * @return number of enemy balls
     */
    public int getEnemySize() {
        return enemyModels.size();
    }

    /**
     * @return number of obstacles
     */
    public int getObstaclesSize() {
        return obstaclesModels.size();
    }

    /**
     * Delete the instance
     */
    public void delete(){
        instance=null;
    }
}
