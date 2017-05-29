package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.LevelType;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.model.LevelModel;
import com.mygdx.game.model.SandBoxModel;
import com.mygdx.game.model.entities.EntityModel;

import static com.mygdx.game.view.entities.EntityView.PIXEL_TO_METER;

/**
 * Created by Tiago Neves on 28/05/2017.
 */

public class EndView extends EntityView{

    /**
     * Constructor for the view
     *
     * @param game The game this view belongs to. Needed to access the asset manager to get textures.
     */
    public EndView(SpaceBallsGame game,LevelType.levelType currLevel) {
        sprite = createSprite(game,currLevel);
    }

    /**
     * Method that calls the super class method with specific parameters
     * @param game The game this view belongs to. Needed to access the asset manager to get textures.
     * @return Returns the sprite to be rendered
     */
    public Sprite createSprite(SpaceBallsGame game, LevelType.levelType currLevel) {
        float radius = LevelModel.getInstance(currLevel).getEndBall().getRadius();

        return super.createSprite(game,"end.png",radius);
    }
}
