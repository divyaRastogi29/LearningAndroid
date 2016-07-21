package com.example.divya.noteapp.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.divya.noteapp.NoteApp;
import com.example.divya.noteapp.model.Note;
import com.example.divya.noteapp.receiver.AlarmReceiver;

import java.util.Calendar;

/**
 * Created by divya on 16/7/16.
 */
public class NoteAlarmManager {

    private static final String TAG = NoteAlarmManager.class.getSimpleName();

    private static final NoteAlarmManager sInstance = new NoteAlarmManager();

    private NoteAlarmManager() {

    }

    public static NoteAlarmManager getInstance() {
        return sInstance;
    }

    public void setAlarm(Calendar calendar, Note note) {
        AlarmManager alarmManager = (AlarmManager) NoteApp.context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(NoteApp.context, AlarmReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AlarmReceiver.NOTE, note);
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(NoteApp.context, (int)note.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if(Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else if(Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        Log.d(TAG, "Alarm set");

    }

    public void cancelAlarm(int id) {
        AlarmManager alarmManager = (AlarmManager) NoteApp.context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(NoteApp.context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(NoteApp.context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        Log.d(TAG, "Alarm cancelled");
    }
}
