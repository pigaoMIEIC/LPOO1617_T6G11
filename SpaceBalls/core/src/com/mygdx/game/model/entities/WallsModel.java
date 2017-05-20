package com.mygdx.game.model.entities;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.view.MenuView;

/**
 * Created by Tiago Neves on 17/05/2017.
 */

public class WallsModel extends EntityModel {
    @Override
    public ModelType getType() {
        return ModelType.BALL;
    }

    float length;
    boolean dynamic;
    boolean drag;
    boolean highRebound;

    public WallsModel(float x,float y) {
        super(x, y);

        this.length = length;
        this.dynamic = false;
        this.drag = true;
        this.highRebound = true;
    }

    public float getLength() {
        return length;
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
