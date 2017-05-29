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

    boolean joystick;

    private LevelType.levelType currLevel;


    public LevelView(SpaceBallsGame game,LevelType.levelType newLevel) {

        super(game);

        this.stage.setViewport(new StretchViewport(VIEWPORT_WIDTH/PIXEL_TO_METER,VIEWPORT_WIDTH*RATIO/PIXEL_TO_METER));

        Gdx.input.setInputProcessor(stage);

        String[] array  = {"back.png",
                "exterior.png",
                "end.png",
                "inside.png",
                "whiteSquare.png"
        };
        loadAssets(array);

        currLevel = newLevel;

        LevelController lvlContrl = LevelController.getInstance(currLevel);

        sensitivity = game.getPreferences().readSensitivity();

        LevelController.getInstance(currLevel).setSensitivity(game.getPreferences().readSensitivity());
        joystick = game.getPreferences().readJoystick();
        if(joystick) {
            LevelController.getInstance(currLevel).setJoystick(joystick);
            createJoystick();
        }

        lvlContrl.setOffset(game.getPreferences().readOffsetX(),game.getPreferences().readOffsetY());

        camera = createCamera();
        Gdx.input.setCatchBackKey(true);
    }


    public void render(float delta) {
        LevelController lvlContrl = LevelController.getInstance(currLevel);

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

        float accelX = Gdx.input.getAccelerometerX();
        float accelY = Gdx.input.getAccelerometerY();

        lvlContrl.setAccelaration(accelX,accelY);

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

    protected void backToMenu(){
        LevelController.getInstance(currLevel).delete();
        game.setScreen(new MenuView(game));
    }


}
