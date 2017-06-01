package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.controller.LevelController;
import com.mygdx.game.controller.OptionsController;

/**
 * Created by Tiago Neves on 23/05/2017.
 */

public class HowToPlayView extends GameView {


    private ImageButton image;

    /**
     * Method to call the superclass constructor and load the assets and buttons
     *
     * @param game The game which will be associated with the screenAdapter
     */
    public HowToPlayView(SpaceBallsGame game) {
        super(game);
        String a [] = {"how_to_play.png"};
        loadAssets(a);
        createButtons();

    }

    @Override
    void drawEntities() {}

    /**
     * Method to create the buttons for the menu
     */
    public void createButtons(){
        float width = VIEWPORT_WIDTH/PIXEL_TO_METER;
        float height = VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER;

        Drawable buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("how_to_play.png")));
        image = new ImageButton(buttonDrawable);
        image.setSize(width,height);
        image.setPosition(0,0);
        stage.addActor(image);


    }
    /**
     * Method that handles the inputs from the stage
     */
    @Override
    void handleInputs() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)||Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            game.setScreen( new MenuView(game));
        }
    }

}
