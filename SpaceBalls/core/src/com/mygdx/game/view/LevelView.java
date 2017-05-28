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
import com.mygdx.game.model.entities.EndBallModel;
import com.mygdx.game.model.entities.EnemyModel;
import com.mygdx.game.model.entities.StaticModel;
import com.mygdx.game.view.entities.EntityView;
import com.mygdx.game.view.entities.ViewFactory;

/**
 * Created by Tiago Neves on 26/05/2017.
 */

public class LevelView extends GameView {

    /**
     * The camera used to show the viewport.
     */
    private final OrthographicCamera camera;

    private final SpaceBallsGame game;

    float sensitivity;

    boolean joystick;

    private LevelType.levelType currLevel;


    public LevelView(SpaceBallsGame game,LevelType.levelType newLevel) {
        super(game);
        this.game = game;
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


    private void loadAssets() {
        this.game.getAssetManager().load("back.png" , Texture.class);
        this.game.getAssetManager().load("exterior.png", Texture.class);
        this.game.getAssetManager().load("end.png",Texture.class);
        this.game.getAssetManager().load("inside.png", Texture.class);
        this.game.getAssetManager().finishLoading();
    }

    public void render(float delta) {
        stage.setDebugAll(true);

        LevelController.getInstance().update(delta);

        super.render(delta);

        debugPhysics(LevelController.getInstance().getWorld());

        if(joystick)
           LevelController.getInstance().accelerate(touchpad.getKnobPercentX()/16,touchpad.getKnobPercentY()/16);

        //checks if the player Won
        if(LevelController.getInstance().getWin()){
            backToMenu();
        }

        //checks if the player lost
        if(LevelController.getInstance().isColliding()){
            LevelController.getInstance().delete();
            game.setScreen(new GameOverView(game,this));
        }

    }


    @Override
    void drawEntities() {

        BallModel ballModel = LevelModel.getInstance(currLevel).getPlayerModel();
        EntityView view = ViewFactory.makeView(game, ballModel);
        view.update(ballModel);
        view.draw(game.getBatch());

        EndBallModel endBall = LevelModel.getInstance(currLevel).getEndBall();
        view = ViewFactory.makeView(game, endBall);
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

    @Override
    void handleInputs(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)||Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            backToMenu();
        }
    }


    public LevelType.levelType getCurrLevel() {
        return currLevel;
    }


}
