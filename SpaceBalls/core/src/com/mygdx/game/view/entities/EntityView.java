package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.model.SandBoxModel;
import com.mygdx.game.model.entities.EntityModel;

/**
 * Created by Tiago Neves on 18/05/2017.
 */

public abstract class EntityView extends Stage{

    static final float PIXEL_TO_METER = 0.20f / 200;

    /**
     * The sprite representing this entity view.
     */
    Sprite sprite;

//    /**
//     * Creates a view belonging to a game.
//     *
//     * @param game the game this view belongs to. Needed to access the
//     *             asset manager to get textures.
//     */
//    EntityView(SpaceBallsGame game) {
//        sprite = createSprite(game);
//    }

    /**
     * Draws the sprite from this view using a sprite batch.
     *
     * @param batch The sprite batch to be used for drawing.
     */
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    /**
     * Method that creates the view sprite.
     *
     * @param game The game this view belongs to. Needed to access the asset manager to get textures.
     * @param name The name of the texture file.
     * @param radius The measure to scale the sprite to the appropriate size
     * @return the sprite representing this view.
     */
    public Sprite createSprite(SpaceBallsGame game,String name,float radius){
        Texture texture = game.getAssetManager().get(name);

        float factor = (radius*2/PIXEL_TO_METER)/texture.getWidth();
        Sprite sprite =  new Sprite(texture, texture.getWidth(), texture.getHeight());
        sprite.setScale(factor);

        return sprite;
    }

    /**
     * Updates this view based on a certain model.
     *
     * @param model the model used to update this view
     */
    public void update(EntityModel model) {
        sprite.setCenter(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
    }
}
