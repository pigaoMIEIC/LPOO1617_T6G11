package com.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.controller.entities.BallBody;
import com.mygdx.game.controller.entities.WallsBody;
import com.mygdx.game.model.MenuModel;
import com.mygdx.game.model.entities.EntityModel;
import com.mygdx.game.view.GameView;

import java.util.Random;

/**
 * Created by Tiago Neves on 17/05/2017.
 */

public class MenuController extends Controller{

    private float accumulator;

    private static MenuController instance;

    private final World world;
    private Random forceRand = new Random();

    private final WallsBody wallsBody;

    BallBody[] rballBodys = new BallBody[GameView.RANDNR];

    BallBody[] staticBodys = new BallBody[GameView.SRANDNR];

    float sensitivity = 0;

    float accelX;
    float accelY;

    /**
     * MenuController constructor
     */
    MenuController() {

        world = new World(new Vector2(0, 0), false);

        for(int i=0; i < rballBodys.length;i++){
            rballBodys[i] = new BallBody(world,MenuModel.getInstance().getBallModel(i));
            rballBodys[i].applyForceToCenter(5*forceRand.nextFloat()+2,5*forceRand.nextFloat()+2,true);
            rballBodys[i].setType(BodyDef.BodyType.DynamicBody);
            rballBodys[i].setDrag(0.8f);
        }

        for(int i=0; i < staticBodys.length;i++){
            staticBodys[i] = new BallBody(world,MenuModel.getInstance().getStaticBallModel(i));
            staticBodys[i].setType(BodyDef.BodyType.StaticBody);
        }

        wallsBody = new WallsBody(world,MenuModel.getInstance().getStaticModel(), 1f);

    }

    /**
     * @return MenuController instance
     */
    public static MenuController getInstance() {
        if (instance == null)
            instance = new MenuController();
        return instance;
    }

    /**
     * @param delta time passed
     * updates the physics engine and ball positions
     */
    public void update(float delta) {
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;
        }

        Vector2 vector = new Vector2(accelY * sensitivity * 0.01f - offsetY*0.01f, -accelX * sensitivity * 0.01f + offsetX*0.01f);

        for(int i=0; i < rballBodys.length;i++){
            rballBodys[i].applyForceToCenter(vector.x,vector.y, true);
        }


        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
        }
    }

    /**
     * @param accelX
     * Set acceleration in X
     */
    public void setAccelX(float accelX) {
        this.accelX = accelX;
    }

    /**
     * @param accelY
     * Set acceleration in Y
     */
    public void setAccelY(float accelY) {
        this.accelY = accelY;
    }

    /**
     * @return OptionsController physics world
     */
    public World getWorld() {
        return world;
    }

    /**
     * @param sensitivity
     * Sets the controller sensitivity
     */
    public void setSensitivity(float sensitivity) {
        this.sensitivity = sensitivity;
    }

    /**
     * Deletes the instance
     */
    public void delete(){
        this.instance = null;
        MenuModel.getInstance().delete();
    }
}
