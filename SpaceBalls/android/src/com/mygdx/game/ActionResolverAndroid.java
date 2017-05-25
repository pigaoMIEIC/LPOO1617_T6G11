package com.mygdx.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

/**
 * Created by Tiago Neves on 25/05/2017.
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
}
