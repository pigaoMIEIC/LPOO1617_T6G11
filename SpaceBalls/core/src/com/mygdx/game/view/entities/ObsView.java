package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.model.MenuModel;

/**
 * Created by Tiago Neves on 29/05/2017.
 */

public class ObsView extends EntityView{
    public ObsView(SpaceBallsGame game) {
        sprite = createSprite(game);
    }


    public Sprite createSprite(SpaceBallsGame game) {
//        Texture texture = game.getAssetManager().get("enemy.png");
//        float radius = MenuModel.getInstance().getStaticBallModel(1).getRadius();
//
//        float factor = (radius*2/PIXEL_TO_METER)/texture.getWidth();
//        Sprite sprite =  new Sprite(texture, texture.getWidth(), texture.getHeight());
//        sprite.setScale(factor);
//
//        return sprite;
        //float radius = LEv.getInstance().getStaticBallModel(1).getRadius();
        float radius = 0.25f;

        return super.createSprite(game,"whiteSquare.png",radius);
    }
}
