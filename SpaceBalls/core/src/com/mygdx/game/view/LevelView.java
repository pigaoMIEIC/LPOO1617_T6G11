package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.LevelType;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.controller.LevelController;
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

    float sensitivity;

    boolean joystick;

    private LevelType.levelType currLevel;


    public LevelView(SpaceBallsGame game,LevelType.levelType newLevel) {

        super(game);

        this.stage.setViewport(new StretchViewport(VIEWPORT_WIDTH/PIXEL_TO_METER,VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER));

        Gdx.input.setInputProcessor(stage);

        String[] array  = {"back.png",
                "exterior.png",
                "end.png",
                "inside.png"};
        loadAssets(array);

        currLevel = newLevel;

        LevelController lvlContrl = LevelController.getInstance();

        sensitivity = game.getPreferences().readSensitivity();
        LevelController.getInstance().setSensitivity(sensitivity);
        joystick = game.getPreferences().readJoystick();
        if(joystick)
            createJoystick();

        //lvlContrl.setJoystick(game.getPreferences().readJoystick());
        lvlContrl.setOffsetX(game.getPreferences().readOffsetX());
        lvlContrl.setOffsetY(game.getPreferences().readOffsetY());

        camera = createCamera();
        Gdx.input.setCatchBackKey(true);
    }


//    private void loadAssets() {
//        this.game.getAssetManager().load("back.png" , Texture.class);
//        this.game.getAssetManager().load("exterior.png", Texture.class);
//        this.game.getAssetManager().load("end.png",Texture.class);
//        this.game.getAssetManager().load("inside.png", Texture.class);
//        this.game.getAssetManager().finishLoading();
//    }

    public void render(float delta) {
        LevelController lvlContrl = LevelController.getInstance();

        stage.setDebugAll(true);

       lvlContrl.update(delta);

        super.render(delta);

        debugPhysics(lvlContrl.getWorld());

        if(joystick)
          lvlContrl.accelerate(touchpad.getKnobPercentX()/16,touchpad.getKnobPercentY()/16);

        //checks if the player Won
        if(lvlContrl.getWin()){
            backToMenu();
        }

        //checks if the player lost
        if(lvlContrl.isColliding()){
            lvlContrl.delete();
            game.setScreen(new GameOverView(game,this));
        }

    }


    @Override
    void drawEntities() {

        BallModel ballModel = LevelModel.getInstance(currLevel).getPlayerModel();
        drawView(ballModel);

        EndBallModel endBall = LevelModel.getInstance(currLevel).getEndBall();
        EntityView view = ViewFactory.makeView(game, endBall, currLevel);
        view.update(endBall);
        view.draw(game.getBatch());


        for (int i = 0; i < LevelModel.getInstance(currLevel).getEnemySize(); i++) {
            EnemyModel enemyModel = LevelModel.getInstance(currLevel).getEnemyModel(i);
           drawView(enemyModel);
        }

        for (int i = 1; i < LevelModel.getInstance(currLevel).getObstaclesSize(); i++) {
            StaticModel obstacleModel = LevelModel.getInstance(currLevel).getStaticModel(i);
            drawView(obstacleModel);
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
