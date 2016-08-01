package com.example.divya.wallpaperapp.NotificationService;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import com.example.divya.wallpaperapp.R;
import com.example.divya.wallpaperapp.WallpaperApp;

/**
 * Created by divya on 1/8/16.
 */
public class NotifyManager {
    private static final NotifyManager INSTANCE = new NotifyManager();

    public static NotifyManager getInstance(){
        return INSTANCE;
    }

    public void setNotification(int id) {
        Intent intent = new Intent(WallpaperApp.context, NotificationReciever.class);
        intent.putExtra(WallpaperApp.context.getString(R.string.image_id), id);
        WallpaperApp.context.sendBroadcast(intent);
    }

    public void updateNotification(int id, String msg) {
        NotificationManager notificationManager = (NotificationManager)WallpaperApp.context.getSystemService(
                Context.NOTIFICATION_SERVICE);
    }

    public void cancelNotification(int id) {
        NotificationManager notificationManager = (NotificationManager)WallpaperApp.context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }
}
