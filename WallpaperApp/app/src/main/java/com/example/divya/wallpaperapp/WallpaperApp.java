package com.example.divya.wallpaperapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by divya on 21/7/16.
 */
public class WallpaperApp extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
