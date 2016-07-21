package com.example.divya.noteapp.utils;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by akash on 7/21/16.
 */
public class Utils {

    private static String[] COLORS = {"#F44336" ,"#E91E63", "#9C27B0", "#673AB7", "#3F51B5", "#2196F3", "#009688", "#4CAF50", "#FFEB3B", "#FF9800", "#FF5722", "#607D8B"};

    public static int getRandomColor() {
        Random rnd = new Random();
        return Color.parseColor(COLORS[rnd.nextInt(COLORS.length)]);
    }
}
