package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.controller.entities.BallBody;
import com.mygdx.game.controller.entities.EntityBody;
import com.mygdx.game.controller.entities.SquareBody;
import com.mygdx.game.controller.entities.StaticBody;
import com.mygdx.game.controller.entities.WallsBody;
import com.mygdx.game.model.LevelModel;

import java.util.Vector;

import static com.mygdx.game.LevelType.levelType.ONE;
import static com.mygdx.game.LevelType.levelType.TWO;


/**
 * Created by User on 26-05-2017.
 */

public class LevelsBodies {

    private static final LevelsBodies ourInstance = new LevelsBodies();

    public static LevelsBodies getInstance() {
        return ourInstance;
    }

    /**
     * Default contructor
     */
    private LevelsBodies(){
    }


    /**
     * Method to get the entity bodies of the elements present in the level passed as argument
     *
     * @param level Indicates the level of the models to be returned
     * @param world Indicates the world in which the bodies will be created
     * @return Returns the entity bodies of the chosen level
     */
    public Vector<EntityBody> getEntitiesBodies(LevelType.levelType level,World world){
        Vector<EntityBody> temp = new Vector<EntityBody>();
        switch(level){
            case ONE:
                //Player ball
                temp.add(new BallBody(world, LevelModel.getInstance(ONE).getPlayerModel()));
                //End ball
                temp.add(new BallBody(world,LevelModel.getInstance(ONE).getEndBall()));
                //Enemy balls
                for (int i = 0; i < LevelModel.getInstance(ONE).getEnemySize() ; i++) {
                    temp.add(new BallBody(world, LevelModel.getInstance(ONE).getEnemyModel(i)));
                }
                return temp;
            case TWO:
                //Player ball
                temp.add(new BallBody(world, LevelModel.getInstance(TWO).getPlayerModel()));
                //End ball
                temp.add(new BallBody(world,LevelModel.getInstance(TWO).getEndBall()));
                //Enemy balls
                for (int i = 0; i < LevelModel.getInstance(TWO).getEnemySize() ; i++) {
                    temp.add(new BallBody(world, LevelModel.getInstance(TWO).getEnemyModel(i)));
                }
                return temp;
            default:
                System.out.println("default levelBodies.java... ups!!");
                return temp;
        }
    }

    /**
     * Method to get the static bodies of the elements present in the level passed as argument
     *
     * @param level Indicates the level of the models to be returned
     * @param world Indicates the world in which the bodies will be created
     * @return Returns the static bodies of the chosen level
     */
    public Vector<StaticBody> getStaticBodies(LevelType.levelType level, World world){
        Vector<StaticBody> temp = new Vector<StaticBody>();
        switch(level){
            case ONE:
                System.out.println("this is staticBodies one");
                //outer limits wall
                temp.add(new WallsBody(world, LevelModel.getInstance(ONE).getStaticModel(0), 0f));
                //inner obstacles
                for (int i = 1; i < LevelModel.getInstance(ONE).getObstaclesSize(); i++) {
                    temp.add(new SquareBody(world,LevelModel.getInstance(ONE).getStaticModel(i), 0f));
                }
                return temp;
            case TWO:
                //outer limits wall
                temp.add(new WallsBody(world, LevelModel.getInstance(TWO).getStaticModel(0), 0f));
                //inner obstacles
                for (int i = 1; i < LevelModel.getInstance(TWO).getObstaclesSize(); i++) {
                    temp.add(new SquareBody(world,LevelModel.getInstance(TWO).getStaticModel(i), 0f));
                }
                return temp;
            default:
                System.out.println("default level... ups!!");
                return temp;
        }
    }
}
