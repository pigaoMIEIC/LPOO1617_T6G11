package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.controller.SandBoxController;
import com.mygdx.game.model.SandBoxModel;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.EnemyModel;
import com.mygdx.game.view.entities.EntityView;
import com.mygdx.game.view.entities.ViewFactory;

/**
 * Created by Tiago Neves on 22/05/2017.
 */

public class SandBoxView extends GameView{

    private ImageButton startButton;
    private ImageButton exitButton;
    private ImageButton howtoplay;
    private ImageButton sandbox;
    private ImageButton credits;
    private ImageButton title;
    private ImageButton options;

    private ProgressBar bar;

    private boolean joystick;


    public SandBoxView(SpaceBallsGame game) {
        super(game);

        this.stage.setViewport(new StretchViewport(VIEWPORT_WIDTH/PIXEL_TO_METER,VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER));

        Gdx.input.setInputProcessor(stage);

        loadAssets();

        Skin skin = new Skin();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        TextureRegionDrawable textureBar = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("whiteSquare.png"))));
        ProgressBar.ProgressBarStyle barStyle = new ProgressBar.ProgressBarStyle(skin.newDrawable("white", Color.RED), textureBar);
        barStyle.knobBefore = barStyle.knob;
        bar = new ProgressBar(0, 5, 1/60f, false, barStyle);
        bar.setPosition(0, VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER);
        bar.setSize(VIEWPORT_WIDTH/PIXEL_TO_METER,0.8f);
        stage.addActor(bar);

        Gdx.input.setCatchBackKey(true);


        sensitivity = game.getPreferences().readSensitivity();
        SandBoxController.getInstance().setSensitivity(sensitivity);
        joystick = game.getPreferences().readJoystick();
        if(joystick)
         createJoystick();

        SandBoxController.getInstance().setJoystick(game.getPreferences().readJoystick());
        SandBoxController.getInstance().setOffsetX(game.getPreferences().readOffsetX());
        SandBoxController.getInstance().setOffsetY(game.getPreferences().readOffsetY());

    }


    private void loadAssets() {
        game.getAssetManager().load( "back.png" , Texture.class);
        game.getAssetManager().load( "ball.png" , Texture.class);
        game.getAssetManager().load( "calibrate.png" , Texture.class);
        game.getAssetManager().load( "credits.png" , Texture.class);
        game.getAssetManager().load( "enemy.png" , Texture.class);
        game.getAssetManager().load( "Exit.png" , Texture.class);
        game.getAssetManager().load( "howtoplay.png" , Texture.class);
        game.getAssetManager().load( "options.png" , Texture.class);
        game.getAssetManager().load( "play.png" , Texture.class);
        game.getAssetManager().load( "sandbox.png" , Texture.class);
        game.getAssetManager().load( "title.png" , Texture.class);
        game.getAssetManager().load( "transparent.png" , Texture.class);
        game.getAssetManager().load( "exterior.png" , Texture.class);
        game.getAssetManager().load( "inside.png" , Texture.class);
        game.getAssetManager().finishLoading();
    }


    @Override
    public void render(float delta) {
//
//        handleInputs(delta);
//        SandBoxController.getInstance().update(delta);
//
//        camera.update();
//
//        game.getBatch().setProjectionMatrix(camera.combined);
//
//        Gdx.gl.glClearColor( 0f, 0f,0f, 1 );
//        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
//
//        game.getBatch().begin();
//        drawEntities();
//        game.getBatch().end();
//
//        if (DEBUG_PHYSICS) {
//            debugCamera = camera.combined.cpy();
//            debugCamera.scl(1 / PIXEL_TO_METER);
//            debugRenderer.render(SandBoxController.getInstance().getWorld(), debugCamera);
//        }
//
//        stage.act();
//        stage.draw();
        SandBoxController.getInstance().update(delta);

        super.render(delta);

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(SandBoxController.getInstance().getWorld(), debugCamera);
        }

        if(SandBoxController.getInstance().isColliding()){
            SandBoxController.getInstance().setColliding(false);
            SandBoxController.getInstance().delete();
            game.setScreen(new GameOverView(game,this));
            return;
        }

        bar.setValue(SandBoxController.getInstance().getSeconds());

        if(joystick)
            SandBoxController.getInstance().accelerate(touchpad.getKnobPercentX()/16,touchpad.getKnobPercentY()/16);


        if(Gdx.input.justTouched() && joystick){
            float conversionX = VIEWPORT_WIDTH/PIXEL_TO_METER/Gdx.graphics.getWidth();
            float conversionY = VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER/Gdx.graphics.getHeight();
            float radius = VIEWPORT_WIDTH/12/PIXEL_TO_METER;
            touchpad.setPosition(Gdx.input.getX()*conversionX - radius,(Gdx.graphics.getHeight() - Gdx.input.getY())*conversionY - radius);
            touchpad.toFront();
            System.out.println(touchpad.getX());
        }
    }

    @Override
    void handleInputs(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            SandBoxController.getInstance().delete();
            game.setScreen(new MenuView(game));
        }
    }

    @Override
    void drawEntities() {
        for (int i = 0; i < SandBoxModel.getInstance().getnBalls(); i++) {
            EnemyModel ballModel = SandBoxModel.getInstance().getEnemyModel(i);
            drawView(ballModel);
        }

        BallModel ballModel = SandBoxModel.getInstance().getPlayerModel();
        drawView(ballModel);


    }

    public Stage getStage() {
        return stage;
    }

}
