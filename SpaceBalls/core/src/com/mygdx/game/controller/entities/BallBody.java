package com.mygdx.game.controller.entities;


import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.EnemyModel;
import com.mygdx.game.model.entities.StaticBallModel;

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

        float density = 0.5f, friction = 0f, restitution = 1f;

        if (model.hasHighRebound()) {
            restitution = 1f;
        }else restitution = 0.3f;

        //Ball shape
        createCircle(body, model.getRadius(), density, friction, restitution, SHIP_BODY, (short) (ASTEROID_BODY | SHIP_BODY | BULLET_BODY));


    }

    public BallBody(World world, StaticBallModel staticBallModel) {
        super(world, staticBallModel);

        float density = 0.5f, friction = 0f, restitution;

        if (staticBallModel.hasHighRebound()) {
            restitution = 1f;
        }else restitution = 0f;
        //Ball shape
        createCircle(body, staticBallModel.getRadius(), density, friction, restitution, SHIP_BODY, (short) (ASTEROID_BODY | SHIP_BODY | BULLET_BODY));
    }

    public BallBody(World world, EnemyModel enemyModel) {
        super(world, enemyModel);

        float density = 0.5f, friction = 0f, restitution;

        if (enemyModel.hasHighRebound()) {
            restitution = 1f;
        }else restitution = 0f;
        //Ball shape
        createCircle(body, enemyModel.getRadius(), density, friction, restitution, SHIP_BODY, (short) (ASTEROID_BODY | SHIP_BODY | BULLET_BODY));
    }
}

