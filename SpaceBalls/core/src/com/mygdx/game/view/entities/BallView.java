package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.controller.MenuController;
import com.mygdx.game.model.MenuModel;
import com.mygdx.game.model.entities.EntityModel;

/**
 * Created by Tiago Neves on 18/05/2017.
 */

public class BallView extends  EntityView{
    public BallView(SpaceBallsGame game) {
        super(game);
    }

    @Override
    public Sprite createSprite(SpaceBallsGame game) {
        Texture texture = game.getAssetManager().get("ball.png");
        float radius = MenuModel.getInstance().getBall().getRadius();

        float factor = texture.getWidth()/(radius/PIXEL_TO_METER);
        Sprite sprite =  new Sprite(texture, texture.getWidth(), texture.getHeight());
        sprite.setScale(factor);

        return sprite;
    }

    @Override
    public void update(EntityModel model) {
        super.update(model);
    }
}
