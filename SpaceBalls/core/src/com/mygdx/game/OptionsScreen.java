package com.mygdx.game;

/**
 * Created by Tiago Neves on 21/04/2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * The Menu screen.
 */
public class OptionsScreen extends ScreenAdapter {
    private final SpaceBallsGame game;
    private final ImageButton back;
    private final Button calibrate;

    private final Stage menuStage;
    private final OptionsStage optionsStage;


    float width = Gdx.graphics.getWidth();
    float heigth = Gdx.graphics.getHeight();


    /**
     * @param game the game
     */
    OptionsScreen(SpaceBallsGame game) {

        this.game = game;
        this.menuStage = new Stage();
        this.optionsStage = new OptionsStage(this.game);
        menuStage.setViewport(new FitViewport(width, heigth));

        game.getAssetManager().load("back.png", Texture.class);
        game.getAssetManager().load("calibrate.png", Texture.class);
        game.getAssetManager().finishLoading();

        // Sets the stage as its input processor
        Gdx.input.setInputProcessor(menuStage);

//        float buttonXSize = 0.3f/PIXEL_TO_METER;
        float buttonYSize = heigth / 10;

        float spacing = (Gdx.graphics.getHeight() - buttonYSize * 3) / 6;



        Drawable buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("back.png")));
        back = new ImageButton(buttonDrawable);
        back.setSize(width/10,heigth/5);
        back.setPosition(width - width/10,heigth-heigth/5);
        menuStage.addActor(back);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("calibrate.png")));
        calibrate = new Button(buttonDrawable);
        calibrate.setSize(width/2.2f,width/2.2f);
        calibrate.setPosition(width/2,0);
        menuStage.addActor(calibrate);


    }

    /**
     * Renders the screen
     *
     * @param delta time since last rendered in seconds
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.setScreen(new MenuScreen(game));
        }

        if(back.isPressed()){
            game.setScreen(new MenuScreen(game));
        }



        // Steps the stage
        menuStage.act(delta);
        optionsStage.act(delta);


        // Draws the stage

        menuStage.draw();
        optionsStage.draw();
        menuStage.setDebugAll(true);


    }

    /**
     * Resize the stage viewport when the screen is resized
     *
     * @param width  the new screen width
     * @param height the new screen height
     */
    @Override
    public void resize(int width, int height) {
        menuStage.getViewport().update(width, height, true);
        optionsStage.getViewport().update(width,height, true);
    }
}
