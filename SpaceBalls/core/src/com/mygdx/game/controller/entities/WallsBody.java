package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.StaticModel;
import com.mygdx.game.view.MenuView;

/**
 * Created by Tiago Neves on 17/05/2017.
 */

public class WallsBody extends StaticBody{
    /**
     * Constructs a space ship body according to
     * a space ship model.
     *  @param world the physical world this space ship belongs to.
     * @param model the model representing this space ship.
     * @param restitution
     */
    public WallsBody(World world, StaticModel model, float restitution) {
        super(world, model);

        float density = 1f, friction = 0f;

        //Wall shape
        createWall(body, density, friction, restitution);
    }

    /**
     * Helper method to create a polygon fixture represented by a set of vertexes.
     * @param body The body the fixture is to be attached to.
     * @param density The density of the fixture. How heavy it is in relation to its area.
     * @param friction The friction of the fixture. How slippery it is.
     * @param restitution The restitution of the fixture. How much it bounces.
     */
    public final void createWall(Body body, float density, float friction, float restitution) {

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


}
