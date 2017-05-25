package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.controller.MenuController;
import com.mygdx.game.controller.OptionsController;
import com.mygdx.game.controller.SandBoxController;
import com.mygdx.game.model.MenuModel;
import com.mygdx.game.model.OptionsModel;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.EnemyModel;
import com.mygdx.game.model.entities.StaticBallModel;
import com.mygdx.game.view.LevelsView;
import com.mygdx.game.view.SandBoxView;
import com.mygdx.game.view.entities.EntityView;
import com.mygdx.game.view.entities.ViewFactory;

import static com.badlogic.gdx.Input.Keys.R;

/**
 * Created by Tiago Neves on 18/05/2017.
 */

public class OptionsView extends ScreenAdapter {
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

    private Skin sliderSkin;

    private Drawable touchBackground;

    private Drawable touchKnob;

    private Slider.SliderStyle sliderStyle;

    private Drawable buttonDrawable;

    private ImageButton useJoystick;
    private ImageButton calibrate;


    Slider slider;

    Preferences prefs = Gdx.app.getPreferences("My Preferences");


    public OptionsView(SpaceBallsGame game) {
        this.game = game;
        this.stage = new Stage();
        this.stage.setViewport(new StretchViewport(VIEWPORT_WIDTH/PIXEL_TO_METER,VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER));
        Gdx.input.setInputProcessor(stage);
        loadAssets();

        createButtons();
        createSlider();

        camera = createCamera();

        Gdx.input.setCatchBackKey(true);
    }

    private void loadAssets() {
        this.game.getAssetManager().load( "back.png" , Texture.class);
        this.game.getAssetManager().load( "ball.png" , Texture.class);
        this.game.getAssetManager().load( "calibrate.png" , Texture.class);
        this.game.getAssetManager().load( "enemy.png" , Texture.class);
        this.game.getAssetManager().load( "whiteRectangle.png" , Texture.class);
        this.game.getAssetManager().load("1.png" , Texture.class);

        this.game.getAssetManager().finishLoading();
    }

    public void createButtons(){
        float width = VIEWPORT_WIDTH/PIXEL_TO_METER;
        float height = VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER;
        float buttonYSize =height/8;
        float spacing = (height - buttonYSize*3)/6;


        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("1.png")));
        useJoystick = new ImageButton(buttonDrawable);
        useJoystick.setSize(width/5,buttonYSize*0.75f);
        useJoystick.setPosition(width/5,4*buttonYSize);
        stage.addActor(useJoystick);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("calibrate.png")));
        calibrate = new ImageButton(buttonDrawable);
        calibrate.setSize(width/5,buttonYSize*0.75f);
        calibrate.setPosition(width/5,4*buttonYSize);
        stage.addActor(calibrate);


//        Skin toggleSkin = new Skin(Gdx.files.internal("SkinMainMenu/glassy-ui.json"));
//        CheckBox toggleMode = new CheckBox("Use Joystick",toggleSkin);
//        toggleMode.scaleBy(5);
//        toggleMode.setSize(width/5,buttonYSize*0.75f);
//        toggleMode.setPosition(width/5,4*buttonYSize);
//        stage.addActor(toggleMode);
    }

    @Override
    public void render(float delta) {
        stage.setDebugAll(true);
        handleInputs(delta);
        OptionsController.getInstance().update(delta);

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
            debugRenderer.render(OptionsController.getInstance().getWorld(), debugCamera);
        }

        OptionsController.getInstance().setSensitivity(slider.getValue());
        writeSensitivityToPreferences(slider.getValue());


        stage.act();
        stage.draw();

    }

    private void drawEntities() {
        EnemyModel callibrateModel= OptionsModel.getInstance().getCallibrateModel();
        EntityView view1 = ViewFactory.makeView(game, callibrateModel);
        view1.update(callibrateModel);
        view1.draw(game.getBatch());

        BallModel ballModel = OptionsModel.getInstance().getPlayerModel();
        EntityView view = ViewFactory.makeView(game, ballModel);
        view.update(ballModel);
        view.draw(game.getBatch());


    }

    private void createSlider(){

        sliderSkin = new Skin();
        //Set background image
        sliderSkin.add("touchBackground", new Texture("whiteRectangle.png"));
        //Set knob image
        sliderSkin.add("touchKnob", new Texture("inside.png"));
        //Create TouchPad Style
        sliderStyle = new Slider.SliderStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = sliderSkin.getDrawable("touchBackground");
        touchKnob = sliderSkin.getDrawable("touchKnob");

        sliderStyle.background = touchBackground;
        sliderStyle.knob = touchKnob;

        slider = new Slider(10,60,1,false,sliderStyle);
        slider.setPosition((VIEWPORT_WIDTH/10)/PIXEL_TO_METER,VIEWPORT_WIDTH/8/PIXEL_TO_METER);

        slider.setSize((VIEWPORT_WIDTH/2)/PIXEL_TO_METER,VIEWPORT_WIDTH/20/PIXEL_TO_METER);
        stage.addActor(slider);
    }

    private void handleInputs(float delta) {


        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
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

    private void writeSensitivityToPreferences(float sensitivity){
        System.out.println(sensitivity);
        prefs.putFloat("sensitivity",sensitivity);
        float a = prefs.getFloat("sensitivity",123456);
        System.out.println(a);
    }




}
