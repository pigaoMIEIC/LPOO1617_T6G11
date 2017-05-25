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

public class MenuView extends ScreenAdapter {
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

    private ImageButton startButton;
    private ImageButton exitButton;
    private ImageButton howtoplay;
    private ImageButton sandbox;
    private ImageButton credits;
    private ImageButton title;
    private ImageButton options;


    public MenuView(SpaceBallsGame game) {
        this.game = game;
        this.stage = new Stage();
        this.stage.setViewport(new StretchViewport(VIEWPORT_WIDTH/PIXEL_TO_METER,VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER));
        Gdx.input.setInputProcessor(stage);
        loadAssets();

        createButtons();

        camera = createCamera();
    }

    private void loadAssets() {
        this.game.getAssetManager().load( "back.png" , Texture.class);
        this.game.getAssetManager().load( "ball.png" , Texture.class);
        this.game.getAssetManager().load( "calibrate.png" , Texture.class);
        this.game.getAssetManager().load( "credits.png" , Texture.class);
        this.game.getAssetManager().load( "enemy.png" , Texture.class);
        this.game.getAssetManager().load( "Exit.png" , Texture.class);
        this.game.getAssetManager().load( "howtoplay.png" , Texture.class);
        this.game.getAssetManager().load( "options.png" , Texture.class);
        this.game.getAssetManager().load( "play.png" , Texture.class);
        this.game.getAssetManager().load( "Survival.png" , Texture.class);
        this.game.getAssetManager().load( "title.png" , Texture.class);
        this.game.getAssetManager().load( "transparent.png" , Texture.class);

        this.game.getAssetManager().finishLoading();
    }

    public void createButtons(){
        float width = VIEWPORT_WIDTH/PIXEL_TO_METER;
        float height = VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER;
        float buttonYSize =height/10;
        float spacing = (height - buttonYSize*3)/6;

//        System.out.println("tamanho y = "+buttonYSize);
//        System.out.println("largura = "+width);
//        System.out.println("altura = "+height);
//        System.out.println("spacing = "+spacing);

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
        options.setSize(width/20,height/10);
        options.setPosition(width - width/20,height-height/10);
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
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            MenuController.getInstance().accelerate();
        }

        if (exitButton.isPressed()) {
            System.out.println("exit");
            Gdx.app.exit();
        }

        if(sandbox.isPressed()){
            game.setScreen(new SandBoxView(game));
        }

        if(startButton.isPressed()){
            game.setScreen(new LevelsView(game));
        }

        if(options.isPressed()){
            System.out.println("manel");
            game.setScreen(new OptionsView(game));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            System.out.println("está a ser estupido");
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
