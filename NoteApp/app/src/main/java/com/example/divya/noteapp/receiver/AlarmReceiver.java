package com.example.divya.noteapp.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.divya.noteapp.model.Note;
import com.example.divya.noteapp.R;
import com.example.divya.noteapp.ui.activities.MainActivity;

/**
 * Created by divya on 12/7/16.
 */
public class AlarmReceiver extends BroadcastReceiver {

    public static final String NOTE = "note";

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getExtras();
        if(extras != null) {
            Note note = (Note) extras.getSerializable(NOTE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(note.getTitle());
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setAutoCancel(true);
            builder.setDefaults(Notification.DEFAULT_ALL);

            Intent activityIntent = new Intent(context, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(NOTE, note);
            activityIntent.putExtras(bundle);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, (int)note.getId(), activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify((int)note.getId(), builder.build());
        }
    }
}
