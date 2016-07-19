package com.example.divya.noteapp.data_manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by divya on 2/7/16.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "NoteReminders";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "reminders";
    public static final String IMAGE_COLOR = "image";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_REMINDER = "reminder";
    private static final String query = "create table "+TABLE_NAME+" ("+IMAGE_COLOR+" integer, "+COLUMN_ID+ " integer primary key " +
            "autoincrement, "+COLUMN_TITLE+ ","+COLUMN_REMINDER+" not null);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("Upgrade","Upgrade called");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL(query);
    }
}
