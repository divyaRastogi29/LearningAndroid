package com.example.divya.noteapp;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    List<Note> notesList;
    private Toolbar toolbar;
    private ImageButton createButton;
    private RecyclerView recyclerView;
    private ReminderDataSource reminderDataSource;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setSupportActionBar(toolbar);
        bindViews();
        createButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new DbThread().execute("");
    }

    private void bindViews() {
        reminderDataSource.open();
        adapter = new NoteAdapter(notesList,reminderDataSource);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initViews() {
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        createButton = (ImageButton)findViewById(R.id.createButton);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewId);
        notesList  = new ArrayList<>();
        reminderDataSource = ReminderDataSource.newInstance(getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("reminderDataSource",reminderDataSource);
        ReminderFragment fragment = new ReminderFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private class DbThread extends AsyncTask<String,Void,List<Note>>{

        @Override
        protected List<Note> doInBackground(String... params) {
            notesList = reminderDataSource.getAllNotes();
            return notesList;
        }
        @Override
        protected void onPostExecute(List<Note> s) {
            super.onPostExecute(s);
            adapter.notifyDataSetChanged();
        }
    }
}
