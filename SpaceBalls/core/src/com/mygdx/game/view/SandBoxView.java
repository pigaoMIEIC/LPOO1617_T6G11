package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MenuScreen;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.controller.MenuController;
import com.mygdx.game.controller.SandBoxController;
import com.mygdx.game.model.MenuModel;
import com.mygdx.game.model.SandBoxModel;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.EnemyModel;
import com.mygdx.game.model.entities.StaticBallModel;
import com.mygdx.game.view.entities.EntityView;
import com.mygdx.game.view.entities.ViewFactory;

/**
 * Created by Tiago Neves on 22/05/2017.
 */

public class SandBoxView extends ScreenAdapter{
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

    private static SpaceBallsGame game = null;

    private final Stage stage;

    private ImageButton startButton;
    private ImageButton exitButton;
    private ImageButton howtoplay;
    private ImageButton sandbox;
    private ImageButton credits;
    private ImageButton title;
    private ImageButton options;

    ProgressBar bar;

    private Skin touchpadSkin;

    private SpriteBatch batch;

    private  Touchpad.TouchpadStyle  touchpadStyle;

    private  Drawable touchBackground,touchKnob;

    private Touchpad touchpad;

    float sensitivity;


    public SandBoxView(SpaceBallsGame game) {
        this.game = game;
        this.stage = new Stage();
        this.stage.setViewport(new StretchViewport(VIEWPORT_WIDTH/PIXEL_TO_METER,VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER));
        Gdx.input.setInputProcessor(stage);
        loadAssets();

        //createButtons();

        camera = createCamera();

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
        bar.setSize(VIEWPORT_WIDTH/PIXEL_TO_METER,0.2f);
        stage.addActor(bar);

        Gdx.input.setCatchBackKey(true);

        createJoystick();
        sensitivity = game.getPreferences().readSensitivity();

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
        this.game.getAssetManager().load( "back.png" , Texture.class);
        this.game.getAssetManager().load( "ball.png" , Texture.class);
        this.game.getAssetManager().load( "calibrate.png" , Texture.class);
        this.game.getAssetManager().load( "credits.png" , Texture.class);
        this.game.getAssetManager().load( "enemy.png" , Texture.class);
        this.game.getAssetManager().load( "Exit.png" , Texture.class);
        this.game.getAssetManager().load( "howtoplay.png" , Texture.class);
        this.game.getAssetManager().load( "options.png" , Texture.class);
        this.game.getAssetManager().load( "play.png" , Texture.class);
        this.game.getAssetManager().load( "sandbox.png" , Texture.class);
        this.game.getAssetManager().load( "title.png" , Texture.class);
        this.game.getAssetManager().load( "transparent.png" , Texture.class);
        this.game.getAssetManager().load( "exterior.png" , Texture.class);
        this.game.getAssetManager().load( "inside.png" , Texture.class);


        this.game.getAssetManager().finishLoading();
    }


    @Override
    public void render(float delta) {

        SandBoxController.getInstance().update(delta);

        camera.update();

        game.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor( 0f, 0f,0f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        stage.act();
        stage.draw();
        game.getBatch().begin();

        drawEntities();
        game.getBatch().end();

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(SandBoxController.getInstance().getWorld(), debugCamera);
        }



        if(SandBoxController.getInstance().isColliding()){
            SandBoxController.getInstance().setColliding(false);
            SandBoxController.getInstance().delete();
            game.setScreen(new GameOverView(game));
            return;
        }

        bar.setValue(SandBoxController.getInstance().getSeconds());

        SandBoxController.getInstance().accelerate(touchpad.getKnobPercentX()/16,touchpad.getKnobPercentY()/16);

        handleInputs(delta);


    }

    private void drawEntities() {
        for (int i = 0; i < SandBoxModel.getInstance().getnBalls(); i++) {
            EnemyModel ballModel = SandBoxModel.getInstance().getEnemyModel(i);
            EntityView view = ViewFactory.makeView(game, ballModel);
            view.update(ballModel);
            view.draw(game.getBatch());
        }

        BallModel ballModel = SandBoxModel.getInstance().getPlayerModel();
        EntityView view = ViewFactory.makeView(game, ballModel);
        view.update(ballModel);
        view.draw(game.getBatch());


    }

    private void handleInputs(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            SandBoxController.getInstance().delete();
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

    public Stage getStage() {
        return stage;
    }


}
