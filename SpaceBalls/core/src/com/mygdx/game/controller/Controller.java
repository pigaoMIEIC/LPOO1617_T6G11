package com.mygdx.game.controller;

/**
 * Created by Tiago Neves on 29/05/2017.
 */

public abstract class Controller {
    float offsetX = 0;
    float offsetY = 0;

    /**
     * @param offsetx
     * @param offsety
     * Sets the accelerometer offset
     */
    public void setOffset(float offsetx,float offsety){
        offsetX = offsetx;
        offsetY = offsety;
    }

}
