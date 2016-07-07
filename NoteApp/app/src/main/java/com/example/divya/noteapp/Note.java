package com.example.divya.noteapp;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by divya on 2/7/16.
 */
public class Note {
    private long id;
    private String title;
    private String reminder;
    private int imgColor;

    public int getImgColor() {
        return imgColor;
    }
    public void setImgColor(int imgView) {
        this.imgColor = imgView;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReminder() {
        return reminder;
    }
    public void setReminder(String reminder) {
        this.reminder = reminder;
    }



    public String toString(){
        return id+" "+title+" "+reminder;
    }
}
