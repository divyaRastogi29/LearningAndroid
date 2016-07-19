package com.example.divya.noteapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by divya on 16/7/16.
 */
public class NoteApp extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
