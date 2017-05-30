package com.mygdx.game.model.entities;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.view.MenuView;

/**
 * Created by Tiago Neves on 17/05/2017.
 */

public class StaticModel extends EntityModel {
    @Override
    public ModelType getType() {
        return ModelType.OBS;
    }

    boolean dynamic;
    boolean drag;
    boolean highRebound;


    /**
     * StaticModel constructor
     */
    public StaticModel(float x, float y) {
        super(x, y);


        this.dynamic = false;
        this.drag = true;
        this.highRebound = true;
    }
}
