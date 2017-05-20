package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.EntityModel;

/**
 * Wrapper class that represents an abstract physical
 * body supported by a Box2D body.
 */
public abstract class StaticBody {

    /**
     * The Box2D body that supports this body.
     */
    final Body body;

    /**
     * Constructs a body representing a model in a certain world.
     *
     * @param world The world this body lives on.
     * @param model The model representing the body.
     */
    StaticBody(World world, EntityModel model) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(model.getX(), model.getY());

        body = world.createBody(bodyDef);
        body.setUserData(model);
    }

    /**
     * Helper method to create a polygon fixture represented by a set of vertexes.
     * @param body The body the fixture is to be attached to.
     * @param density The density of the fixture. How heavy it is in relation to its area.
     * @param friction The friction of the fixture. How slippery it is.
     * @param restitution The restitution of the fixture. How much it bounces.
     * @param radius The radius of the circle fixture.
     */
    final void createWall(Body body, float radius, float density, float friction, float restitution) {

        CircleShape circle = new CircleShape();
        circle.setRadius(radius);
        float k = 2f;
        float height_1 = 1f;

        //Create Shapes
        EdgeShape leftWallShape = new EdgeShape();
        leftWallShape.set(0, 0, 0, height_1);

        EdgeShape rightWallShape = new EdgeShape();
        rightWallShape.set(k, 0,k, height_1);

        EdgeShape ceilingShape = new EdgeShape();
        ceilingShape.set(0, height_1, k, height_1);

        EdgeShape floorShape = new EdgeShape();
        floorShape.set(0, 0,k, 0);

        //Create Fixtures
        FixtureDef leftWall = new FixtureDef();
        leftWall.shape = leftWallShape;
        leftWall.density = density;
        leftWall.friction = friction;
        leftWall.restitution = restitution;

        FixtureDef rightWall = new FixtureDef();
        rightWall.shape = rightWallShape;
        rightWall.density = density;
        rightWall.friction = friction;
        rightWall.restitution = restitution;

        FixtureDef ceiling = new FixtureDef();
        ceiling.shape = ceilingShape;
        ceiling.density = density;
        ceiling.friction = friction;
        ceiling.restitution = restitution;

        FixtureDef floor = new FixtureDef();
        floor.shape = floorShape;
        floor.density = density;
        floor.friction = friction;
        floor.restitution = restitution;

        //Attach fixtures to body
        body.createFixture(leftWall);
        body.createFixture(rightWall);
        body.createFixture(ceiling);
        body.createFixture(floor);

        body.setActive(true);



        //Dispose shape
        ceilingShape.dispose();
        leftWallShape.dispose();
        rightWallShape.dispose();
        floorShape.dispose();
    }

    /**
     * Wraps the getX method from the Box2D body class.
     *
     * @return the x-coordinate of this body.
     */
    public float getX() {
        return body.getPosition().x;
    }

    /**
     * Wraps the getY method from the Box2D body class.
     *
     * @return the y-coordinate of this body.
     */
    public float getY() {
        return body.getPosition().y;
    }

    /**
     * Wraps the getAngle method from the Box2D body class.
     *
     * @return the angle of rotation of this body.
     */
    public float getAngle() {
        return body.getAngle();
    }

    /**
     * Wraps the setTransform method from the Box2D body class.
     *
     * @param x the new x-coordinate for this body
     * @param y the new y-coordinate for this body
     * @param angle the new rotation angle for this body
     */
    public void setTransform(float x, float y, float angle) {
        body.setTransform(x, y, angle);
    }

    /**
     * Sets the angular velocity of this object in the direction it is rotated.
     *
     * @param velocity the new linear velocity angle for this body
     */
    public void setLinearVelocity(float velocity) {
        body.setLinearVelocity((float)(velocity * -Math.sin(getAngle())), (float) (velocity * Math.cos(getAngle())));
    }

    /**
     * Wraps the setAngularVelocity method from the Box2D body class.
     *
     * @param omega the new angular velocity angle for this body
     */
    public void setAngularVelocity(float omega) {
        body.setAngularVelocity(omega);
    }

    /**
     * Wraps the applyForceToCenter method from the Box2D body class.
     *
     * @param forceX the x-component of the force to be applied
     * @param forceY the y-component of the force to be applied
     * @param awake should the body be awaken
     */
    public void applyForceToCenter(float forceX, float forceY, boolean awake) {
        body.applyForceToCenter(forceX, forceY, awake);
    }

    /**
     * Wraps the getUserData method from the Box2D body class.
     *
     * @return the user data
     */
    public Object getUserData() {
        return body.getUserData();
    }
}
