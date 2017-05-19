package com.mygdx.game.controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.BallActor;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.WallsActor;
import com.mygdx.game.controller.entities.BallBody;
import com.mygdx.game.model.MenuModel;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.EntityModel;

import java.awt.Menu;

import static com.mygdx.game.GameStage.VIEWPORT_WIDTH;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by Tiago Neves on 17/05/2017.
 */

public class MenuController{

    private float accumulator;

    private static MenuController instance;

    private final World world;

    private final BallBody ballBody;

    int randn = 6;//number of random balls in start menu
    BallBody[] rballBodys = new BallBody[randn];



    //    Vector2[] positions = {
//            new Vector2(0,0),
//            new Vector2(VIEWPORT_WIDTH,0),
//            new Vector2(0,VIEWPORT_WIDTH * ratio),
//            new Vector2(VIEWPORT_WIDTH,VIEWPORT_WIDTH * ratio),
//            new Vector2(VIEWPORT_WIDTH/4,(VIEWPORT_WIDTH * ratio)/2),
//            new Vector2(VIEWPORT_WIDTH-VIEWPORT_WIDTH/4,(VIEWPORT_WIDTH * ratio)/2)
//    };
    MenuController() {

        world = new World(new Vector2(0, 0), false);


        ballBody = new BallBody(world,MenuModel.getInstance().getBall());

    }

    public static MenuController getInstance() {
        if (instance == null)
            instance = new MenuController();
        return instance;
    }

    public void update(float delta) {
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;
        }

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
        }
    }

    public World getWorld() {
        return world;
    }

    public void accelerate() {
        System.out.println("manel");
        ballBody.applyForceToCenter(20000f,20000f, true);
    }
}
