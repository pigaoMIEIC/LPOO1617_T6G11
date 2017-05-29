package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.model.MenuModel;
import com.mygdx.game.model.entities.EntityModel;

/**
 * Created by Tiago Neves on 18/05/2017.
 */

public class BallView extends  EntityView{
    /**
     * Constructor for the view
     *
     * @param game The game this view belongs to. Needed to access the asset manager to get textures.
     */
    public BallView(SpaceBallsGame game) {
        sprite = createSprite(game);
    }

    /**
     * Method that calls the super class method with specific parameters
     * @param game The game this view belongs to. Needed to access the asset manager to get textures.
     * @return Returns the sprite to be rendered
     */
    public Sprite createSprite(SpaceBallsGame game) {
        float radius = MenuModel.getInstance().getBallModel(1).getRadius();

        return super.createSprite(game,"ball.png",radius);
    }
}
