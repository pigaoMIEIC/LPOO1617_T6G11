package com.mygdx.game.view.entities;

import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.EntityModel;

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.model.entities.EntityModel.ModelType.BALL;
import static com.mygdx.game.model.entities.EntityModel.ModelType.OTHERS;


/**
 * A factory for EntityView objects with cache
 */

public class ViewFactory {
    private static Map<EntityModel.ModelType, EntityView> cache =
            new HashMap<EntityModel.ModelType, EntityView>();

    public static EntityView makeView(SpaceBallsGame game, EntityModel model) {

        if (!cache.containsKey(model.getType())) {
            if (model.getType() == BALL)
                cache.put(model.getType(), new BallView(game));
            if (model.getType() == OTHERS)
                cache.put(model.getType(), new BallView(game));//ainda n há outros... mas um dia pode...
        }
        return cache.get(model.getType());
    }
}
