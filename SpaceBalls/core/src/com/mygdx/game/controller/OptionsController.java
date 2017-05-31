package com.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.controller.entities.BallBody;
import com.mygdx.game.controller.entities.WallsBody;
import com.mygdx.game.model.OptionsModel;
import com.mygdx.game.model.entities.EntityModel;

import java.util.ArrayList;
import java.util.Random;

import static com.mygdx.game.view.MenuView.VIEWPORT_WIDTH;

/**
 * Created by Tiago Neves on 17/05/2017.
 */

public class OptionsController extends Controller{
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


    float accelY = 0;
    float accelX = 0;

    /**
     * OptionsController constructor
     */
    private OptionsController() {

        world = new World(new Vector2(0, 0), false);

        for(int i = 0; i < smoothnessLevel; i++){
            X_values.add(0f);
            Y_values.add(0f);
        }

        playerBody = new BallBody(world, OptionsModel.getInstance().getPlayerModel());
        playerBody.setDrag(0.2f);

        callibrateBody = new BallBody(world, OptionsModel.getInstance().getCallibrateModel());

        wallsBody = new WallsBody(world,OptionsModel.getInstance().getStaticModel(), 0.5f);

        calRadius=OptionsModel.getInstance().getCallibrateModel().getRadius();


    }

    /**
     * @return OptionsController instance
     */
    public static OptionsController getInstance() {
        if (instance == null)
            instance = new OptionsController();
        return instance;
    }


    /**
     * @param delta time passed
     * updates the physics engine and ball positions
     */
    public void update(float delta) {
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        seconds += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;

        }

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



    /**
     * @return OptionsController physics world
     */
    public World getWorld() {
        return world;
    }


    /**
     * Delete the instance
     */
    public void delete(){
        this.instance = null;
        OptionsModel.getInstance().delete();
    }

    /**
     * @param sensitivity
     * Sets the controller sensitivity
     */
    public void setSensitivity(float sensitivity) {
        this.sensitivity = sensitivity;
    }

    /**
     * @param arr array list of Floats
     * @return average of the array
     */
    public float average(ArrayList<Float> arr){
        Float sum = new Float(0);
        for(Float f : arr)
            sum += f;
        return sum/arr.size();
    }


    /**
     * @return Current accelerometer offset
     */
    public float[] getReadingXY() {
        float[] temp= {readingX.floatValue(),readingY.floatValue()};
        return temp;
    }


    /**
     * Set accelerometer offset
     */
    public void setOffsetXY(float[] offsetXY) {
        this.offsetX = offsetXY[0];
        this.offsetY = offsetXY[1];
    }
    /**
     * @param accelY
     * Set acceleration in Y
     */
    public void setAccelY(float accelY) {
        this.accelY = accelY;
    }

    /**
     * @param accelX
     * Set acceleration in X
     */
    public void setAccelX(float accelX) {
        this.accelX = accelX;
    }
}
