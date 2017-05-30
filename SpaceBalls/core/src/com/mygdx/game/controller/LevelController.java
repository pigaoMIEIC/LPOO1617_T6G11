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

public class LevelController extends Controller{
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

    static LevelType.levelType currLevel = LevelType.levelType.ONE;

    private boolean win;

    private float accelY = 0;

    private float accelX = 0;

    /**
     * @return LevelController instance
     */
    public static LevelController getInstance(LevelType.levelType newLevel) {
        if (instance == null)
            instance = new LevelController();
        if(currLevel!=newLevel){
            currLevel = newLevel;
            instance = new LevelController();
        }
        return instance;
    }

    /**
     * LevelController constructor
     */
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



    /**
     * @return OptionsController physics world
     */
    public World getWorld() {
        return world;
    }

    /**
     * @return true if the ball has collided with enemy balls,false otherwise
     */
    public boolean isColliding() {
        return Colliding;
    }

    /**
     * @param colliding
     * Sets the colliding field
     */
    public void setColliding(boolean colliding) {
        Colliding = colliding;
    }

    /**
     * Delete the instance
     */
    public void delete(){
        this.instance = null;
        LevelModel.getInstance(currLevel).delete();
    }

    /**
     * @return true if the player won
     */
    public boolean getWin() {
        return win;
    }

    /**
     * @param sensitivity
     * Sets the controller sensitivity
     */
    public void setSensitivity(float sensitivity) {
        this.sensitivity = sensitivity;
    }

    /**
     * @param joystick true if the input is by joystick
     */
    public void setJoystick(boolean joystick) {
        this.joystick = joystick;
    }

    /**
     * @param accelarationX
     * @param accelarationY
     * Sets the playerBall acceleration
     */
    public void setAccelaration(float accelarationX,float accelarationY){
        this.accelX = accelarationX;
        this.accelY = accelarationY;
    }
}
