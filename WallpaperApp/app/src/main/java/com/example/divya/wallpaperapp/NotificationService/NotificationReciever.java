
package com.example.divya.wallpaperapp.NotificationService;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.divya.wallpaperapp.R;
import com.example.divya.wallpaperapp.WallpaperApp;

import java.util.Random;

/**
 * Created by divya on 24/7/16.
 */

public class NotificationReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra(WallpaperApp.context.getString(R.string.image_id), 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(WallpaperApp.context);
        mBuilder.setContentTitle("Downloading Image "+id);
        mBuilder.setContentText("Downloading in Progress......");
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setProgress(0,0,true);
        NotificationManager mNotifyManager = (NotificationManager)WallpaperApp.context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        mNotifyManager.notify(id, mBuilder.build());
    }

}

