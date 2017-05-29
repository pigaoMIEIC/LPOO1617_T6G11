package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.model.MenuModel;

/**
 * Created by Tiago Neves on 29/05/2017.
 */

public class ObsView extends EntityView{

    /**
     * Constructor for the view
     *
     * @param game The game this view belongs to. Needed to access the asset manager to get textures.
     */
    public ObsView(SpaceBallsGame game) {
        sprite = createSprite(game);
    }

    /**
     * Method that calls the super class method with specific parameters
     * @param game The game this view belongs to. Needed to access the asset manager to get textures.
     * @return Returns the sprite to be rendered
     */
    public Sprite createSprite(SpaceBallsGame game) {
        float radius = 0.25f;
        return super.createSprite(game,"whiteSquare.png",radius);
    }
}
