package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
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
    /**
     * The camera used to show the viewport.
     */
    private final OrthographicCamera camera;

    private final SpaceBallsGame game;


    private ImageButton startButton;
    private ImageButton exitButton;
    private ImageButton howtoplay;
    private ImageButton sandbox;
    private ImageButton credits;
    private ImageButton title;
    private ImageButton options;


    public MenuView(SpaceBallsGame game) {
        super(game);
        this.game = game;
        this.stage.setViewport(new StretchViewport(VIEWPORT_WIDTH/PIXEL_TO_METER,VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER));
        Gdx.input.setInputProcessor(stage);

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

        camera = createCamera();

        MenuController.getInstance().setOffsetX(game.getPreferences().readOffsetX());
        MenuController.getInstance().setOffsetY(game.getPreferences().readOffsetY());

        MenuController.getInstance().setSensitivity(game.getPreferences().readSensitivity());
    }

//    private void loadAssets() {
//        this.game.getAssetManager().load( "play.png" , Texture.class);
//        this.game.getAssetManager().load( "Exit.png" , Texture.class);
//        this.game.getAssetManager().load( "title.png" , Texture.class);
//        this.game.getAssetManager().load( "howtoplay.png" , Texture.class);
//        this.game.getAssetManager().load( "credits.png" , Texture.class);
//        this.game.getAssetManager().load( "options.png" , Texture.class);
//        this.game.getAssetManager().load( "Survival.png" , Texture.class);
//        this.game.getAssetManager().load( "ball.png" , Texture.class);
//        this.game.getAssetManager().load( "enemy.png" , Texture.class);
//
//        this.game.getAssetManager().finishLoading();
//    }

    public void createButtons(){
        float width = VIEWPORT_WIDTH/PIXEL_TO_METER;
        float height = VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER;
        float buttonYSize =height/10;
        float spacing = (height - buttonYSize*3)/6;

        Drawable buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("play.png")));
        startButton = new ImageButton(buttonDrawable);
        startButton.setSize(width/5,buttonYSize*2f);
        startButton.setPosition(width/2 - width/10,height/2+buttonYSize);
        stage.addActor(startButton);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("Exit.png")));
        exitButton = new ImageButton(buttonDrawable);
        exitButton.setSize(width/10,buttonYSize);
        exitButton.setPosition(width/2 - width/20,spacing);
        stage.addActor(exitButton);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("title.png")));
        title = new ImageButton(buttonDrawable);
        title.setSize(width/2,buttonYSize*1.1f);
        title.setPosition(width/2 - width/4,height - buttonYSize*1.5f);
        stage.addActor(title);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("howtoplay.png")));
        howtoplay = new ImageButton(buttonDrawable);
        howtoplay.setSize(width/3,buttonYSize);
        howtoplay.setPosition(width/3,spacing*4 - buttonYSize);
        stage.addActor(howtoplay);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("options.png")));
        options = new ImageButton(buttonDrawable);
        options.setSize(width/10,height/10);
        options.setPosition(width - width/10,height-height/9);
        stage.addActor(options);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("Survival.png")));
        sandbox = new ImageButton(buttonDrawable);
        sandbox.setSize(width/5,buttonYSize);
        sandbox.setPosition(width/2 - width/10,spacing*5 - buttonYSize);
        stage.addActor(sandbox);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("credits.png")));
        credits = new ImageButton(buttonDrawable);
        credits.setSize(width/6,buttonYSize);
        credits.setPosition(width/2 - width/12,spacing*3 - buttonYSize);
        stage.addActor(credits);

    }

    @Override
    public void render(float delta) {
        MenuController.getInstance().update(delta);

        super.render(delta);

        debugPhysics(MenuController.getInstance().getWorld());
    }

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

    @Override
    void handleInputs(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            MenuController.getInstance().accelerate();
        }

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

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
        }

    }

}
