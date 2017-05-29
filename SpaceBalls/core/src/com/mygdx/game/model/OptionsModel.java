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


    public OptionsModel() {
        this.callibrateModel = new EnemyModel(3f, 2f, 0.08f,false);

        this.playerModel = new BallModel(2f, 2f, 0.08f,false);

        this.staticModel = new StaticModel(0,0);
    }

    public static OptionsModel getInstance() {
        if (instance == null)
            instance = new OptionsModel();
        return instance;
    }


    public EnemyModel getCallibrateModel() {
        return callibrateModel;
    }


    public BallModel getPlayerModel() {
        return playerModel;
    }


    public StaticModel getStaticModel() {
        return staticModel;
    }


    public void delete(){
        instance=null;
    }
}
