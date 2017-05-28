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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.LevelType;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.controller.MenuController;
import com.mygdx.game.model.MenuModel;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.StaticBallModel;
import com.mygdx.game.view.entities.EntityView;
import com.mygdx.game.view.entities.ViewFactory;

import java.util.Vector;

/**
 * Created by Tiago Neves on 23/05/2017.
 */

public class LevelChoiceView extends GameView {


    /**
     * The camera used to show the viewport.
     */
    private final OrthographicCamera camera;

    private final SpaceBallsGame game;

    private Button tmpButton;

    private Vector<Button> levels = new Vector<Button>();
    private ImageButton returni;



    public LevelChoiceView(SpaceBallsGame game) {
        super(game);
        this.game = game;
        this.stage.setViewport(new StretchViewport(VIEWPORT_WIDTH/PIXEL_TO_METER,VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER));
        Gdx.input.setInputProcessor(stage);
        loadAssets();

        createButtons();

        camera = createCamera();
        Gdx.input.setCatchBackKey(true);
    }

    private void loadAssets() {
        this.game.getAssetManager().load( "back.png" , Texture.class);
        this.game.getAssetManager().load("1.png", Texture.class);
        this.game.getAssetManager().load("2.png", Texture.class);
        this.game.getAssetManager().load("3.png", Texture.class);
        this.game.getAssetManager().load("4.png", Texture.class);
        this.game.getAssetManager().load("5.png", Texture.class);
        this.game.getAssetManager().finishLoading();
    }

    public void createButtons(){
        float width = VIEWPORT_WIDTH/PIXEL_TO_METER;
        float height = VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER;
        float buttonSize =Math.min((height/4)-(height/20),(width-(width/4)-(width/4))/5);

        float horzSpacing = (width-(width/4)-(5*buttonSize))/4f;

        Drawable buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("1.png")));
        tmpButton = new ImageButton(buttonDrawable);
        tmpButton.setSize(buttonSize,buttonSize);
        tmpButton.setPosition(width/8,(height/2)-(buttonSize/2));
        levels.addElement(tmpButton);
        stage.addActor(tmpButton);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("2.png")));
        tmpButton = new Button(buttonDrawable);
        tmpButton.setSize(buttonSize,buttonSize);
        tmpButton.setPosition(width/8+(buttonSize+horzSpacing),(height/2)-(buttonSize/2));
        levels.addElement(tmpButton);
        stage.addActor(tmpButton);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("3.png")));
        tmpButton = new Button(buttonDrawable);
        tmpButton.setSize(buttonSize,buttonSize);
        tmpButton.setPosition(width/8+(buttonSize+horzSpacing)*2,(height/2)-(buttonSize/2));
        levels.addElement(tmpButton);
        stage.addActor(tmpButton);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("4.png")));
        tmpButton = new Button(buttonDrawable);
        tmpButton.setSize(buttonSize,buttonSize);
        tmpButton.setPosition(width/8+(buttonSize+horzSpacing)*3,(height/2)-(buttonSize/2));
        levels.addElement(tmpButton);
        stage.addActor(tmpButton);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("5.png")));
        tmpButton = new Button(buttonDrawable);
        tmpButton.setSize(buttonSize,buttonSize);
        tmpButton.setPosition(width/8+(buttonSize+horzSpacing)*4,(height/2)-(buttonSize/2));
        levels.addElement(tmpButton);
        stage.addActor(tmpButton);

        //(buttonYSize+horzSpacing)


    }

    @Override
    public void render(float delta) {
        stage.setDebugAll(true);
        handleInputs(delta);
        MenuController.getInstance().update(delta);

        camera.update();

        game.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor( 0f, 0f,0f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();

        drawEntities();
        game.getBatch().end();

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(MenuController.getInstance().getWorld(), debugCamera);
        }

        stage.act();
        stage.draw();

    }

    private void drawEntities() {
        for (int i = 0; i < MenuController.RANDNR; i++) {
            BallModel ballModel = MenuModel.getInstance().getBallModel(i);
            EntityView view = ViewFactory.makeView(game, ballModel);
            view.update(ballModel);
            view.draw(game.getBatch());
        }

        for (int i = 0; i < MenuController.SRANDNR; i++) {
            StaticBallModel ballModel = MenuModel.getInstance().getStaticBallModel(i);
            EntityView view = ViewFactory.makeView(game, ballModel);
            view.update(ballModel);
            view.draw(game.getBatch());
        }
    }

    private void handleInputs(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            this.dispose();
            game.setScreen(new MenuView(game));
        }

        if(levels.elementAt(0).isPressed()){
            game.setScreen(new LevelView(game, LevelType.levelType.ONE));
        }
    }

}
