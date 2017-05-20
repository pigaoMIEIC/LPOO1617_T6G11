package com.mygdx.game.controller.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.WallsModel;

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

        float density = 0.5f, friction = 0f, restitution = 1f;

        //Wall shape
        createWall(body, 0.08f, density, friction, restitution);
    }

    private float ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());

    private float heigthM = VIEWPORT_WIDTH * ratio;

//    public WallBody(World world) {
//        createWallsBody(world);
//    }

    void createWallsBody(World world) {
        //Create wall definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        // Create walls body
        Body walls = world.createBody(bodyDef);
        walls.setTransform(0, 0, 0);

        //Create Shapes
        EdgeShape leftWallShape = new EdgeShape();
        leftWallShape.set(0, 0, 0, heigthM);

        EdgeShape rightWallShape = new EdgeShape();
        rightWallShape.set(VIEWPORT_WIDTH, 0, VIEWPORT_WIDTH, heigthM);

        EdgeShape ceilingShape = new EdgeShape();
        ceilingShape.set(0, heigthM, VIEWPORT_WIDTH, heigthM);

        EdgeShape floorShape = new EdgeShape();
        floorShape.set(0, 0,VIEWPORT_WIDTH, 0);

        //Create Fixtures
        FixtureDef leftWall = new FixtureDef();
        leftWall.shape = leftWallShape;
        leftWall.density = 0f;
        leftWall.friction = 0f;
        leftWall.restitution = 1f;

        FixtureDef rightWall = new FixtureDef();
        rightWall.shape = rightWallShape;
        rightWall.density = 0f;
        rightWall.friction = 0f;
        rightWall.restitution = 1f;

        FixtureDef ceiling = new FixtureDef();
        ceiling.shape = ceilingShape;
        ceiling.density = 0f;
        ceiling.friction = 0f;
        ceiling.restitution = 1f;

        FixtureDef floor = new FixtureDef();
        floor.shape = floorShape;
        floor.density = 0f;
        floor.friction = 0f;
        floor.restitution = 1f;

        //Attach fixtures to body
        walls.createFixture(leftWall);
        walls.createFixture(rightWall);
        walls.createFixture(ceiling);
        walls.createFixture(floor);



        //Dispose shape
        ceilingShape.dispose();
        leftWallShape.dispose();
        rightWallShape.dispose();
        floorShape.dispose();
    }

}
