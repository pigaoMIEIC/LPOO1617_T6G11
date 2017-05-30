package com.mygdx.game.model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.controller.SandBoxController;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.EnemyModel;
import com.mygdx.game.model.entities.StaticModel;

import java.util.Random;
import java.util.Vector;

import static com.mygdx.game.view.MenuView.RATIO;
import static com.mygdx.game.view.MenuView.VIEWPORT_WIDTH;

/**
 * Created by Tiago Neves on 22/05/2017.
 */

public class SandBoxModel{
    private static SandBoxModel instance;

    private Vector<EnemyModel> ballModels = new Vector<EnemyModel>();

    private BallModel playerModel;

    private StaticModel staticModel;


    private int nBalls = 1;



    /**
     * SandBoxModel constructor
     */
    public SandBoxModel() {
        nBalls = 1;
        this.ballModels.removeAllElements();
        this.ballModels.addElement(new EnemyModel(0.08f, 0.08f, 0.08f,true));


        this.playerModel = new BallModel(2f, 2f, 0.08f,false);

        this.staticModel = new StaticModel(0,0);
    }

    /**
     * @return SandBoxModel instance
     */
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

    /**
     * @return Ball Model of the player ball model
     */
    public BallModel getPlayerModel() {
        return playerModel;
    }

    /**
     * @return Number of enemy balls in game
     */
    public int getnBalls() {
        return nBalls;
    }


    /**
     * @return StaticModel of the game's wall
     */
    public StaticModel getStaticModel() {
        return staticModel;
    }


    /**
     * Method to start the next level
     */
    public void nextLevel(){
        float radius = ballModels.elementAt(0).getRadius();

        nBalls++;
        Random r = new Random();

        float x = r.nextFloat()*VIEWPORT_WIDTH - radius;
        float y =r.nextFloat()*VIEWPORT_WIDTH*RATIO - radius;

        this.ballModels.addElement(new EnemyModel(x,y,radius,true));

        SandBoxController.getInstance().nextLevel(nBalls);
    }

    /**
     * Delete the instance
     */
    public void delete(){
        instance=null;
    }
}
