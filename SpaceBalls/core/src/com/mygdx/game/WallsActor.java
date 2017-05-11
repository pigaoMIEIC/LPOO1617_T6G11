package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.mygdx.game.GameStage.VIEWPORT_WIDTH;
/**
 * Created by Tiago Neves on 21/04/2017.
 */

public class WallsActor extends Actor{
    /**
     * The texture used to draw this actor
     */
    private final Texture texture;

    private float height = 0.5f;

    private float width = VIEWPORT_WIDTH;

    private float ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());

    private float heigthM = VIEWPORT_WIDTH * ratio+height;

    WallsActor(SpaceBallsGame game){
        texture = game.getAssetManager().get("ground.jpg");
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    /**
     * Draws this actor
     *
     * @param batch the SpriteBatch used to draw this actor
     * @param parentAlpha the alpha component inherited from the father
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        //batch.draw(texture, 0, -height,0,0, (int)(VIEWPORT_WIDTH / PIXEL_TO_METER), (int)(.50f / PIXEL_TO_METER));
    }


    /**
     * Creates the ground body
     *
     * @param world the world this body belongs to
     * @return the body
     */
    Body createFloor(World world) {
        // Create the ball body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        // Create the ball body
        Body body = world.createBody(bodyDef);


        // Create rectangular shape
        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(width, height); // Viewport width and 50cm height
        body.setTransform(0, -height, 0); // Bottom left corner

        // Create ground fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.density = .5f;      // how heavy is the ground
        fixtureDef.friction =  .5f;    // how slippery is the ground
        fixtureDef.restitution =  1; // how bouncy is the ground

        // Attach fixture to body
        body.createFixture(fixtureDef);

        // Dispose of circle shape
        rectangle.dispose();

        return body;
    }


    Body createLeft(World world) {
        // Create the ball body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        // Create the ball body
        Body body = world.createBody(bodyDef);


        // Create rectangular shape
        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(1, heigthM); // Viewport width and 50cm height
        body.setTransform(-1, 0, 0); // Bottom left corner

        // Create ground fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.density = .5f;      // how heavy is the ground
        fixtureDef.friction =  .5f;    // how slippery is the ground
        fixtureDef.restitution =  1; // how bouncy is the ground

        // Attach fixture to body
        body.createFixture(fixtureDef);

        // Dispose of circle shape
        rectangle.dispose();

        return body;
    }

    Body createRight(World world) {
        // Create the ball body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        // Create the ball body
        Body body = world.createBody(bodyDef);


        // Create rectangular shape
        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(1, heigthM); // Viewport width and 50cm height
        body.setTransform(width+1, 0, 0); // Bottom left corner

        // Create ground fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.density = .5f;      // how heavy is the ground
        fixtureDef.friction =  .5f;    // how slippery is the ground
        fixtureDef.restitution =  1; // how bouncy is the ground

        // Attach fixture to body
        body.createFixture(fixtureDef);

        // Dispose of circle shape
        rectangle.dispose();

        return body;
    }

    Body createTop(World world) {
        // Create the ball body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        // Create the ball body
        Body body = world.createBody(bodyDef);


        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(width, height); // Viewport width and 50cm height
        body.setTransform(0, heigthM, 0); // Bottom left corner

        // Create ground fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.density = .5f;      // how heavy is the ground
        fixtureDef.friction =  .5f;    // how slippery is the ground
        fixtureDef.restitution =  1; // how bouncy is the ground

        // Attach fixture to body
        body.createFixture(fixtureDef);

        // Dispose of circle shape
        rectangle.dispose();

        return body;
    }
}
