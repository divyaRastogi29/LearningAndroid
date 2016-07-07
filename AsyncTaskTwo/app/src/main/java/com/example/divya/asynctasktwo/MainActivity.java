package com.example.divya.asynctasktwo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button nextBtn, prevBtn;
    private int currentFragmentPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        bindViews();
        showFragment(currentFragmentPos);
    }

    private void bindViews() {
        nextBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);
    }

    private void initViews() {
        nextBtn = (Button) findViewById(R.id.btn_next);
        prevBtn = (Button) findViewById(R.id.btn_prev);
    }

    private void showFragment(int pos) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;

        switch (pos) {

            case 0:
                fragment = new ImageFragment();
                break;

            case 1:
                fragment = new ImageFragment2();
                break;

            case 2:
                fragment = new ImageFragment3();
                break;

            case 3:
                fragment = new ImageFragment4();
                break;

            case 4:
                fragment = new ImageFragment5();
                break;

        }

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {

            case R.id.btn_next:
                showNextImg();
                break;

            case R.id.btn_prev:
                showPrevImg();
                break;
        }
    }

    private void showPrevImg() {
        currentFragmentPos--;
        if(currentFragmentPos < 0) {
            currentFragmentPos = 4;
        }
        showFragment(currentFragmentPos);
    }

    private void showNextImg() {
        currentFragmentPos++;
        showFragment(currentFragmentPos % 5);
    }
}
