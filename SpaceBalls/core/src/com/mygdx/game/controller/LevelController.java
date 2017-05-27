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

    private final Vector<StaticBody> obstaclesBodies = new Vector<StaticBody>();

    private Vector<BallBody> enemyBodies = new Vector<BallBody>();

    private BallBody endBall;

    BallBody playerBody;

    private boolean Colliding;

    private Object userData;

    private Object userData2;

    boolean joystick = false;

    float sensitivity = 60;

    float offsetX = 0;
    float offsetY = 0;

    static LevelType.levelType currLevel = LevelType.levelType.ONE;

    private boolean win;

    public static LevelController getInstance() {
        if (instance == null){
            instance = new LevelController();
        }
        return instance;
    }

    LevelController() {
        world = new World(new Vector2(0, 0), false);

        Vector<EntityBody> entityTemp = LevelsBodies.getInstance().getEntitiesBodies(currLevel,world);
        Vector<StaticBody> staticTemp = LevelsBodies.getInstance().getStaticBodies(currLevel,world);

        playerBody = (BallBody)entityTemp.elementAt(0);
        playerBody.setDrag(0.5f);
        userData = playerBody.getUserData();

        endBall = (BallBody)entityTemp.elementAt(1);
        userData2 = endBall.getUserData();
        for (int i = 2; i < entityTemp.size(); i++) {
            enemyBodies.addElement((BallBody)entityTemp.elementAt(i));
        }

        endBall = (BallBody)entityTemp.elementAt(2) ;

        wallsBody = (WallsBody)staticTemp.elementAt(0);

        for (int i = 1; i < staticTemp.size(); i++) {
            obstaclesBodies.addElement(staticTemp.elementAt(i));
        }


        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {
           
                if(contact.getFixtureA().getBody().getUserData() == userData && contact.getFixtureB().getBody().getUserData() != userData && contact.getFixtureB().getBody().getUserData() != userData2){
                    Colliding = true;
                }
                if(contact.getFixtureA().getBody().getUserData() == userData && contact.getFixtureB().getBody().getUserData() == userData2)
                    win = true;


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

        for(int i=0; i < enemyBodies.size();i++){
            Vector2 follow = new Vector2((playerBody.getX()-enemyBodies.elementAt(i).getX())/50,(playerBody.getY()-enemyBodies.elementAt(i).getY())/50);
            follow.limit(0.01f);
            enemyBodies.elementAt(i).applyForceToCenter(follow.x,follow.y,true);
        }

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

    public boolean getWin() {
        return win;
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
