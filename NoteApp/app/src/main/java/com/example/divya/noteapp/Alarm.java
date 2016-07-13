package com.example.divya.noteapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

/**
 * Created by divya on 12/7/16.
 */
public class Alarm extends BroadcastReceiver {
 String date,time;
    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer mp;
        mp=MediaPlayer.create(context, R.raw.ringone);
        mp.start();
        Toast.makeText(context, "Alarm set!!!!!!!!!!", Toast.LENGTH_LONG).show(); // For example

      //  wl.release();
    }

    public void cancelAlarm(Context context){
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
