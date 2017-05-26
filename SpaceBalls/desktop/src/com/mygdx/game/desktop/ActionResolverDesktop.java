package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Preferences;

/**
 * Created by Tiago Neves on 26/05/2017.
 */

public class ActionResolverDesktop implements Preferences{
    @Override
    public float readSensitivity() {
        com.badlogic.gdx.Preferences prefs = Gdx.app.getPreferences("My Preferences");
        float s = prefs.getFloat("sensitivity", 0);
        return s;
    }

    @Override
    public void writeSensitivity(float sensitivity) {
        com.badlogic.gdx.Preferences prefs = Gdx.app.getPreferences("My Preferences");
        prefs.putFloat("sensitivity", sensitivity);
    }

    @Override
    public float readOffsetX() {
        com.badlogic.gdx.Preferences prefs = Gdx.app.getPreferences("My Preferences");
        float s = prefs.getFloat("offsetX", 0);
        return s;
    }

    @Override
    public void writeOffsetX(float offset) {
        com.badlogic.gdx.Preferences prefs = Gdx.app.getPreferences("My Preferences");
        prefs.putFloat("offsetX", offset);
    }

    @Override
    public float readOffsetY() {
        com.badlogic.gdx.Preferences prefs = Gdx.app.getPreferences("My Preferences");
        float s = prefs.getFloat("offsetY", 0);
        return s;
    }

    @Override
    public void writeOffsetY(float offset) {
        com.badlogic.gdx.Preferences prefs = Gdx.app.getPreferences("My Preferences");
        prefs.putFloat("offsetY", offset);
    }

    @Override
    public boolean readJoystick() {
        com.badlogic.gdx.Preferences prefs = Gdx.app.getPreferences("My Preferences");
        boolean f = prefs.getBoolean("joystick", false);
        return f;
    }

    @Override
    public void writeJoystick(boolean joystick) {
        com.badlogic.gdx.Preferences prefs = Gdx.app.getPreferences("My Preferences");
        prefs.putBoolean("joystick", joystick);
    }
}
