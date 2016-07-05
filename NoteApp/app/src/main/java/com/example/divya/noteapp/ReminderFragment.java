package com.example.divya.noteapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by divya on 5/7/16.
 */
public class ReminderFragment extends Fragment implements View.OnClickListener{
    private EditText editTitle;
    private EditText editReminder;
    private Button submitButton;
    private ReminderDataSource reminderDataSource;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout,container,false);
        savedInstanceState = this.getArguments();
        reminderDataSource = (ReminderDataSource)savedInstanceState.getSerializable("reminderDataSource");
        initViews(view);
        submitButton.setOnClickListener(this);
        return view;
    }

    private void initViews(View view) {
        submitButton = (Button)view.findViewById(R.id.submitButton);
        editTitle = (EditText) view.findViewById(R.id.titleNote);
        editReminder = (EditText)view.findViewById(R.id.reminderNote);
    }

    @Override
    public void onClick(View v) {
        String title = editTitle.getText().toString();
        String reminder = editReminder.getText().toString();
        Note note = reminderDataSource.createNote(title, reminder);
        Log.d("Note Inserted",note.toString());
    }
}
