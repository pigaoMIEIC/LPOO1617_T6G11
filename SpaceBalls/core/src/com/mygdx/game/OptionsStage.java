package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Tiago Neves on 21/04/2017.
 */

public class OptionsStage extends Stage{
    /**
     * Viewport width in meters. Height depends on screen ratio
     */
    static final int VIEWPORT_WIDTH = 4;

    /**
     * A football is 22cm in diameter and the sprite has a width of 200px
     */
    static final float PIXEL_TO_METER = 0.22f / 200;

    /**
     * The physical world
     */
    private final World world;

    /**
     * The ball body
     */
    private final Body ballBody;

    /**
     * The ball actor
     */
    private final BallActor ballActor;

    private final Body limitBody;

    private  final OrthographicCamera camera;

    private final Box2DDebugRenderer debugRenderer;

    private Matrix4 debugCamera;

    boolean joystick = false;

    OptionsStage(SpaceBallsGame game) {
        float ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
        setViewport(new FitViewport(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ratio));

        camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER,VIEWPORT_WIDTH / PIXEL_TO_METER * ratio);
        //camera.position.set(new Vector3(-camera.viewportWidth /2,-camera.viewportHeight /2 ,0));

        //Create debug camera
        debugRenderer = new Box2DDebugRenderer();
        debugCamera = camera.combined.cpy();
        debugCamera.scl(1 / PIXEL_TO_METER);

        boolean availableAccelerometers = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        if(!availableAccelerometers){
            joystick = true;
        }
        // Set the viewport



        // Load the textures
        game.getAssetManager().load("ball.png", Texture.class);
        game.getAssetManager().load("ground.jpg", Texture.class);
        game.getAssetManager().load("transparent.png", Texture.class);
        game.getAssetManager().finishLoading();

        ballActor = new BallActor(game,"ball.png", 0.11f);
        ballActor.setPosition((VIEWPORT_WIDTH) / 2 / PIXEL_TO_METER, (VIEWPORT_WIDTH * ratio) / 2 / PIXEL_TO_METER);
        addActor(ballActor);


        world = new World(new Vector2(0, 0), true);

        WallsActor wallActor = new WallsActor(game);
        wallActor.setPosition((VIEWPORT_WIDTH) / 2, (VIEWPORT_WIDTH * ratio) / 2 / PIXEL_TO_METER);
        addActor(wallActor);
        wallActor.createWallsBody(world);




        AlphaAction alphaAction = new AlphaAction();
        alphaAction.setAlpha(.5f);
        alphaAction.setDuration(10);
        ballActor.addAction(alphaAction);


        ballBody = ballActor.createBody(world,true,true,true);
        limitBody = ballActor.createBody(world,true,true,false);


        // Touch events
        ballActor.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //ballBody.applyForceToCenter(0, 2, true);
                return true;
            }
        });

        // Fling events
        ballActor.addListener(new ActorGestureListener(){
            @Override
            public void fling(InputEvent event, float velocityX, float velocityY, int button) {
                Vector2 vector = new Vector2(velocityX / 1000, velocityY / 1000);
                vector.rotateRad(ballBody.getAngle());
                ballBody.applyForceToCenter(vector, true);
            }
        });



    }


    @Override
    public void act(float delta) {
        super.act(delta);

        // Step the world
        world.step(delta, 6, 2);
        float accelX = Gdx.input.getAccelerometerX();
        float accelY = Gdx.input.getAccelerometerY();
        Vector2 vector = new Vector2(accelY / 100, -accelX / 100);
        Vector2 vector2 = new Vector2(vector.x*9.8f * 2,vector.y*9.8f * 2);
        vector2.limit(0.8f);
        float x = vector2.x +VIEWPORT_WIDTH/2  - 0.11f+ VIEWPORT_WIDTH/4;
        float y = vector2.y  + VIEWPORT_WIDTH/4.4f;



        ballBody.setTransform(x,y,0);

        // Update the ball actor position
        ballActor.setRotation((float) Math.toDegrees(ballBody.getAngle()));
        ballActor.setPosition(ballBody.getPosition().x / PIXEL_TO_METER, ballBody.getPosition().y / PIXEL_TO_METER);

        debugCamera = camera.combined.cpy();
        debugCamera.scl(1 / PIXEL_TO_METER);
        debugRenderer.render(world, debugCamera);
    }
}
