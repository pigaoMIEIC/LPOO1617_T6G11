package com.mygdx.game.view.entities;

import com.mygdx.game.LevelType;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.EntityModel;
import com.mygdx.game.view.LevelView;

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.model.entities.EntityModel.ModelType.BALL;
import static com.mygdx.game.model.entities.EntityModel.ModelType.END;
import static com.mygdx.game.model.entities.EntityModel.ModelType.ENEMY;
import static com.mygdx.game.model.entities.EntityModel.ModelType.OBS;
import static com.mygdx.game.model.entities.EntityModel.ModelType.STATIC;


/**
 * A factory for EntityView objects with cache
 */

public class ViewFactory {
    private static Map<EntityModel.ModelType, EntityView> cache =
            new HashMap<EntityModel.ModelType, EntityView>();


    /**
     *
     * @param game The game this view belongs to. Needed to access the asset manager to get textures.
     * @param model The model to be displayed
     * @param currLevel The current level type. Used in case there is an EndBall model to access teh ball information
     * @return Returns the EntityView that will be displayed
     */
    public static EntityView makeView(SpaceBallsGame game, EntityModel model, LevelType.levelType currLevel) {

        if (!cache.containsKey(model.getType())) {
            if (model.getType() == BALL)
                cache.put(model.getType(), new BallView(game));
            if (model.getType() == STATIC)
                cache.put(model.getType(), new StaticBallView(game));
            if (model.getType() == ENEMY)
                cache.put(model.getType(), new EnemyView(game));
            if(model.getType() == END)
                cache.put(model.getType(), new EndView(game,currLevel));
            if(model.getType() == OBS)
                cache.put(model.getType(), new ObsView(game));
        }
        return cache.get(model.getType());
    }
}
