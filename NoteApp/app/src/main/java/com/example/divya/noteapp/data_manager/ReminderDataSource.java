package com.example.divya.noteapp.data_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;

import com.example.divya.noteapp.NoteApp;
import com.example.divya.noteapp.model.Note;
import com.example.divya.noteapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by divya on 2/7/16.
 */
public class ReminderDataSource {

    private static final ReminderDataSource INSTANCE = new ReminderDataSource();

    private SQLiteDatabase database;

    private DbHelper       dbHelper;

    private String[]       allColumns = { DbHelper.IMAGE_COLOR, DbHelper.COLUMN_ID, DbHelper.COLUMN_TITLE,
            DbHelper.COLUMN_REMINDER, DbHelper.isAlarmSet, DbHelper.TIME };

    public static ReminderDataSource getInstance() {
        return INSTANCE;
    }

    private ReminderDataSource() {
        dbHelper = new DbHelper(NoteApp.context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public synchronized Note createNote(String title, String reminder, int isAlarmSet, String time) {
        ContentValues values = new ContentValues();

        int color = Utils.getRandomColor();
        values.put(DbHelper.IMAGE_COLOR, color);
        values.put(DbHelper.COLUMN_TITLE, title);
        values.put(DbHelper.COLUMN_REMINDER, reminder);
        values.put(DbHelper.isAlarmSet, isAlarmSet);
        values.put(DbHelper.TIME, time);
        long insertId = database.insert(DbHelper.TABLE_NAME, null, values);
        String[] ids = { insertId + "" };
        Cursor cursor = database.query(DbHelper.TABLE_NAME, allColumns, DbHelper.COLUMN_ID + "=?", ids, null, null, null);
        cursor.moveToFirst();
        Note newNote = cursorToNote(cursor);
        cursor.close();

        return newNote;
    }

    public synchronized void updateNote(long id, String title, String reminder, int color, int isAlarmSet, String time) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.IMAGE_COLOR, color);
        values.put(DbHelper.COLUMN_TITLE, title);
        values.put(DbHelper.COLUMN_REMINDER, reminder);
        values.put(DbHelper.isAlarmSet, isAlarmSet);
        values.put(DbHelper.TIME, time);
        String[] ids = { id + "" };
        int noOfRowsAffected = database.update(DbHelper.TABLE_NAME, values, DbHelper.COLUMN_ID + "=?", ids);
        Log.d("Updated : ", noOfRowsAffected + "");
    }

    public synchronized void deleteNote(Note note) {
        long id = note.getId();
        Log.d("Deletion", "Comment deleted with id : " + id);
        String[] ids = { note.getId() + "" };
        database.delete(DbHelper.TABLE_NAME, DbHelper.COLUMN_ID + "=?", ids);
    }

    public synchronized List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        Cursor cursor = database.query(DbHelper.TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Note note = cursorToNote(cursor);
            notes.add(note);
            cursor.moveToNext();
        }
        cursor.close();
        return notes;
    }

    private Note cursorToNote(Cursor cursor) {
        Note note = new Note();
        note.setImgColor(cursor.getInt(0));
        note.setId(cursor.getLong(1));
        note.setTitle(cursor.getString(2));
        note.setReminder(cursor.getString(3));
        note.setisAlarmSet(cursor.getInt(4));
        note.setTime(cursor.getString(5));
        return note;
    }
}
