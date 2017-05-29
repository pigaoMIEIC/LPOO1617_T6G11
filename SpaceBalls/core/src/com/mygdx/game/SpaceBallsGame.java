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

    private final Preferences preferences;

    /**
     * Constructor for the game class
     *
     * @param preferences interface to communicate with the required implementation (android,desktop,ios)
     */
    public SpaceBallsGame(Preferences preferences) {
        this.preferences = preferences;
    }


    private SpriteBatch batch;

    /**
     * Manages the game assets
     */
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

    /**
     * Loads the necessary fonts and sets the screen for the entry menu
     */
    private void startGame() {
        MyFonts.getInstance();
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


    /**
     *
     * Return the Sprite Batch
     *
     * @return
     */
    public SpriteBatch getBatch() {
        return batch;
    }

//    public void setJoystick(boolean joystick) {
//        this.joystick = joystick;
//    }
//
//    public boolean hasJoystick() {
//
//        return joystick;
//    }

    /**
     *
     * Returns the preferences interface
     *
     * @return
     */
    public Preferences getPreferences() {
        return preferences;
    }

}
