package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.SpaceBallsGame;
import com.mygdx.game.controller.MenuController;
import com.mygdx.game.model.MenuModel;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.view.entities.EntityView;
import com.mygdx.game.view.entities.ViewFactory;

import java.util.List;

/**
 * Created by Tiago Neves on 18/05/2017.
 */

public class MenuView extends ScreenAdapter {
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
    private static final float VIEWPORT_WIDTH = 4;

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

    public MenuView(SpaceBallsGame game) {
        this.game = game;
        loadAssets();
//        this.game.

        camera = createCamera();

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

        this.game.getAssetManager().finishLoading();
    }

    @Override
    public void render(float delta) {

        handleInputs(delta);
        MenuController.getInstance().update(delta);

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
            debugRenderer.render(MenuController.getInstance().getWorld(), debugCamera);
        }

    }
    private void drawEntities() {
        BallModel ballModel = MenuModel.getInstance().getBall();
        EntityView view = ViewFactory.makeView(game, ballModel);
        view.update(ballModel);
        view.draw(game.getBatch());
    }

    private void handleInputs(float delta) {
          if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                MenuController.getInstance().accelerate();
            }
    }

    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        if (DEBUG_PHYSICS) {
            debugRenderer = new Box2DDebugRenderer();
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
        }

        return camera;
    }


}
