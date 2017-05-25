package com.mygdx.game.model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.WallsModel;

import java.util.Random;
import java.util.Vector;

import static com.mygdx.game.view.MenuView.RATIO;
import static com.mygdx.game.view.MenuView.VIEWPORT_WIDTH;

/**
 * Created by Tiago Neves on 22/05/2017.
 */

public class OptionsModel extends Stage{
    private static OptionsModel instance;

    private BallModel callibrateModel;

    private BallModel playerModel;

    private WallsModel wallsModel;


    private int nBalls = 1;


    public OptionsModel() {
        this.callibrateModel = new BallModel(3f, 2f, 0.08f,false);

        this.playerModel = new BallModel(2f, 2f, 0.08f,false);

        this.wallsModel = new WallsModel(0,0);
    }

    public static OptionsModel getInstance() {
        if (instance == null)
            instance = new OptionsModel();
        return instance;
    }


    public BallModel getCallibrateModel() {
        return callibrateModel;
    }


    public BallModel getPlayerModel() {
        return playerModel;
    }

    public int getnBalls() {
        return nBalls;
    }


    public WallsModel getWallsModel() {
        return wallsModel;
    }


    public void delete(){
        instance=null;
    }
}
