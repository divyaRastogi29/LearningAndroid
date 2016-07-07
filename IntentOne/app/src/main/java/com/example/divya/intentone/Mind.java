package com.example.divya.intentone;

import java.io.Serializable;

/**
 * Created by divya on 17/6/16.
 */
public class Mind implements Serializable {
    String name ;
    int img;

    public Mind() {}

    public Mind(String name, int img) {
        setImg(img);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }


}
