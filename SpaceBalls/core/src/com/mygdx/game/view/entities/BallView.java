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
    public BallView(SpaceBallsGame game) {
        sprite = createSprite(game);
    }

    public Sprite createSprite(SpaceBallsGame game) {
//        Texture texture = game.getAssetManager().get("ball.png");
//        float radius = MenuModel.getInstance().getBallModel(1).getRadius();
//
//        float factor = (radius*2/PIXEL_TO_METER)/texture.getWidth();
//        Sprite sprite =  new Sprite(texture, texture.getWidth(), texture.getHeight());
//        sprite.setScale(factor);
//
//        return sprite;
        float radius = MenuModel.getInstance().getBallModel(1).getRadius();

        return super.createSprite(game,"ball.png",radius);
    }

    @Override
    public void update(EntityModel model) {
        super.update(model);
    }
}
