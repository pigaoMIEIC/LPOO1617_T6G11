package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.model.MenuModel;
import com.mygdx.game.model.entities.EntityModel;

import static com.mygdx.game.view.MenuView.PIXEL_TO_METER;

/**
 * Created by Tiago Neves on 22/05/2017.
 */

public class StaticBallView extends  EntityView{

    /**
     * Constructor for the view
     *
     * @param game Game from which the texture will be assigned
     */
    public StaticBallView(SpaceBallsGame game) {
        sprite = createSprite(game);
    }

    /**
     * Method that calls the super class method with specific parameters
     * @param game Game from which the texture will be assigned
     * @return Returns the sprite to be rendered
     */
    public Sprite createSprite(SpaceBallsGame game) {
        float radius = MenuModel.getInstance().getStaticBallModel(1).getRadius();
        return super.createSprite(game,"enemy.png",radius);
    }
}
