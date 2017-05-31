package com.mygdx.game;


/**
 * Created by Tiago Neves on 25/05/2017.
 */

public interface Preferences {
    float readSensitivity();
    void writeSensitivity(float sensitivity);
    float readOffsetX();
    void writeOffsetX(float offset);
    float readOffsetY();
    void writeOffsetY(float offset);
    boolean readJoystick();
    void writeJoystick(boolean joystick);
}
