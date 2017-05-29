package com.mygdx.game.model.entities;

/**
 * Created by Tiago Neves on 22/05/2017.
 */

public class StaticBallModel extends EntityModel{
    @Override
    public EntityModel.ModelType getType() {
        return ModelType.STATIC;
    }

    private float radius;
    private boolean highRebound;

    public StaticBallModel(float x, float y, float radius, boolean highRebound) {
        super(x, y);
        this.radius = radius;
        this.highRebound = highRebound;
    }

    public float getRadius() {
        return radius;
    }

    public boolean hasHighRebound() {
        return highRebound;
    }
}
