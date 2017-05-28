package com.mygdx.game;

import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.EndBallModel;
import com.mygdx.game.model.entities.EnemyModel;
import com.mygdx.game.model.entities.EntityModel;
import com.mygdx.game.model.entities.StaticModel;

import java.util.Vector;

import static com.mygdx.game.view.LevelView.RATIO;
import static com.mygdx.game.view.LevelView.VIEWPORT_WIDTH;

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
        Vector<EntityModel> tempEntity = new Vector<EntityModel>();
        switch(level){
            case ONE:
                System.out.println("this is Model one");
                tempEntity.add(new BallModel(0.08f, 0.08f, 0.08f,true));
                tempEntity.add(new EndBallModel(2,2,0.08f));
                tempEntity.add(new EnemyModel(2.1f,2.1f, 0.08f,true));
                tempEntity.add(new EnemyModel(0.8f, VIEWPORT_WIDTH*RATIO-0.08f, 0.08f,true));
                return tempEntity;
            case TWO:
                return tempEntity;
            default:
                System.out.println("default levelModel.java... ups!!");
                return tempEntity;
        }
    }



    public final Vector<StaticModel> getStaticModels(LevelType.levelType level){
        Vector<StaticModel> tempStatic = new Vector<StaticModel>();
        switch(level){
            case ONE:
                System.out.println("this is StaticModel one");
                tempStatic.add(new StaticModel(0,0));
                tempStatic.add(new StaticModel(1.5f,1.5f));
                tempStatic.add(new StaticModel(3f,2f));
                return tempStatic;
            case TWO:
                return tempStatic;
            default:
                System.out.println("default levelModel.java... ups!!");
                return tempStatic;
        }
    }



}
