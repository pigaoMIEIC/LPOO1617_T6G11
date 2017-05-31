package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.LevelType;
import com.mygdx.game.Preferences;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.controller.LevelController;
import com.mygdx.game.model.entities.EntityModel;
import com.mygdx.game.view.entities.EntityView;
import com.mygdx.game.view.entities.ViewFactory;

/**
 * Created by Tiago Neves on 28/05/2017.
 */

public abstract class GameView extends ScreenAdapter{



    /**
     * Used to debug the position of the physics fixtures
     */
    protected static final boolean DEBUG_PHYSICS = false;

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
    public static final float RATIO = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
    //public static  final float RATIO = 0.5f;  //testing proposes

    public final static int RANDNR = 4;//number of random balls in start menu
    public final static int SRANDNR = 6;//number of random balls in start menu

    /**
     * A renderer used to debug the physical fixtures.
     */
    protected Box2DDebugRenderer debugRenderer;

    /**
     * The transformation matrix used to transform meters into
     * pixels in order to show fixtures in their correct places.
     */
    protected Matrix4 debugCamera;

    /**
     * The camera used to show the viewport.
     */
    protected OrthographicCamera camera;


    protected final Stage stage;

    SpaceBallsGame game;

    Touchpad touchpad;

    float sensitivity;

    float[] offsetXY = {0,0};

    /**
     * Method to initialize the local game, the camera and the stage
     *
     * @param game The game which will be associated with the screenAdapter
     */
    public GameView(SpaceBallsGame game) {
        super();
        stage = new Stage();
        this.stage.setViewport(new StretchViewport(VIEWPORT_WIDTH/PIXEL_TO_METER,VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER));
        camera = createCamera();
        this.game = game;
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(stage);

    }

    /**
     * Method to update the world and render the updated view
     * @param delta Time delta from the last update
     */
    public void render(float delta) {
        Batch batch= game.getBatch();

        handleInputs();

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin();
        drawEntities();
        batch.end();

        stage.act();
        stage.draw();
    }

    abstract void drawEntities();

    abstract void handleInputs();

    /**
     * Loads the file names received as Textures
     * @param textures The names of the files to be loaded as Textures
     */
    protected void loadAssets(String[] textures){
        for (String str: textures) {
            this.game.getAssetManager().load(str,Texture.class);
        }
        this.game.getAssetManager().finishLoading();
    }

    /**
     * Draws the view of the model passed as a parameter
     *
     * @param model Model to be drawn
     */
    protected void drawView(EntityModel model){
        EntityView view = ViewFactory.makeView(game, model, null);
        view.update(model);
        view.draw(game.getBatch());
    }

    /**
     * Renders the debug camera for the game Physics
     *
     * @param world the world that will be rendered
     */
    protected void debugPhysics(World world){
        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(world, debugCamera);
        }
    }

    /**
     * Creates the Joystick for the game
     */
    protected void createJoystick(){
        Touchpad.TouchpadStyle  touchpadStyle;
        Drawable touchBackground,touchKnob;
        Skin touchpadSkin;

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

    /**
     * Creates the camera for the View
     *
     * @return Returns the created camera
     */
    protected OrthographicCamera createCamera() {
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

    /**
     * Writes the offset to the Preferences interface
     *
     * @param x New X coordinate offset
     * @param y New Y coordinate offset
     */
    protected void writeOffset(float x, float y){
        Preferences prefs= game.getPreferences();
        prefs.writeOffsetX(x);
        prefs.writeOffsetY(y);
    }

    /**
     * Method to get the offset from the Preferences interface
     *
     * @return Returns an array with the X and Y coordinates offset respectively
     */
    protected float[] readOffsetXY(){
        float[] array = new float[2];
        Preferences prefs = game.getPreferences();
        array[0] = prefs.readOffsetX();
        array[1] = prefs.readOffsetY();
        return array;
    }

    /**
     * Method to update the Viewport size
     *
     * @param width New Viewport width
     * @param height New Viewport height
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
    }

}
