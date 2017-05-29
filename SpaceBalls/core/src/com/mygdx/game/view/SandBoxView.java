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

    SandBoxController controller = SandBoxController.getInstance();

    /**
     * Method to call the superclass constructor and loads the necessary assets. Creates the necessary objects in the stage.
     *
     * @param game The game which will be associated with the screenAdapter
     */
    public SandBoxView(SpaceBallsGame game) {
        super(game);

        String[] array = {"back.png",
                "ball.png",
                "enemy.png" ,
                "transparent.png",
                "exterior.png",
                "inside.png"};
        super.loadAssets(array);

        Skin skin = new Skin();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        TextureRegionDrawable textureBar = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("whiteRectangle.png"))));
        ProgressBar.ProgressBarStyle barStyle = new ProgressBar.ProgressBarStyle(skin.newDrawable("white", Color.RED), textureBar);
        barStyle.knobBefore = barStyle.knob;
        bar = new ProgressBar(0, 5, 1/60f, false, barStyle);
        bar.setPosition(0, VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER);
        bar.setSize(VIEWPORT_WIDTH/PIXEL_TO_METER,0.8f);
        stage.addActor(bar);

        sensitivity = game.getPreferences().readSensitivity();
        SandBoxController.getInstance().setSensitivity(sensitivity);
        joystick = game.getPreferences().readJoystick();
        if(joystick)
            createJoystick();

        SandBoxController.getInstance().setJoystick(game.getPreferences().readJoystick());
        SandBoxController.getInstance().setOffset(game.getPreferences().readOffsetX(),game.getPreferences().readOffsetY());

    }

    /**
     * Method to update the world and render the updated view
     * @param delta Time delta from the last update
     */
    @Override
    public void render(float delta) {;
        controller.update(delta);

        super.render(delta);

        debugPhysics(controller.getWorld());

        if(controller.isColliding()){
            controller.setColliding(false);
            controller.delete();
            game.setScreen(new GameOverView(game,this));
            return;
        }


        bar.setValue(controller.getSeconds());

        if(joystick)
            controller.accelerate(touchpad.getKnobPercentX()/16,touchpad.getKnobPercentY()/16);
        else{
            float accelX = Gdx.input.getAccelerometerX();
            float accelY = Gdx.input.getAccelerometerY();
            controller.accelerate(accelX,accelY);
        }


        if(Gdx.input.justTouched() && joystick){
            float conversionX = VIEWPORT_WIDTH/PIXEL_TO_METER/Gdx.graphics.getWidth();
            float conversionY = VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER/Gdx.graphics.getHeight();
            float radius = VIEWPORT_WIDTH/12/PIXEL_TO_METER;
            touchpad.setPosition(Gdx.input.getX()*conversionX - radius,(Gdx.graphics.getHeight() - Gdx.input.getY())*conversionY - radius);
            touchpad.toFront();
            System.out.println(touchpad.getX());
        }
    }

    /**
     * Method that handles the inputs from the stage
     */
    @Override
    void handleInputs() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            controller.delete();
            game.setScreen(new MenuView(game));
        }
    }

    /**
     * Method that draws the views of the models present in the scene
     */
    @Override
    void drawEntities() {
        for (int i = 0; i < SandBoxModel.getInstance().getnBalls(); i++) {
            EnemyModel ballModel = SandBoxModel.getInstance().getEnemyModel(i);
            drawView(ballModel);
        }

        BallModel ballModel = SandBoxModel.getInstance().getPlayerModel();
        drawView(ballModel);


    }

    /**
     * Getter for the stage
     * @return Returns the stage
     */
    public Stage getStage() {
        return stage;
    }

}
