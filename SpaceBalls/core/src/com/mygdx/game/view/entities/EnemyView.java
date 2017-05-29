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
        sprite = createSprite(game);
    }


    public Sprite createSprite(SpaceBallsGame game) {
        float radius = SandBoxModel.getInstance().getEnemyModel(0).getRadius();

        return super.createSprite(game,"enemy.png",radius);
    }

    @Override
    public void update(EntityModel model) {
        super.update(model);
    }
}
