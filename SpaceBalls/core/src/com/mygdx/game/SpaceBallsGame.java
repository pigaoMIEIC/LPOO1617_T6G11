package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.view.MenuView;

/**
 * Created by Tiago Neves on 07/04/2017.
 */

public class SpaceBallsGame extends Game{
    /**
     * Manages the game assets
     */

    private SpriteBatch batch;
    private AssetManager assetManager;

    private boolean joystick;



    /**
     * Creates a new game and set the current screen
     */
    @Override
    public void create() {batch = new SpriteBatch();
        assetManager = new AssetManager();

        startGame();
    }

    private void startGame() {
        setScreen(new MenuView(this));
    }

    /**
     * Returns the asset manager
     *
     * @return the asset manager
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setJoystick(boolean joystick) {
        this.joystick = joystick;
    }

    public boolean hasJoystick() {

        return joystick;
    }

}
