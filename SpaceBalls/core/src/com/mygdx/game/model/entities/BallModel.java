package com.mygdx.game.model.entities;

/**
 * Created by Tiago Neves on 17/05/2017.
 */

public class BallModel extends EntityModel {
    @Override
    public ModelType getType() {
        return ModelType.BALL;
    }

    private float radius;
    private boolean dynamic;
    private boolean drag;
    private boolean highRebound;
    private boolean fixed;

    public BallModel(float x, float y, float radius, boolean dynamic, boolean drag, boolean highRebound) {
        super(x, y);
        this.radius = radius;
        this.dynamic = dynamic;
        this.drag = drag;
        this.fixed = true;
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
