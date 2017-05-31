package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.controller.MenuController;
import com.mygdx.game.model.MenuModel;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.StaticBallModel;
import com.mygdx.game.view.entities.EntityView;
import com.mygdx.game.view.entities.ViewFactory;

/**
 * Created by Tiago Neves on 18/05/2017.
 */

public class MenuView extends GameView {
    private ImageButton startButton;
    private ImageButton exitButton;
    private ImageButton howtoplay;
    private ImageButton sandbox;
    private ImageButton credits;
    private ImageButton title;
    private ImageButton options;

    private MenuController controller;


    public MenuView(SpaceBallsGame game) {
        super(game);

        String[] array  = {"play.png",
                "Exit.png",
                "title.png",
                "howtoplay.png",
                "credits.png",
                "options.png",
                "Survival.png",
                "ball.png",
                "enemy.png"};
        loadAssets(array);

        createButtons();

        controller = MenuController.getInstance();

        controller.setOffset(game.getPreferences().readOffsetX(),game.getPreferences().readOffsetY());

        controller.setSensitivity(game.getPreferences().readSensitivity());
    }

    /**
     * Method to create the buttons for the menu
     */
    public void createButtons(){
        float width = VIEWPORT_WIDTH/PIXEL_TO_METER;
        float height = VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER;
        float buttonYSize =height/10;
        float spacing = (height - buttonYSize*3)/6;

        AssetManager assetManager = game.getAssetManager();

        Drawable buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)assetManager.get("play.png")));
        startButton = new ImageButton(buttonDrawable);
        startButton.setSize(width/5,buttonYSize*2f);
        startButton.setPosition(width/2 - width/10,height/2+buttonYSize);
        stage.addActor(startButton);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)assetManager.get("Exit.png")));
        exitButton = new ImageButton(buttonDrawable);
        exitButton.setSize(width/10,buttonYSize);
        exitButton.setPosition(width/2 - width/20,spacing);
        stage.addActor(exitButton);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)assetManager.get("title.png")));
        title = new ImageButton(buttonDrawable);
        title.setSize(width/2,buttonYSize*1.1f);
        title.setPosition(width/2 - width/4,height - buttonYSize*1.5f);
        stage.addActor(title);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)assetManager.get("howtoplay.png")));
        howtoplay = new ImageButton(buttonDrawable);
        howtoplay.setSize(width/3,buttonYSize);
        howtoplay.setPosition(width/3,spacing*4 - buttonYSize);
        stage.addActor(howtoplay);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)assetManager.get("options.png")));
        options = new ImageButton(buttonDrawable);
        options.setSize(width/10,height/10);
        options.setPosition(width - width/10,height-height/9);
        stage.addActor(options);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)assetManager.get("Survival.png")));
        sandbox = new ImageButton(buttonDrawable);
        sandbox.setSize(width/5,buttonYSize);
        sandbox.setPosition(width/2 - width/10,spacing*5 - buttonYSize);
        stage.addActor(sandbox);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)assetManager.get("credits.png")));
        credits = new ImageButton(buttonDrawable);
        credits.setSize(width/6,buttonYSize);
        credits.setPosition(width/2 - width/12,spacing*3 - buttonYSize);
        stage.addActor(credits);

    }

    /**
     * Method to update the world and render the updated view
     * @param delta Time delta from the last update
     */
    @Override
    public void render(float delta) {
        //stage.setDebugAll(true);
        float x = Gdx.input.getAccelerometerX();
        float y = Gdx.input.getAccelerometerY();
        controller.setAccelX(x);
        controller.setAccelY(y);
        controller.update(delta);

        super.render(delta);

        debugPhysics(controller.getWorld());
    }
    
    /**
     * Method that draws the views of the models present in the scene
     */
    @Override
    void drawEntities() {
        for (int i = 0; i < RANDNR; i++) {
            BallModel ballModel = MenuModel.getInstance().getBallModel(i);
            drawView(ballModel);
        }

        for (int i = 0; i < SRANDNR; i++) {
            StaticBallModel ballModel = MenuModel.getInstance().getStaticBallModel(i);
            drawView(ballModel);
        }

    }

    /**
     * Method that handles the inputs from the stage
     */
    @Override
    void handleInputs() {

        if (exitButton.isPressed()) {
            Gdx.app.exit();
        }

        if(sandbox.isPressed()){
            game.setScreen(new SandBoxView(game));
        }

        if(startButton.isPressed()){
            game.setScreen(new LevelChoiceView(game));
        }

        if(options.isPressed()){
            game.setScreen(new OptionsView(game));
        }

        if(howtoplay.isPressed()){
            game.setScreen( new HowToPlayView(game));
        }

        if(credits.isPressed()){
            game.setScreen( new CreditsView(game));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
        }

    }

}
