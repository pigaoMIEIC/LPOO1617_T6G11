package com.mygdx.game.model.entities;

/**
 * Created by Tiago Neves on 17/05/2017.
 */

public class EnemyModel extends EntityModel {
    @Override
    public ModelType getType() {
        return ModelType.ENEMY;
    }

    private float radius;
    private boolean dynamic;
    private boolean drag;
    private boolean highRebound;

    public EnemyModel(float x, float y, float radius, boolean highRebound) {
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
