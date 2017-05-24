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
 * Created by Tiago Neves on 23/05/2017.
 */

public class GameOverView extends ScreenAdapter {
    /**
     * Used to debug the position of the physics fixtures
     */
    private static final boolean DEBUG_PHYSICS = true;

    /**
     * How much meters does a pixel represent.
     */
    public static final float PIXEL_TO_METER = 0.20f / 200;

    /**
     * The width of the viewport in meters. The height is
     * automatically calculated using the screen ratio.
     */
    public static final float VIEWPORT_WIDTH = 4;

    /**
     * The screen ratio.
     */
    public static final float RATIO = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());;

    /**
     * The camera used to show the viewport.
     */
    private final OrthographicCamera camera;

    /**
     * A renderer used to debug the physical fixtures.
     */
    private Box2DDebugRenderer debugRenderer;

    /**
     * The transformation matrix used to transform meters into
     * pixels in order to show fixtures in their correct places.
     */
    private Matrix4 debugCamera;

    private final SpaceBallsGame game;

    private final Stage stage;

//    private Vector2[] positions = {
//            new Vector2(0,0),
//            new Vector2(VIEWPORT_WIDTH,0),
//            new Vector2(0,VIEWPORT_WIDTH * RATIO),
//            new Vector2(VIEWPORT_WIDTH,VIEWPORT_WIDTH * RATIO),
//            new Vector2(VIEWPORT_WIDTH/4,(VIEWPORT_WIDTH * RATIO)/2),
//            new Vector2(VIEWPORT_WIDTH-VIEWPORT_WIDTH/4,(VIEWPORT_WIDTH * RATIO)/2)
//    };

    private ImageButton gameOver;
    private ImageButton returni;
    private ImageButton restart;



    public GameOverView(SpaceBallsGame game) {
        this.game = game;
        this.stage = new Stage();
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

    @Override
    public void render(float delta) {
        handleInputs(delta);

        camera.update();

        game.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor( 0f, 0f,0f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();

        game.getBatch().end();

        stage.act();
        stage.draw();

    }


    private void handleInputs(float delta) {
        if(restart.isPressed()){
            game.setScreen(new SandBoxView(game));
        }

        if(returni.isPressed()){
            game.setScreen(new MenuView(game));
        }
    }

    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * RATIO);

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        if (DEBUG_PHYSICS) {
            debugRenderer = new Box2DDebugRenderer();
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
        }

        return camera;
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
    }
}
