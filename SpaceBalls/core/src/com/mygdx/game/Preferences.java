package com.mygdx.game;

import javax.naming.Context;

/**
 * Created by Tiago Neves on 25/05/2017.
 */

public interface Preferences {
    float readSensitivity();
    void writeSensitivity(float sensitivity);
}
