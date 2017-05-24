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
    private boolean dynamic;
    private boolean drag;
    private boolean highRebound;

    public StaticBallModel(float x, float y, float radius, boolean dynamic, boolean drag, boolean highRebound) {
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
