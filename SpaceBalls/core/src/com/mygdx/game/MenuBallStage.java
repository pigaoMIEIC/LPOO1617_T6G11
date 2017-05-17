package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Tiago Neves on 21/04/2017.
 */

public class MenuBallStage extends Stage {
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

    private final float ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());


    int randn = 6;//number of random balls in start menu
    BallActor[] rballActors = new BallActor[randn];
    Body[] rballBodys = new Body[randn];
    Vector2[] positions = {
            new Vector2(0,0),
            new Vector2(VIEWPORT_WIDTH,0),
            new Vector2(0,VIEWPORT_WIDTH * ratio),
            new Vector2(VIEWPORT_WIDTH,VIEWPORT_WIDTH * ratio),
            new Vector2(VIEWPORT_WIDTH/4,(VIEWPORT_WIDTH * ratio)/2),
            new Vector2(VIEWPORT_WIDTH-VIEWPORT_WIDTH/4,(VIEWPORT_WIDTH * ratio)/2)
    };
    boolean first = true;



    MenuBallStage(SpaceBallsGame game) {


        // Set the viewport

        setViewport(new FitViewport(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ratio));

        // Load the textures
        game.getAssetManager().load("ball.png", Texture.class);
        game.getAssetManager().load("ground.jpg", Texture.class);
        game.getAssetManager().load("enemy.png", Texture.class);
        game.getAssetManager().finishLoading();

        world = new World(new Vector2(0, 0), false);

        ballActor = new BallActor(game,"ball.png", 0.11f);
        ballActor.setPosition((VIEWPORT_WIDTH) / 2 / PIXEL_TO_METER, (VIEWPORT_WIDTH * ratio) / 2 / PIXEL_TO_METER);
        addActor(ballActor);
        for (int i = 0; i < rballActors.length; i++) {
            rballActors[i] = new BallActor(game,"enemy.png", 0.11f);
//            float randomY = (float)Math.random() * VIEWPORT_WIDTH ;
//            float randomX = (float)Math.random() * (VIEWPORT_WIDTH * ratio);

            rballActors[i].setPosition(positions[i].x, positions[i].y);
            addActor(rballActors[i]);
            rballBodys[i] = rballActors[i].createBody(world,false,true,true);
            rballBodys[i].setTransform(positions[i].x, positions[i].y,0);
        }


        WallsActor wallActor = new WallsActor(game);
        wallActor.setPosition(20,60);
        addActor(wallActor);


        ballBody = ballActor.createBody(world,true,false,true);
        wallActor.createWallsBody(world);




    }


    @Override
    public void act(float delta) {
        super.act(delta);

        // Step the world
        world.step(delta, 6, 2);

        if(first) {
            double randomY = Math.random() * 10 + 1;
            double randomX = Math.random() * 10 + 1;
            Vector2 vector = new Vector2((float) randomX , (float) randomY );
            ballBody.applyForceToCenter(vector, true);
            first = false;
        }else  {
            float accelX = Gdx.input.getAccelerometerX();
            float accelY = Gdx.input.getAccelerometerY();
            Vector2 vector = new Vector2(accelY / 50, -accelX / 50);
            ballBody.applyForceToCenter(vector, true);

        }

        ballActor.setPosition(ballBody.getPosition().x / PIXEL_TO_METER, ballBody.getPosition().y / PIXEL_TO_METER);

        for (int i = 0; i < rballActors.length; i++) {
            rballActors[i].setPosition(rballBodys[i].getPosition().x / PIXEL_TO_METER, rballBodys[i].getPosition().y / PIXEL_TO_METER);
        }


    }


}


