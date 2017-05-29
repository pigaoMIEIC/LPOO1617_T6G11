package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.model.MenuModel;
import com.mygdx.game.model.SandBoxModel;
import com.mygdx.game.model.entities.EntityModel;

/**
 * Created by Tiago Neves on 22/05/2017.
 */

public class EnemyView extends  EntityView{

    /**
     * Constructor for the view
     *
     * @param game The game this view belongs to. Needed to access the asset manager to get textures.
     */
    public EnemyView(SpaceBallsGame game) {
        sprite = createSprite(game);
    }

    /**
     * Method that calls the super class method with specific parameters
     * @param game The game this view belongs to. Needed to access the asset manager to get textures.
     * @return Returns the sprite to be rendered
     */
    public Sprite createSprite(SpaceBallsGame game) {
        float radius = SandBoxModel.getInstance().getEnemyModel(0).getRadius();

        return super.createSprite(game,"enemy.png",radius);
    }
}
