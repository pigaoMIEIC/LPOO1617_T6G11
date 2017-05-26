package com.mygdx.game;

import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.EnemyModel;
import com.mygdx.game.model.entities.EntityModel;
import com.mygdx.game.model.entities.WallsModel;

import java.util.Vector;

/**
 * Created by User on 26-05-2017.
 */

public class LevelsModels {
    private static final LevelsModels ourInstance = new LevelsModels();

    public static LevelsModels getInstance() {
        return ourInstance;
    }

    private LevelsModels() {}

    public final Vector<EntityModel> getEntitiesModels(LevelType.levelType level){
        Vector<EntityModel> temp = new Vector<EntityModel>();
        switch(level){
            case ONE:
                System.out.println("this is Model one");
                temp.add(new WallsModel(0,0));
                temp.add(new EnemyModel(0.5f, 0.5f, 0.08f,true));
                temp.add(new BallModel(0.08f, 0.08f, 0.08f,true));
                return temp;
            case TWO:
                return temp;
            default:
                System.out.println("default level... ups!!");
                return temp;
        }
    }


}
