package com.mygdx.game.controller.entities;


import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.BallModel;

/**
 * A concrete representation of an EntityBody
 * representing the player space ship.
 */
public class BallBody extends EntityBody {
    /**
     * Constructs a space ship body according to
     * a space ship model.
     *
     * @param world the physical world this space ship belongs to.
     * @param model the model representing this space ship.
     */
    public BallBody(World world, BallModel model) {
        super(world, model);

        float density = 0.5f, friction = 0.4f, restitution = 0.5f;
        int width = 75, height = 75;

        // Left winglet
        createFixture(body, new float[]{
                12,28, 15,28, 19,32, 19,42, 13,43
        }, width, height, density, friction, restitution, SHIP_BODY, (short) (ASTEROID_BODY | SHIP_BODY | BULLET_BODY));
    }
}

