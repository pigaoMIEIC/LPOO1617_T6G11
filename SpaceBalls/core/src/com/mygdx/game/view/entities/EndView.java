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
    public EndView(SpaceBallsGame game,LevelType.levelType currLevel) {
        sprite = createSprite(game,currLevel);
    }


    public Sprite createSprite(SpaceBallsGame game, LevelType.levelType currLevel) {
//        Texture texture = game.getAssetManager().get("end.png");
//        float radius = LevelModel.getInstance(LevelType.levelType.ONE).getEndBall().getRadius();
//
//        float factor = (radius*2/PIXEL_TO_METER)/texture.getWidth();
//        Sprite sprite =  new Sprite(texture, texture.getWidth(), texture.getHeight());
//        sprite.setScale(factor);
//
//        return sprite;

        float radius = LevelModel.getInstance(currLevel).getEndBall().getRadius();

        return super.createSprite(game,"end.png",radius);
    }
}
