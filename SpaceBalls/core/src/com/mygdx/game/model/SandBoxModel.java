package com.mygdx.game.model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.controller.SandBoxController;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.EnemyModel;
import com.mygdx.game.model.entities.WallsModel;

import java.util.Random;
import java.util.Vector;

import static com.mygdx.game.view.MenuView.RATIO;
import static com.mygdx.game.view.MenuView.VIEWPORT_WIDTH;

/**
 * Created by Tiago Neves on 22/05/2017.
 */

public class SandBoxModel extends Stage{
    private static SandBoxModel instance;

    private Vector<EnemyModel> ballModels = new Vector<EnemyModel>();

    private BallModel playerModel;

    private WallsModel wallsModel;


    private int nBalls = 1;




    public SandBoxModel() {
        nBalls = 1;
        this.ballModels.removeAllElements();
        this.ballModels.addElement(new EnemyModel(0.08f, 0.08f, 0.08f,true));


        this.playerModel = new BallModel(2f, 2f, 0.08f,false);

        this.wallsModel = new WallsModel(0,0);
    }

    public static SandBoxModel getInstance() {
        if (instance == null)
            instance = new SandBoxModel();
        return instance;
    }

    /**
     * @param i index in the vector of balls
     * @return
     */
    public EnemyModel getEnemyModel(int i) {
        return ballModels.elementAt(i);
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

    public void nextLevel(){
        float radius = ballModels.elementAt(0).getRadius();

        nBalls++;
        Random r = new Random();

        float x = r.nextFloat()*VIEWPORT_WIDTH - radius;
        float y =r.nextFloat()*VIEWPORT_WIDTH*RATIO - radius;

        this.ballModels.addElement(new EnemyModel(x,y,radius,true));

        SandBoxController.getInstance().nextLevel(nBalls,radius);
    }

    public void delete(){
        instance=null;
    }
}
