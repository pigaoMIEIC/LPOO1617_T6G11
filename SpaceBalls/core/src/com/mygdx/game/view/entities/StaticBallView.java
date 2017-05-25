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
    public StaticBallView(SpaceBallsGame game) {
        super(game);
    }

    @Override
    public Sprite createSprite(SpaceBallsGame game) {
        Texture texture = game.getAssetManager().get("enemy.png");
        float radius = MenuModel.getInstance().getStaticBallModel(1).getRadius();

        float factor = (radius*2/PIXEL_TO_METER)/texture.getWidth();
        Sprite sprite =  new Sprite(texture, texture.getWidth(), texture.getHeight());
        sprite.setScale(factor);

        return sprite;
    }

    @Override
    public void update(EntityModel model) {
        super.update(model);
    }
}
