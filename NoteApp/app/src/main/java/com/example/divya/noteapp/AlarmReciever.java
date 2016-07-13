package com.example.divya.noteapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by divya on 12/7/16.
 */
public class AlarmReciever extends Service{
    Alarm alarm = new Alarm();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
