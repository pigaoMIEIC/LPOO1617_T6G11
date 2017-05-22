package com.mygdx.game.controller.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.WallsModel;
import com.mygdx.game.view.MenuView;

import static com.mygdx.game.GameStage.VIEWPORT_WIDTH;

/**
 * Created by Tiago Neves on 17/05/2017.
 */

public class WallsBody extends StaticBody{
    /**
     * Constructs a space ship body according to
     * a space ship model.
     *
     * @param world the physical world this space ship belongs to.
     * @param model the model representing this space ship.
     */
    public WallsBody(World world, WallsModel model) {
        super(world, model);

        float density = 1f, friction = 0f, restitution = 1f;

        //Wall shape
        createWall(body, 0.08f, density, friction, restitution);
    }

    /**
     * Helper method to create a polygon fixture represented by a set of vertexes.
     * @param body The body the fixture is to be attached to.
     * @param density The density of the fixture. How heavy it is in relation to its area.
     * @param friction The friction of the fixture. How slippery it is.
     * @param restitution The restitution of the fixture. How much it bounces.
     * @param radius The radius of the circle fixture.
     */
    public final void createWall(Body body, float radius, float density, float friction, float restitution) {

        float width = MenuView.VIEWPORT_WIDTH;
        float height = MenuView.VIEWPORT_WIDTH*MenuView.RATIO;

        //Create Shapes
        EdgeShape leftWallShape = new EdgeShape();
        leftWallShape.set(0, 0, 0, height);

        EdgeShape rightWallShape = new EdgeShape();
        rightWallShape.set(width, 0,width, height);

        EdgeShape ceilingShape = new EdgeShape();
        ceilingShape.set(0, height, width, height);

        EdgeShape floorShape = new EdgeShape();
        floorShape.set(0, 0,width, 0);

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

//    private float ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());

//    private float heigthM = VIEWPORT_WIDTH * ratio;


//    void createWallsBody(World world) {
//        //Create wall definition
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.StaticBody;
//
//        // Create walls body
//        Body walls = world.createBody(bodyDef);
//        walls.setTransform(0, 0, 0);
//
//        //Create Shapes
//        EdgeShape leftWallShape = new EdgeShape();
//        leftWallShape.set(0, 0, 0, heigthM);
//
//        EdgeShape rightWallShape = new EdgeShape();
//        rightWallShape.set(VIEWPORT_WIDTH, 0, VIEWPORT_WIDTH, heigthM);
//
//        EdgeShape ceilingShape = new EdgeShape();
//        ceilingShape.set(0, heigthM, VIEWPORT_WIDTH, heigthM);
//
//        EdgeShape floorShape = new EdgeShape();
//        floorShape.set(0, 0,VIEWPORT_WIDTH, 0);
//
//        //Create Fixtures
//        FixtureDef leftWall = new FixtureDef();
//        leftWall.shape = leftWallShape;
//        leftWall.density = 0f;
//        leftWall.friction = 0f;
//        leftWall.restitution = 1f;
//
//        FixtureDef rightWall = new FixtureDef();
//        rightWall.shape = rightWallShape;
//        rightWall.density = 0f;
//        rightWall.friction = 0f;
//        rightWall.restitution = 1f;
//
//        FixtureDef ceiling = new FixtureDef();
//        ceiling.shape = ceilingShape;
//        ceiling.density = 0f;
//        ceiling.friction = 0f;
//        ceiling.restitution = 1f;
//
//        FixtureDef floor = new FixtureDef();
//        floor.shape = floorShape;
//        floor.density = 0f;
//        floor.friction = 0f;
//        floor.restitution = 1f;
//
//        //Attach fixtures to body
//        walls.createFixture(leftWall);
//        walls.createFixture(rightWall);
//        walls.createFixture(ceiling);
//        walls.createFixture(floor);
//
//
//
//        //Dispose shape
//        ceilingShape.dispose();
//        leftWallShape.dispose();
//        rightWallShape.dispose();
//        floorShape.dispose();
//    }

}
