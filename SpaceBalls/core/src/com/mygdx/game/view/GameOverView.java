package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.controller.LevelController;
import com.mygdx.game.controller.OptionsController;

/**
 * Created by Tiago Neves on 23/05/2017.
 */

public class GameOverView extends GameView {


    private ImageButton gameOver;
    private ImageButton returni;
    private ImageButton restart;
    private Object clas;



    /**
     * Method to call the superclass constructor and load the assets and buttons
     *
     * @param game The game which will be associated with the screenAdapter
     */
    public GameOverView(SpaceBallsGame game,Object clas) {
        super(game);
        this.clas = clas;

        String[] array = {"oneline.png",
                "Exit.png" ,
                "restart.png"
        };
        loadAssets(array);

        createButtons();
    }

    /**
     * Method to create the buttons for the menu
     */
    public void createButtons(){
        float width = VIEWPORT_WIDTH/PIXEL_TO_METER;
        float height = VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER;
        float buttonYSize =height/10;
        float spacing = (height - buttonYSize*3)/6;

        Drawable buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("oneline.png")));
        gameOver = new ImageButton(buttonDrawable);
        gameOver.setSize(width*0.9f,buttonYSize*3f);
        gameOver.setPosition(width/2 - width*0.45f,height - buttonYSize*3.1f);
        stage.addActor(gameOver);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("Exit.png")));
        returni = new ImageButton(buttonDrawable);
        returni.setSize(width/7,buttonYSize*2f);
        returni.setPosition(width - width/10 - width/7,height/2-buttonYSize);
        stage.addActor(returni);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("restart.png")));
        restart = new ImageButton(buttonDrawable);
        restart.setSize(width/4,buttonYSize*2f);
        restart.setPosition(width/10,height/2-buttonYSize);
        stage.addActor(restart);

    }

    @Override
    void drawEntities() {}

    /**
     * Method that handles the inputs from the stage
     */
    @Override
    void handleInputs() {
        if(restart.isPressed()){
            if(clas instanceof SandBoxView) {
                game.setScreen(new SandBoxView(game));
            }
            if(clas instanceof LevelView){
                game.setScreen(new LevelView(game,(((LevelView) clas).getCurrLevel())));
            }

        }

        if(returni.isPressed()){
            if(clas instanceof SandBoxView) {
                OptionsController.getInstance().delete();
            }
            if(clas instanceof LevelView){

                LevelController.getInstance(((LevelView) clas).getCurrLevel()).delete();
            }
            game.setScreen(new MenuView(game));
        }
    }

}
