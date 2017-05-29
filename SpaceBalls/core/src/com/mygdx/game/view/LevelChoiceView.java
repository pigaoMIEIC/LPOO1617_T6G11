package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
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

    private Button tmpButton;

    private Vector<Button> levels = new Vector<Button>();
    private ImageButton returni;


    /**
     * Method to call the superclass constructor and load the assets and buttons
     *
     * @param game The game which will be associated with the screenAdapter
     */
    public LevelChoiceView(SpaceBallsGame game) {
        super(game);

        String[] array = {"back.png",
                "1.png",
                "2.png",
                "3.png",
                "4.png",
                "5.png"
        };
        loadAssets(array);
        createButtons();
    }

    /**
     * Method to create the buttons for the menu
     */
    public void createButtons(){
        float width = VIEWPORT_WIDTH/PIXEL_TO_METER;
        float height = VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER;
        float buttonSize =Math.min((height/4)-(height/20),(width-(width/4)-(width/4))/5);
        AssetManager assetManager = game.getAssetManager();

        float horzSpacing = (width-(width/4)-(5*buttonSize))/4f;

        Drawable buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)assetManager.get("1.png")));
        tmpButton = new ImageButton(buttonDrawable);
        tmpButton.setSize(buttonSize,buttonSize);
        tmpButton.setPosition(width/8,(height/2)-(buttonSize/2));
        levels.addElement(tmpButton);
        stage.addActor(tmpButton);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)assetManager.get("2.png")));
        tmpButton = new Button(buttonDrawable);
        tmpButton.setSize(buttonSize,buttonSize);
        tmpButton.setPosition(width/8+(buttonSize+horzSpacing),(height/2)-(buttonSize/2));
        levels.addElement(tmpButton);
        stage.addActor(tmpButton);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)assetManager.get("3.png")));
        tmpButton = new Button(buttonDrawable);
        tmpButton.setSize(buttonSize,buttonSize);
        tmpButton.setPosition(width/8+(buttonSize+horzSpacing)*2,(height/2)-(buttonSize/2));
        levels.addElement(tmpButton);
        stage.addActor(tmpButton);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)assetManager.get("4.png")));
        tmpButton = new Button(buttonDrawable);
        tmpButton.setSize(buttonSize,buttonSize);
        tmpButton.setPosition(width/8+(buttonSize+horzSpacing)*3,(height/2)-(buttonSize/2));
        levels.addElement(tmpButton);
        stage.addActor(tmpButton);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)assetManager.get("5.png")));
        tmpButton = new Button(buttonDrawable);
        tmpButton.setSize(buttonSize,buttonSize);
        tmpButton.setPosition(width/8+(buttonSize+horzSpacing)*4,(height/2)-(buttonSize/2));
        levels.addElement(tmpButton);
        stage.addActor(tmpButton);

        //(buttonYSize+horzSpacing)


    }

    /**
     * Method to update the world and render the updated view
     * @param delta Time delta from the last update
     */
    @Override
    public void render(float delta) {
        MenuController controler = MenuController.getInstance();
        stage.setDebugAll(true);
        float x = Gdx.input.getAccelerometerX();
        float y = Gdx.input.getAccelerometerY();
        controler.setAccelX(x);
        controler.setAccelY(y);
        controler.update(delta);

        super.render(delta);

        debugPhysics(MenuController.getInstance().getWorld());
    }

    /**
     * Method that draws the views of the models present in the scene
     */
    @Override
    void drawEntities() {
        for (int i = 0; i < RANDNR; i++) {
            BallModel ballModel = MenuModel.getInstance().getBallModel(i);
            drawView(ballModel);
        }

        for (int i = 0; i < SRANDNR; i++) {
            StaticBallModel staticBallModel = MenuModel.getInstance().getStaticBallModel(i);
            drawView(staticBallModel);
        }
    }

    /**
     * Method that handles the inputs from the stage
     */
    @Override
    void handleInputs() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            this.dispose();
            game.setScreen(new MenuView(game));
        }

        if(levels.elementAt(0).isPressed()){
            game.setScreen(new LevelView(game, LevelType.levelType.ONE));
        }

        if(levels.elementAt(1).isPressed()){
            game.setScreen(new LevelView(game, LevelType.levelType.TWO));
        }
    }

}
