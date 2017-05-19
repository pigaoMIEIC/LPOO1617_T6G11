package com.mygdx.game.model.entities;

/**
 * Created by Tiago Neves on 17/05/2017.
 */

public class BallModel extends EntityModel {
    @Override
    public ModelType getType() {
        return ModelType.BALL;
    }

    float radius;
    boolean dynamic;
    boolean drag;
    boolean highRebound;

    public BallModel(float x, float y, float radius, boolean dynamic, boolean drag, boolean highRebound) {
        super(x, y);
        this.radius = radius;
        this.dynamic = dynamic;
        this.drag = drag;
        this.highRebound = highRebound;
    }

    public float getRadius() {
        return radius;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public boolean hasDrag() {
        return drag;
    }

    public boolean hasHighRebound() {
        return highRebound;
    }
}
