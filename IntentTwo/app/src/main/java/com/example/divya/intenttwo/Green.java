package com.example.divya.intenttwo;

import java.io.Serializable;

/**
 * Created by divya on 23/6/16.
 */
public class Green implements Serializable{
    private int img;
    private String text;
    Green(int img, String text){
        this.img = img ;
        this.text = text ;
    }
    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
