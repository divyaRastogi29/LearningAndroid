package com.example.divya.wallpaperapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by divya on 21/7/16.
 */
public class WallpaperApp extends Application {
    public static Context context;
    public static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        sharedPreferences = this.getSharedPreferences(getString(R.string.key_file), Context.MODE_PRIVATE);
        updateId(0);

    }

    public static void updateId(int id){
        editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.image_id),id);
        editor.commit();
    }
}
