package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Tiago Neves on 07/04/2017.
 */

public class SpaceBallsGame extends Game{
    /**
     * Manages the game assets
     */
    private AssetManager assetManager;

    /**
     * Creates a new game and set the current screen
     */
    @Override
    public void create() {
        assetManager = new AssetManager();

        // Sets the game screen
        setScreen(new MenuScreen(this));
    }

    /**
     * Returns the asset manager
     *
     * @return the asset manager
     */
    AssetManager getAssetManager() {
        return assetManager;
    }
}
