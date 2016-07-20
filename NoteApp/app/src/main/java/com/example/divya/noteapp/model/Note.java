package com.example.divya.noteapp.model;

import android.view.View;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by divya on 2/7/16.
 */
public class Note implements Serializable {
    private long id;
    private String title;
    private String reminder;
    private int imgColor;
    private int isAlarmSet;
    private String time;

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

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
    public int isAlarmSet() {
        return isAlarmSet;
    }

    public void setisAlarmSet(int checked) {
        isAlarmSet = checked;
    }

    public String toString(){
        return id+" "+title+" "+reminder;
    }
}
