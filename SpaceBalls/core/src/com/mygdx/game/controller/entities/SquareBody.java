package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.StaticModel;

/**
 * Created by Eduardo on 27/05/2017.
 */

public class SquareBody extends StaticBody {

    public SquareBody(World world, StaticModel model, float restitution) {
        super(world, model);

        createFixture(body,new float[]{0,0,500,0,500,500,0,500},500,500,restitution);
    }
}
