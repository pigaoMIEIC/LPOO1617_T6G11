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
import com.mygdx.game.controller.entities.BallBody;
import com.mygdx.game.controller.entities.WallsBody;
import com.mygdx.game.model.MenuModel;
import com.mygdx.game.model.OptionsModel;
import com.mygdx.game.model.SandBoxModel;
import com.mygdx.game.model.entities.EntityModel;

import java.util.ArrayList;
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

    private BallBody playerBody;

    private float sensitivity = 1/50f;

    private float calRadius;

    private final int smoothnessLevel = 6;

    //data of accelerometer in x
    private ArrayList<Float> X_values = new ArrayList<Float>(smoothnessLevel);
    //data of accelerometer in y
    private ArrayList<Float> Y_values = new ArrayList<Float>(smoothnessLevel);


    //readings
    private Float readingY = new Float(0);
    private Float readingX = new Float(0);
    private Float offsetY = 0f;
    private Float offsetX = 0f;


    OptionsController() {

        world = new World(new Vector2(0, 0), false);

        for(int i = 0; i < smoothnessLevel; i++){
            X_values.add(0f);
            Y_values.add(0f);
        }

        playerBody = new BallBody(world, OptionsModel.getInstance().getPlayerModel());
        playerBody.setDrag(0.2f);

        callibrateBody = new BallBody(world, OptionsModel.getInstance().getCallibrateModel());

        wallsBody = new WallsBody(world,OptionsModel.getInstance().getWallsModel(), 0.5f);

        calRadius=OptionsModel.getInstance().getCallibrateModel().getRadius();


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

        X_values.remove(0);
        X_values.add(accelX*delta);
        Y_values.remove(0);
        Y_values.add(accelY*delta);

        readingX = sensitivity*average(X_values);
        readingY = sensitivity*average(Y_values);

        Vector2 vector = new Vector2(readingY - offsetY, -readingX + offsetX);
        vector.limit(VIEWPORT_WIDTH/6 - calRadius);


        callibrateBody.setTransform(vector.x+VIEWPORT_WIDTH-VIEWPORT_WIDTH/6-calRadius, vector.y+VIEWPORT_WIDTH/3+calRadius,0);

        playerBody.applyForceToCenter(vector.x,vector.y,true);


        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
        }


        playerBody.limitSpeed();

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

    public float average(ArrayList<Float> arr){
        Float sum = new Float(0);
        for(Float f : arr)
            sum += f;
        return sum/arr.size();
    }


    public float getReadingY() {
        return readingY.floatValue();
    }


    public float getReadingX() {
        return readingX.floatValue();
    }


    public void setOffsetY(Float readingY) {
        this.offsetY = readingY;
    }

    public void setOffsetX(Float readingX) {
        this.offsetX = readingX;
    }
}
