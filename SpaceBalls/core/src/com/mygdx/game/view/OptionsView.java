package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MyFonts;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.controller.OptionsController;
import com.mygdx.game.model.OptionsModel;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.EnemyModel;
import com.mygdx.game.view.entities.EntityView;
import com.mygdx.game.view.entities.ViewFactory;

/**
 * Created by Tiago Neves on 18/05/2017.
 */

public class OptionsView extends GameView{


    /**
     * The camera used to show the viewport.
     */
    private final OrthographicCamera camera;

    private Skin sliderSkin;

    private Drawable touchBackground;

    private Drawable touchKnob;

    private Slider.SliderStyle sliderStyle;

    private Drawable buttonDrawable;

    private Button calibrate;

    private float sensitivity;

    CheckBox checkBox;


    Slider slider;

    float width = VIEWPORT_WIDTH/PIXEL_TO_METER;
    float height = VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER;

    float offsetY = 0;
    float offsetX = 0;

    Label lblsensitivity;


    public OptionsView(SpaceBallsGame game) {
        super(game);
        this.stage.setViewport(new StretchViewport(VIEWPORT_WIDTH/PIXEL_TO_METER,VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER));
        Gdx.input.setInputProcessor(stage);
        loadAssets();

        createButtons();
        createSlider();
        createCheckBox();

        camera = createCamera();

        Gdx.input.setCatchBackKey(true);

        sensitivity = game.getPreferences().readSensitivity();
        if(sensitivity == 0){
            sensitivity = slider.getValue();
        }else slider.setValue(sensitivity);


        this.offsetX =game.getPreferences().readOffsetX();
        this.offsetY =game.getPreferences().readOffsetY();
        OptionsController.getInstance().setOffsetX(this.offsetX);
        OptionsController.getInstance().setOffsetY(this.offsetY);

        checkBox.setChecked(game.getPreferences().readJoystick());

    }

    private void createCheckBox() {
        BitmapFont font = MyFonts.getInstance().getFont();
        Sprite off = new Sprite((Texture)game.getAssetManager().get("off.png"));
        off.setSize(width/6,width/12);
        Sprite on = new Sprite((Texture)game.getAssetManager().get("on.png"));
        on.setSize(width/6,width/12);
        SpriteDrawable offDrawable = new SpriteDrawable(off);
        SpriteDrawable onDrawable = new SpriteDrawable(on);
        CheckBox.CheckBoxStyle s = new CheckBox.CheckBoxStyle(offDrawable,onDrawable,font, Color.RED);

        checkBox = new CheckBox(" JoyStick",s);

        checkBox.setPosition(width/10,5*height/8);
        stage.addActor(checkBox);
    }



    private void loadAssets() {
        this.game.getAssetManager().load( "back.png" , Texture.class);
        this.game.getAssetManager().load( "ball.png" , Texture.class);
        this.game.getAssetManager().load( "calibrate.png" , Texture.class);
        this.game.getAssetManager().load( "enemy.png" , Texture.class);
        this.game.getAssetManager().load( "whiteRectangle.png" , Texture.class);
        this.game.getAssetManager().load("1.png" , Texture.class);
        this.game.getAssetManager().load("joystick.png" , Texture.class);
        this.game.getAssetManager().load("on.png" , Texture.class);
        this.game.getAssetManager().load("off.png" , Texture.class);

        this.game.getAssetManager().finishLoading();
    }

    public void createButtons(){
        float radius = OptionsModel.getInstance().getCallibrateModel().getRadius();
        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("calibrate.png")));
        calibrate = new Button(buttonDrawable);
        calibrate.setSize(width/3,width/3);
        float x = (5*width)/6 - width/6 - radius/PIXEL_TO_METER;
        float y =  width/3-width/6 + radius/PIXEL_TO_METER;
        calibrate.setPosition(x,y);
        stage.addActor(calibrate);

        lblsensitivity = new Label(String.format("%s: ", "Sensitivity"), new Label.LabelStyle(MyFonts.getInstance().getFont(), Color.RED));
        lblsensitivity.setPosition((VIEWPORT_WIDTH/10)/PIXEL_TO_METER,VIEWPORT_WIDTH/5/PIXEL_TO_METER);
        stage.addActor(lblsensitivity);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
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
        game.getPreferences().writeSensitivity(slider.getValue());



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

        slider = new Slider(1,7,1/100f,false,sliderStyle);
        slider.setPosition((VIEWPORT_WIDTH/10)/PIXEL_TO_METER,VIEWPORT_WIDTH/8/PIXEL_TO_METER);

        slider.setSize((VIEWPORT_WIDTH/2)/PIXEL_TO_METER,VIEWPORT_WIDTH/20/PIXEL_TO_METER);
        stage.addActor(slider);
    }

    private void handleInputs(float delta) {


        if (Gdx.input.isKeyPressed(Input.Keys.BACK)||Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            OptionsController.getInstance().delete();
            game.setScreen(new MenuView(game));
        }


        if(calibrate.isPressed()){
            this.offsetX = OptionsController.getInstance().getReadingX();
            this.offsetY = OptionsController.getInstance().getReadingY();
            game.getPreferences().writeOffsetX(this.offsetX);
            game.getPreferences().writeOffsetY(this.offsetY);
            OptionsController.getInstance().setOffsetX(this.offsetX);
            OptionsController.getInstance().setOffsetY(this.offsetY);
        }

        if(checkBox.isChecked()){
          game.getPreferences().writeJoystick(true);
        }else {
            game.getPreferences().writeJoystick(false);
        }


    }




}
