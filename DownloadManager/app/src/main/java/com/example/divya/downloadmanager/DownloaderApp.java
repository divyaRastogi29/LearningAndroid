package com.example.divya.downloadmanager;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by divya on 31/7/16.
 */
public class DownloaderApp extends Application{
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
        editor.putInt(context.getString(R.string.image_id),0);
        editor.commit();
    }
}
