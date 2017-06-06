package com.mygdx.game.client;


import com.mygdx.game.Preferences;

/**
 * Created by Tiago Neves on 25/05/2017.
 */

/**
 * Preferences interface implementation for android deployment
 */
public class ActionResolverHtml implements Preferences {

    @Override
    public float readSensitivity() {
        return 0;
    }

    @Override
    public void writeSensitivity(float sensitivity) {

    }

    @Override
    public float readOffsetX() {
        return 0;
    }

    @Override
    public void writeOffsetX(float offset) {

    }

    @Override
    public float readOffsetY() {
        return 0;
    }

    @Override
    public void writeOffsetY(float offset) {

    }

    @Override
    public boolean readJoystick() {
        return false;
    }

    @Override
    public void writeJoystick(boolean joystick) {

    }
}
