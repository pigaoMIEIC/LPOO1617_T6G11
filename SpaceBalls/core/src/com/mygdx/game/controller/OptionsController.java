package com.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.OptionsStage;
import com.mygdx.game.controller.entities.BallBody;
import com.mygdx.game.controller.entities.WallsBody;
import com.mygdx.game.model.MenuModel;
import com.mygdx.game.model.OptionsModel;
import com.mygdx.game.model.SandBoxModel;
import com.mygdx.game.model.entities.EntityModel;

import java.util.Random;
import java.util.Vector;

import static com.mygdx.game.view.MenuView.RATIO;
import static com.mygdx.game.view.MenuView.VIEWPORT_WIDTH;

/**
 * Created by Tiago Neves on 17/05/2017.
 */

public class OptionsController {
    private float accumulator;

    private float seconds;

    private static OptionsController instance;

    private World world;
    private Random forceRand = new Random();

    private final WallsBody wallsBody;

    private BallBody callibrateBody;

    BallBody playerBody;

    float sensitivity = 1/50f;




    OptionsController() {

        world = new World(new Vector2(0, 0), false);

        playerBody = new BallBody(world, OptionsModel.getInstance().getPlayerModel());
        playerBody.setDrag(0.2f);

        callibrateBody = new BallBody(world, OptionsModel.getInstance().getCallibrateModel());

        wallsBody = new WallsBody(world,OptionsModel.getInstance().getWallsModel(), 0.5f);


    }

    public static OptionsController getInstance() {
        if (instance == null)
            instance = new OptionsController();
        return instance;
    }



    public void update(float delta) {
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        seconds += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;

        }


        float accelX = Gdx.input.getAccelerometerX();
        float accelY = Gdx.input.getAccelerometerY();
        Vector2 vector = new Vector2(accelY /sensitivity, -accelX /sensitivity);


        callibrateBody.setTransform(vector.x, vector.y,0);

        playerBody.applyForceToCenter(vector.x,vector.y,true);


        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
        }


        playerBody.limitSpeed();

    }

    /**
     * Applies force to the player sandbox ball
     * @param x
     * @param y
     */
    public void accelerate(float x,float y){
        playerBody.applyForceToCenter(x,y, true);
    }



    public World getWorld() {
        return world;
    }


    public void delete(){
        this.instance = null;
        OptionsModel.getInstance().delete();
    }

    public float getSeconds() {
        return seconds;
    }

    public void setSensitivity(float sensitivity) {
        this.sensitivity = sensitivity;
    }

}
