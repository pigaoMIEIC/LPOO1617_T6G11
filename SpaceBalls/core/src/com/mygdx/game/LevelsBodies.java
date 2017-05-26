package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.controller.entities.BallBody;
import com.mygdx.game.controller.entities.EntityBody;
import com.mygdx.game.controller.entities.StaticBody;
import com.mygdx.game.controller.entities.WallsBody;
import com.mygdx.game.model.LevelModel;
import com.mygdx.game.model.SandBoxModel;

import java.util.Vector;

import static com.mygdx.game.LevelType.levelType.ONE;


/**
 * Created by User on 26-05-2017.
 */

public class LevelsBodies {

    private static final LevelsBodies ourInstance = new LevelsBodies();

    public static LevelsBodies getInstance() {
        return ourInstance;
    }

    private LevelsBodies(){
    }

    public Vector<EntityBody> getEntitiesBodies(LevelType.levelType level,World world){
        Vector<EntityBody> temp = new Vector<EntityBody>();
        switch(level){
            case ONE:
                System.out.println("this is entityBodies one");
                temp.add(new BallBody(world, LevelModel.getInstance(ONE).getPlayerModel()));
                temp.add(new BallBody(world, LevelModel.getInstance(ONE).getEnemyModel()));
                //temp.add(new endBallBody)
                return temp;
            case TWO:
                return temp;
            default:
                System.out.println("default level... ups!!");
                return temp;
        }
    }


    public Vector<StaticBody> getStaticBodies(LevelType.levelType level, World world){
        Vector<StaticBody> temp = new Vector<>();
        switch(level){
            case ONE:
                System.out.println("this is staticBodies one");
                temp.add(new WallsBody(world, LevelModel.getInstance(ONE).getWallsModel(), 0.5f));
                //temp.add(new endBallBody)
                return temp;
            case TWO:
                return temp;
            default:
                System.out.println("default level... ups!!");
                return temp;
        }
    }
//    static Vector<EntityBody> levelOneBodies(World world){
//        Vector<EntityBody> temp = new Vector<EntityBody>();
//        temp.add(new BallBody(world,SandBoxModel.getInstance().getPlayerModel()));
//        //temp.add(new endBallBody)
//        return temp;
//    }

}
