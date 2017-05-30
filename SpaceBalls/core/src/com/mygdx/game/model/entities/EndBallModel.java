package com.mygdx.game.model.entities;

/**
 * Created by Tiago Neves on 17/05/2017.
 */

public class EndBallModel extends EntityModel {
    private final float radius;

    @Override
    public ModelType getType() {
        return ModelType.END;
    }

    float length;
    boolean dynamic;
    boolean drag;
    boolean highRebound;


    /**
     * EndBallModel constructor
     */
    public EndBallModel(float x, float y,float radius) {
        super(x, y);

        this.radius = radius;
        this.dynamic = false;
        this.drag = true;
        this.highRebound = true;
    }

    /**
     * @return ball radius
     */
    public float getRadius() {
        return radius;
    }
}
