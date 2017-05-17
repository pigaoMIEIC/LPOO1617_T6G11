package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sun.org.apache.xpath.internal.operations.String;

import static com.mygdx.game.GameStage.VIEWPORT_WIDTH;

/**
 * Created by Tiago Neves on 21/04/2017.
 */

public class BallActor extends Actor{
    /**
     * The sprite used to draw the ball.
     */
    private final Sprite sprite;
    private final float radius;

    /**
     * Returns a ball actor.
     * @param game the game the actor belongs to
     * @param texName
     * @param radius
     */
    BallActor(SpaceBallsGame game, java.lang.String texName, float radius) {
        this.radius = radius;

        System.out.println(this.radius);
        Texture texture = game.getAssetManager().get(texName);
        sprite = new Sprite(texture);

        // Necessary so that inputs events are registered correctly
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());

        // Necessary so that rotations are correctly processed
        setOrigin(getWidth() / 2, getHeight() / 2);
        sprite.setOrigin(getWidth() / 2, getHeight() / 2);

    }

    /**
     * Set the position of the ball center (instead of
     * bottom-left corner)
     *
     * @param x the x-coordinate of the center
     * @param y the y-coordinate of the center
     */
    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x - getWidth() / 2, y - getHeight() / 2);
    }

    /**
     * Updates the sprite position when the actor moves
     */
    @Override
    protected void positionChanged() {
        super.positionChanged();
        sprite.setPosition(getX(), getY());
    }

    /**
     * Updates the sprite rotation when the actor rotates
     */
    @Override
    protected void rotationChanged() {
        super.rotationChanged();
        sprite.setRotation(getRotation());
    }

    /**
     * Draws the sprite
     *
     * @param batch the SpriteBatch used to draw the sprite
     * @param parentAlpha the alpha component inherited from the father
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setColor(getColor());
        sprite.draw(batch);
    }

    /**
     * Creates this actor body
     *
     * @param world the world this body belongs to
     * @return the body
     */
    Body createBody(World world,boolean dynamic,boolean drag,boolean highRebound) {
        // Create the ball body definition
        BodyDef bodyDef = new BodyDef();
        if(dynamic) {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }else bodyDef.type = BodyDef.BodyType.StaticBody;
        if(drag) {
            bodyDef.angularDamping = 0.2f;
            bodyDef.linearDamping = 0.2f;
        }
        // Create the ball body
        float ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
        Body body = world.createBody(bodyDef);
        body.setTransform(VIEWPORT_WIDTH / 2, (VIEWPORT_WIDTH * ratio) / 2, 0); // Middle of the viewport, no rotation

        // Create circle shape
        CircleShape circle = new CircleShape();
        circle.setRadius(this.radius); // 22cm / 2

        // Create ball fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = .5f;      // how heavy is the ball
        fixtureDef.friction =  0;    // how slippery is the ball
        if(highRebound)
            fixtureDef.restitution =  1; // how bouncy is the ball
        else fixtureDef.restitution =  0.9f;

        // Attach fixture to body
        body.createFixture(fixtureDef);

        // Dispose of circle shape
        circle.dispose();

        return body;
    }
}
