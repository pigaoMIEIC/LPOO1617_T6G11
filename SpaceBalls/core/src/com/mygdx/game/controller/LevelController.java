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
import com.mygdx.game.LevelType;
import com.mygdx.game.LevelsBodies;
import com.mygdx.game.controller.entities.BallBody;
import com.mygdx.game.controller.entities.EntityBody;
import com.mygdx.game.controller.entities.StaticBody;
import com.mygdx.game.controller.entities.WallsBody;
import com.mygdx.game.model.LevelModel;
import com.mygdx.game.model.entities.EntityModel;

import java.util.Random;
import java.util.Vector;

import static com.mygdx.game.view.MenuView.RATIO;
import static com.mygdx.game.view.MenuView.VIEWPORT_WIDTH;

/**
 * Created by Tiago Neves on 26/05/2017.
 */

public class LevelController {
    private float accumulator;

    private float seconds;

    private static LevelController instance;

    private World world;
    private Random forceRand = new Random();

    private final WallsBody wallsBody;

    private BallBody rballBodys;

    BallBody playerBody;

    private boolean Colliding;

    private Object userData;

    boolean joystick = true;

    float sensitivity = 60;

    float offsetX;
    float offsetY;

    static LevelType.levelType currLevel = LevelType.levelType.ONE;

    public static LevelController getInstance() {
        if (instance == null){
            instance = new LevelController();
        }
        return instance;
    }

    LevelController() {
        world = new World(new Vector2(0, 0), false);

        Vector<EntityBody> enityTemp = LevelsBodies.getInstance().getEntitiesBodies(currLevel,world);
        Vector<StaticBody> staticTemp = LevelsBodies.getInstance().getStaticBodies(currLevel,world);

        playerBody = (BallBody)enityTemp.elementAt(0);
        playerBody.setDrag(0.5f);
        userData = playerBody.getUserData();


        rballBodys = (BallBody)enityTemp.elementAt(1);
        rballBodys.setType(BodyDef.BodyType.DynamicBody);

        wallsBody = (WallsBody)staticTemp.elementAt(0);

        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {
                //for (int i = 0; i < LevelModel.getInstance(currLevel).getnBalls(); i++) {
                    if(contact.getFixtureA().getBody().getUserData() == userData && contact.getFixtureB().getBody().getUserData() != userData){
                        Colliding = true;
                    }
                //}


            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }

        });

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

        Vector2 vector = new Vector2((accelY *sensitivity)/35 - offsetY, (-accelX *sensitivity)/35 + offsetX);

        if(!joystick)
            playerBody.applyForceToCenter(vector.x,vector.y, true);

        //for(int i=0; i < rballBodys.size();i++){
            Vector2 follow = new Vector2((playerBody.getX()-rballBodys.getX())/50,(playerBody.getY()-rballBodys.getY())/50);
            follow.limit(0.01f);
            rballBodys.applyForceToCenter(follow.x,follow.y,true);
        //}



        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
        }


        playerBody.limitSpeed();

    }

    /**
     * Applies force to the player Level ball
     * @param x
     * @param y
     */
    public void accelerate(float x,float y){
        playerBody.applyForceToCenter(sensitivity*x,sensitivity*y, true);
    }



    public World getWorld() {
        return world;
    }
    
    public boolean isColliding() {
        return Colliding;
    }

    public void setColliding(boolean colliding) {
        Colliding = colliding;
    }

    public void delete(){
        this.instance = null;
        LevelModel.getInstance(currLevel).delete();
    }

    public float getSeconds() {
        return seconds;
    }

    public void setSensitivity(float sensitivity) {
        this.sensitivity = sensitivity;
    }

    public void setJoystick(boolean joystick) {
        this.joystick = joystick;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }
}