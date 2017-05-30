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
import com.mygdx.game.model.SandBoxModel;
import com.mygdx.game.model.entities.EntityModel;

import java.util.Random;
import java.util.Vector;

import static com.mygdx.game.view.MenuView.RATIO;
import static com.mygdx.game.view.MenuView.VIEWPORT_WIDTH;

/**
 * Created by Tiago Neves on 22/05/2017.
 */

public class SandBoxController {
    private float accumulator;

    private float seconds;

    private static SandBoxController instance;

    private World world;
    private Random forceRand = new Random();

    private final WallsBody wallsBody;

    private Vector<BallBody> rballBodys = new Vector<BallBody>();

    BallBody playerBody;

    private boolean Colliding;

    private Object userData;

    boolean joystick = true;

    float sensitivity = 60;

    float offsetX;
    float offsetY;

    float radius;


    /**
     * SandBoxController constructor
     */
    SandBoxController() {
            rballBodys.removeAllElements();
            SandBoxModel sandBoxModel = SandBoxModel.getInstance();

            world = new World(new Vector2(0, 0), false);

            playerBody = new BallBody(world,sandBoxModel.getPlayerModel());
            playerBody.setDrag(0.5f);

            userData = playerBody.getUserData();


            rballBodys.addElement(new BallBody(world, SandBoxModel.getInstance().getEnemyModel(0)));
            radius = sandBoxModel.getEnemyModel(0).getRadius();
            rballBodys.elementAt(0).setType(BodyDef.BodyType.DynamicBody);

            wallsBody = new WallsBody(world,SandBoxModel.getInstance().getStaticModel(), 0.5f);

            world.setContactListener(new ContactListener() {

                @Override
                public void beginContact(Contact contact) {
                    for (int i = 0; i < SandBoxModel.getInstance().getnBalls(); i++) {
                        if(contact.getFixtureA().getBody().getUserData() == userData && contact.getFixtureB().getBody().getUserData() != userData){
                            Colliding = true;
                        }
                    }


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
     * @return SandBoxController instance
     */
    public static SandBoxController getInstance() {
        if (instance == null){
            instance = new SandBoxController();
        }
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



        if(seconds >= 5f){
            SandBoxModel.getInstance().nextLevel();
            seconds = 0;
        }


        for(int i=0; i < rballBodys.size();i++){
            Vector2 follow = new Vector2((playerBody.getX()-rballBodys.elementAt(i).getX())/50,(playerBody.getY()-rballBodys.elementAt(i).getY())/50);
            follow.limit(0.01f);
            rballBodys.elementAt(i).applyForceToCenter(follow.x,follow.y,true);
        }



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
        if(joystick)
         playerBody.applyForceToCenter(sensitivity*x,sensitivity*y, true);
        else{
            System.out.println("sensitivity sand " + sensitivity);
            System.out.println("offsetx sand  " + offsetX);
            System.out.println("offsetY sand  " + offsetY);
            System.out.println("force sand  " + (-x *sensitivity)/35);
            Vector2 vector = new Vector2((y *sensitivity)/35 - offsetY, (-x *sensitivity)/35 + offsetX);
            playerBody.applyForceToCenter(vector.x,vector.y, true);
        }
    }

    /**
     * @return controller's physics world
     */
    public World getWorld() {
        return world;
    }


    /**
     * Method to start the next level
     */
    public void nextLevel(int nBall){

        Random r = new Random();
        for(int i = 0; i < nBall-1;i++) {
            float x = (r.nextFloat()*(VIEWPORT_WIDTH-radius)) + radius;// + radius;
            float y =(r.nextFloat()*(VIEWPORT_WIDTH*RATIO - radius)) + radius;// + radius ;
            this.rballBodys.elementAt(i).setTransform(x,y,0);
            this.rballBodys.elementAt(i).setLinearVelocity(0);
        }
//
        rballBodys.addElement(new BallBody(world, SandBoxModel.getInstance().getEnemyModel(nBall-1)));
        rballBodys.elementAt(nBall-1).setType(BodyDef.BodyType.DynamicBody);
        playerBody.setDrag(0.6f);
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
        SandBoxModel.getInstance().delete();
    }

    /**
     * @return seconds spent on the current level
     */
    public float getSeconds() {
        return seconds;
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
     * @param offsetX
     * @param offsetY
     * Sets the accelerometer offset
     */
    public void setOffset(float offsetX,float offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

}


