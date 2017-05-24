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
    public EnemyView(SpaceBallsGame game) {
        super(game);
    }

    @Override
    public Sprite createSprite(SpaceBallsGame game) {
        Texture texture = game.getAssetManager().get("enemy.png");
        float radius = SandBoxModel.getInstance().getEnemyModel(0).getRadius();

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
