package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.controller.MenuController;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.StaticBallModel;
import com.mygdx.game.model.entities.StaticModel;
import com.mygdx.game.view.GameView;

import static com.mygdx.game.view.MenuView.RATIO;
import static com.mygdx.game.view.MenuView.VIEWPORT_WIDTH;


/**
 * Created by Tiago Neves on 18/05/2017.
 */

public class MenuModel extends Stage{
    private static MenuModel instance;

    private BallModel[] ballModels = new BallModel[GameView.RANDNR];
    private StaticBallModel[] staticBallModel = new StaticBallModel[GameView.SRANDNR];
    private StaticModel staticModel;

    Vector2[] positions = {
            new Vector2(0,0),
            new Vector2(VIEWPORT_WIDTH,0),
            new Vector2(0,VIEWPORT_WIDTH * RATIO),
            new Vector2(VIEWPORT_WIDTH,VIEWPORT_WIDTH * RATIO),
            new Vector2(0,(VIEWPORT_WIDTH * RATIO)/2),
            new Vector2(VIEWPORT_WIDTH,(VIEWPORT_WIDTH * RATIO)/2)
    };

    public MenuModel() {
        for(int i = 0; i < ballModels.length;i++) {
            this.ballModels[i] = new BallModel(0.08f, 0.08f, 0.08f,true);
        }

        for(int i = 0; i < staticBallModel.length;i++) {
            this.staticBallModel[i] = new StaticBallModel(positions[i].x, positions[i].y, 0.1f,false);
        }

        this.staticModel = new StaticModel(0,0);
    }

    public static MenuModel getInstance() {
        if (instance == null)
            instance = new MenuModel();
        return instance;
    }

    /**
     * @param i index in the vector of balls
     * @return
     */
    public BallModel getBallModel(int i) {
        return ballModels[i];
    }

    public StaticBallModel getStaticBallModel(int i) {
        return staticBallModel[i];
    }

    public StaticModel getStaticModel() {
        return staticModel;
    }

}
