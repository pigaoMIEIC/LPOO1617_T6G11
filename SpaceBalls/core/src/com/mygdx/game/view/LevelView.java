package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.LevelType;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.controller.LevelController;
import com.mygdx.game.controller.SandBoxController;
import com.mygdx.game.model.LevelModel;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.EnemyModel;
import com.mygdx.game.model.entities.StaticModel;
import com.mygdx.game.view.entities.EntityView;
import com.mygdx.game.view.entities.ViewFactory;

/**
 * Created by Tiago Neves on 26/05/2017.
 */

public class LevelView extends GameView {

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

    private Skin touchpadSkin;

    private SpriteBatch batch;

    private  Touchpad.TouchpadStyle  touchpadStyle;

    private Drawable touchBackground,touchKnob;

    private Touchpad touchpad;

    float sensitivity;

    boolean joystick;

    private LevelType.levelType currLevel;


    public LevelView(SpaceBallsGame game,LevelType.levelType newLevel) {
        super(game);
        this.game = game;
        this.stage = new Stage();
        this.stage.setViewport(new StretchViewport(VIEWPORT_WIDTH/PIXEL_TO_METER,VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER));
        Gdx.input.setInputProcessor(stage);
        loadAssets();

        currLevel = newLevel;

        sensitivity = game.getPreferences().readSensitivity();
        LevelController.getInstance().setSensitivity(sensitivity);
        joystick = game.getPreferences().readJoystick();
        if(joystick)
            createJoystick();

        LevelController.getInstance().setJoystick(game.getPreferences().readJoystick());
        LevelController.getInstance().setOffsetX(game.getPreferences().readOffsetX());
        LevelController.getInstance().setOffsetY(game.getPreferences().readOffsetY());

        camera = createCamera();
        Gdx.input.setCatchBackKey(true);
    }

    private void createJoystick(){
        batch = new SpriteBatch();
        //Create camera

        //Create a touchpad skin
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("exterior.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("inside.png"));
        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");

        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(VIEWPORT_WIDTH/6/PIXEL_TO_METER,VIEWPORT_WIDTH/6/PIXEL_TO_METER, 200, 200);
        touchpad.setSize(VIEWPORT_WIDTH/6/PIXEL_TO_METER,VIEWPORT_WIDTH/6/PIXEL_TO_METER);


        touchpad.setPosition((VIEWPORT_WIDTH-VIEWPORT_WIDTH/4)/PIXEL_TO_METER,VIEWPORT_WIDTH/8/PIXEL_TO_METER);

        //Create a Stage and add TouchPad

        stage.addActor(touchpad);

    }

    private void loadAssets() {
        this.game.getAssetManager().load("back.png" , Texture.class);
        this.game.getAssetManager().load("1.png", Texture.class);
        this.game.getAssetManager().load("2.png", Texture.class);
        this.game.getAssetManager().load("3.png", Texture.class);
        this.game.getAssetManager().load("4.png", Texture.class);
        this.game.getAssetManager().load("5.png", Texture.class);
        this.game.getAssetManager().load("exterior.png", Texture.class);
        this.game.getAssetManager().load("inside.png", Texture.class);
        this.game.getAssetManager().finishLoading();
    }

    public void render(float delta) {
        stage.setDebugAll(true);
        handleInputs(delta);
        LevelController.getInstance().update(delta);

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
            debugRenderer.render(LevelController.getInstance().getWorld(), debugCamera);
        }

        stage.act();
        stage.draw();

        if(joystick)
           LevelController.getInstance().accelerate(touchpad.getKnobPercentX()/16,touchpad.getKnobPercentY()/16);

        //checks if the player Won
        if(LevelController.getInstance().getWin()){
            backToMenu();
        }

        //checks if the player lost
        if(LevelController.getInstance().isColliding()){
           backToMenu();
        }

    }

    private void drawEntities() {

        BallModel ballModel = LevelModel.getInstance(currLevel).getPlayerModel();
        EntityView view = ViewFactory.makeView(game, ballModel);
        view.update(ballModel);
        view.draw(game.getBatch());

        BallModel endBall = LevelModel.getInstance(currLevel).getEndBall();
        view.update(endBall);
        view.draw(game.getBatch());


        for (int i = 0; i < LevelModel.getInstance(currLevel).getEnemySize(); i++) {
            EnemyModel enemyModel = LevelModel.getInstance(currLevel).getEnemyModel(i);
            view = ViewFactory.makeView(game, enemyModel);
            view.update(enemyModel);
            view.draw(game.getBatch());
        }

        for (int i = 1; i < LevelModel.getInstance(currLevel).getObstaclesSize(); i++) {
            StaticModel obstacleModel = LevelModel.getInstance(currLevel).getStaticModel(i);
            view = ViewFactory.makeView(game, obstacleModel);
            view.update(obstacleModel);
            view.draw(game.getBatch());
        }

    }


    private void handleInputs(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)||Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            backToMenu();
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
