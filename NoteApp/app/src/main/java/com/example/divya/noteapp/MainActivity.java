package com.example.divya.noteapp;

import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        RecyclerFragment fragment = new RecyclerFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void initViews() {
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        fragmentManager = getSupportFragmentManager();
    }

}
