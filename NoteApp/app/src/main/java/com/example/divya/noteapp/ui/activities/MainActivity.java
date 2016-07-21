package com.example.divya.noteapp.ui.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.divya.noteapp.R;
import com.example.divya.noteapp.model.Note;
import com.example.divya.noteapp.receiver.AlarmReceiver;
import com.example.divya.noteapp.ui.fragments.CreateMessageFragment;
import com.example.divya.noteapp.ui.fragments.MessageListFragment;
import com.example.divya.noteapp.utils.NoteAlarmManager;

public class MainActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private  Note note ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        MessageListFragment fragment = new MessageListFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().getExtras()!=null){
            note= (Note)getIntent().getExtras().getSerializable(AlarmReceiver.NOTE);
            if((note!=null)&&(note.getId()!=0)) {
                CreateMessageFragment fragment = new CreateMessageFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("Notification", true);
                bundle.putSerializable(AlarmReceiver.NOTE, note);
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().
                        replace(R.id.fragment_container, fragment)
                        .commit();
            }
        }

       else if(fragmentManager.getBackStackEntryCount()==0){
            MessageListFragment fragment = new MessageListFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    private void initViews() {
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        fragmentManager = getSupportFragmentManager();
    }
}
