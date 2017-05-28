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
import com.mygdx.game.controller.LevelController;
import com.mygdx.game.controller.MenuController;
import com.mygdx.game.controller.OptionsController;
import com.mygdx.game.model.MenuModel;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.StaticBallModel;
import com.mygdx.game.view.entities.EntityView;
import com.mygdx.game.view.entities.ViewFactory;

/**
 * Created by Tiago Neves on 23/05/2017.
 */

public class GameOverView extends GameView {


    private ImageButton gameOver;
    private ImageButton returni;
    private ImageButton restart;
    private Object clas;




    public GameOverView(SpaceBallsGame game,Object clas) {
        super(game);
        this.game = game;
        this.clas = clas;
        this.stage.setViewport(new StretchViewport(VIEWPORT_WIDTH/PIXEL_TO_METER,VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER));
        Gdx.input.setInputProcessor(stage);
        loadAssets();

        createButtons();

        camera = createCamera();
    }

    private void loadAssets() {
        this.game.getAssetManager().load( "oneline.png" , Texture.class);
        this.game.getAssetManager().load( "Exit.png" , Texture.class);
        this.game.getAssetManager().load( "Endless.png" , Texture.class);

        this.game.getAssetManager().finishLoading();
    }

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

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("Survival.png")));
        restart = new ImageButton(buttonDrawable);
        restart.setSize(width/7,buttonYSize*2f);
        restart.setPosition(width/10,height/2-buttonYSize);
        stage.addActor(restart);


    }

//    public void render(float delta) {
//        handleInputs(delta);
//
//        camera.update();
//
//        game.getBatch().setProjectionMatrix(camera.combined);
//
//        Gdx.gl.glClearColor( 0f, 0f,0f, 1 );
//        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
//
//        stage.act();
//        stage.draw();
//
//    }


    @Override
    void drawEntities() {}

    @Override
    void handleInputs(float delta) {
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
                LevelController.getInstance().delete();
            }
            game.setScreen(new MenuView(game));
        }
    }

}
