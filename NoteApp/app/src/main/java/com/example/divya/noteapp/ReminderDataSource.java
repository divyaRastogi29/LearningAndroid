package com.example.divya.noteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by divya on 2/7/16.
 */
public class ReminderDataSource{
    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] allColumns = {DbHelper.IMAGE_COLOR, DbHelper.COLUMN_ID,DbHelper.COLUMN_TITLE,DbHelper.COLUMN_REMINDER};

    public static ReminderDataSource getInstance(Context context){
        return new ReminderDataSource(context);
    }

    private ReminderDataSource(Context context) {
        dbHelper = new DbHelper(context);
    }
    public void open()throws SQLException{
        database= dbHelper.getWritableDatabase();
    }
    public void close(){
        dbHelper.close();
    }
    public Note createNote(String title, String reminder){
        ContentValues values = new ContentValues();
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(125)+125, rnd.nextInt(125)+125, rnd.nextInt(125)+125);
        values.put(DbHelper.IMAGE_COLOR,color);
        values.put(DbHelper.COLUMN_TITLE,title);
        values.put(DbHelper.COLUMN_REMINDER,reminder);
        long insertId = database.insert(DbHelper.TABLE_NAME,null,values);
        String[] ids = {insertId+""};
        Cursor cursor = database.query(DbHelper.TABLE_NAME, allColumns,
                DbHelper.COLUMN_ID+"=?", ids, null,null,null);
        cursor.moveToFirst();
        Note newNote = cursorToNote(cursor);
        cursor.close();
        return newNote;
    }

    public void updateNote(long id, String title, String reminder, int color){
        ContentValues values = new ContentValues();
        values.put(DbHelper.IMAGE_COLOR,color);
        values.put(DbHelper.COLUMN_TITLE,title);
        values.put(DbHelper.COLUMN_REMINDER,reminder);
        String[] ids = {id+""};
       int noOfRowsAffected = database.update(DbHelper.TABLE_NAME, values, DbHelper.COLUMN_ID+"=?", ids);
        Log.d("Updated : ",noOfRowsAffected+"");
    }

    public void deleteNote(Note note){
        long id = note.getId();
        Log.d("Deletion","Comment deleted with id : "+id);
        String[] ids = {note.getId()+""};
        database.delete(DbHelper.TABLE_NAME,DbHelper.COLUMN_ID+"=?",ids);
    }

    public List<Note> getAllNotes(){
        List<Note> notes = new ArrayList<>();
        Cursor cursor = database.query(DbHelper.TABLE_NAME, allColumns,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
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
        return note;
    }
}
