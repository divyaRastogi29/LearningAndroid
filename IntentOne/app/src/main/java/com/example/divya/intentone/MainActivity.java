package com.example.divya.intentone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    List<Mind> mindList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        mindList = new ArrayList<>();
        RecyclerView rv = (RecyclerView)findViewById(R.id.recyclerId);
        RecycleAdapter adapter = new RecycleAdapter(this, mindList);
        rv.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(layoutManager);
        populateMindList();
    }

    public void populateMindList(){
        Mind a = new Mind();
        a.setName("brain");
        a.setImg(R.drawable.college);
        mindList.add(a);
        Mind b = new Mind();
        b.setName("nose");
        b.setImg(R.drawable.college);
        mindList.add(b);
        mindList.add(new Mind("brain", R.drawable.college));
        mindList.add(new Mind("brain", R.drawable.college));
        mindList.add(new Mind("brain", R.drawable.college));
        mindList.add(new Mind("brain", R.drawable.college));
        mindList.add(new Mind("brain", R.drawable.college));
        mindList.add(new Mind("brain", R.drawable.college));
        mindList.add(new Mind("brain", R.drawable.college));
        mindList.add(new Mind("brain", R.drawable.college));
        mindList.add(new Mind("brain", R.drawable.college));
        mindList.add(new Mind("brain", R.drawable.college));
        mindList.add(new Mind("brain", R.drawable.college));
        mindList.add(new Mind("brain", R.drawable.college));
    }
}
