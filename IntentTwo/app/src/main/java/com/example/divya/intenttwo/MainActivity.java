package com.example.divya.intenttwo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Green> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateItemList();
        RecyclerView rv = (RecyclerView)findViewById(R.id.recyclerId);
        GreenAdapter adapter = new GreenAdapter(getApplicationContext(),itemList);
        rv.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(layoutManager);

    }

    public void populateItemList(){
        itemList = new ArrayList<>();
        Green g1 = new Green(R.drawable.picone, "Beatuiful!!");
        itemList.add(g1);
        Green g2 = new Green(R.drawable.pictwo, "Amazing!!");
        itemList.add(g2);
        Green g3 = new Green(R.drawable.picthree, "Startling!!");
        itemList.add(g3);
        Green g4 = new Green(R.drawable.picfour, "Crazy!!");
        itemList.add(g4);
        Green g5 = new Green(R.drawable.picfive, "Mesmerizing!!");
        itemList.add(g5);
        Green g6 = new Green(R.drawable.college, "Nostalgic!!");
        itemList.add(g6);
    }
}
