package com.mygdx.game.model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.EnemyModel;
import com.mygdx.game.model.entities.StaticModel;

/**
 * Created by Tiago Neves on 22/05/2017.
 */

public class OptionsModel{
    private static OptionsModel instance;

    private EnemyModel callibrateModel;

    private BallModel playerModel;

    private StaticModel staticModel;


    private int nBalls = 1;


    /**
     * Options Model constructor
     */
    public OptionsModel() {
        this.callibrateModel = new EnemyModel(3f, 2f, 0.08f,false);

        this.playerModel = new BallModel(2f, 2f, 0.08f,false);

        this.staticModel = new StaticModel(0,0);
    }
    /**
     * @return OptionsModel instance
     */
    public static OptionsModel getInstance() {
        if (instance == null)
            instance = new OptionsModel();
        return instance;
    }


    /**
     * @return EnemyModel of the calibrate ball
     */
    public EnemyModel getCallibrateModel() {
        return callibrateModel;
    }


    /**
     * @return Ball Model of the player ball model
     */
    public BallModel getPlayerModel() {
        return playerModel;
    }



    /**
     * @return StaticModel of the game's wall
     */
    public StaticModel getStaticModel() {
        return staticModel;
    }

    /**
     * Delete the instance
     */
    public void delete(){
        instance=null;
    }
}
