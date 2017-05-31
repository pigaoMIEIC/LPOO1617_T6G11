package com.mygdx.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

/**
 * Created by Tiago Neves on 25/05/2017.
 */

/**
 * Preferences interface implementation for android deployment
 */
public class ActionResolverAndroid implements Preferences {
    public static final String myPref = "preferenceName";

    Handler handler;
    Context context;

    public ActionResolverAndroid(Context context) {
        handler = new Handler();
        this.context = context;
    }

    @Override
    public float readSensitivity() {
        SharedPreferences sp = context.getSharedPreferences(myPref,Context.MODE_PRIVATE);
        float sensitivity = sp.getFloat("sensitivity",0);
        return sensitivity;
    }


    @Override
    public void writeSensitivity(float sensitivity) {
        SharedPreferences.Editor editor = context.getSharedPreferences(myPref, Context.MODE_PRIVATE).edit();
        editor.putFloat("sensitivity", sensitivity);
        editor.commit();
    }

    @Override
    public float readOffsetX() {
        SharedPreferences sp = context.getSharedPreferences(myPref,Context.MODE_PRIVATE);
        float offset = sp.getFloat("offsetX",0);
        return offset;
    }

    @Override
    public void writeOffsetX(float offset) {
        SharedPreferences.Editor editor = context.getSharedPreferences(myPref, Context.MODE_PRIVATE).edit();
        editor.putFloat("offsetX", offset);
        editor.commit();
    }

    @Override
    public float readOffsetY() {
        SharedPreferences sp = context.getSharedPreferences(myPref,Context.MODE_PRIVATE);
        float offset = sp.getFloat("offsetY",0);
        return offset;
    }

    @Override
    public void writeOffsetY(float offset) {
        SharedPreferences.Editor editor = context.getSharedPreferences(myPref, Context.MODE_PRIVATE).edit();
        editor.putFloat("offsetY", offset);
        editor.commit();
    }

    @Override
    public boolean readJoystick() {
        SharedPreferences sp = context.getSharedPreferences(myPref,Context.MODE_PRIVATE);
        boolean joystick = sp.getBoolean("joystick",false);
        return joystick;
    }

    @Override
    public void writeJoystick(boolean joystick) {
        SharedPreferences.Editor editor = context.getSharedPreferences(myPref, Context.MODE_PRIVATE).edit();
        editor.putBoolean("joystick", joystick);
        editor.commit();
    }
}
