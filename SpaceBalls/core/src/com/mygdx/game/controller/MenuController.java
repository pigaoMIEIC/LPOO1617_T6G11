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

import java.util.Random;

/**
 * Created by Tiago Neves on 17/05/2017.
 */

public class MenuController{

    private float accumulator;

    private static MenuController instance;

    private final World world;
    private Random forceRand = new Random();

//    private final BallBody ballBody;
    private final WallsBody wallsBody;

    public final static int RANDNR = 4;//number of random balls in start menu
    public final static int SRANDNR = 6;//number of random balls in start menu

    BallBody[] rballBodys = new BallBody[RANDNR];

    BallBody[] staticBodys = new BallBody[SRANDNR];

    float offsetY = 0;
    float offsetX = 0;

    float sensitivity = 0;


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

        float accelX = Gdx.input.getAccelerometerX();
        float accelY = Gdx.input.getAccelerometerY();
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

    public World getWorld() {
        return world;
    }

    public void accelerate() {
        //System.out.println("manel");
        //ballBody.applyForceToCenter(0.1f,0.1f, true);
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public void setSensitivity(float sensitivity) {
        this.sensitivity = sensitivity;
    }
}
