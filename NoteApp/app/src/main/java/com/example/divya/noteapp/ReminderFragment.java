package com.example.divya.noteapp;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by divya on 5/7/16.
 */
public class ReminderFragment extends Fragment implements View.OnClickListener{
    private EditText editTitle;
    private EditText editReminder;
    private FloatingActionButton submitButton;
    private ReminderDataSource reminderDataSource;
    private Note note;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        reminderDataSource = ReminderDataSource.newInstance(getActivity());
        reminderDataSource.open();
        initViews(view);
        submitButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        submitButton.setOnClickListener(this);
        if (getArguments() != null) {
            note = (Note) getArguments().getSerializable("note");
            if (note != null) {
                editTitle.setText(note.getTitle());
                editReminder.setText(note.getReminder());
            }
        }
        return view;
    }
    private void initViews(View view) {
        submitButton = (FloatingActionButton)view.findViewById(R.id.submitButton);
        editTitle = (EditText) view.findViewById(R.id.titleNote);
        editReminder = (EditText)view.findViewById(R.id.reminderNote);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.submitButton) {
            if((note == null)||(note.getId()==0)) {
                note = new Note();

                String title = editTitle.getText().toString().trim();
                String reminder = editReminder.getText().toString().trim();
                if (title.length() > 0) {
                    note = reminderDataSource.createNote(title, reminder);
                    getFragmentManager().popBackStackImmediate();
                    Log.d("Note Inserted", note.toString());
                } else
                    Snackbar.make(v,"Title cannot be left empty",Snackbar.LENGTH_SHORT).show();
            }
            else{
                String title = editTitle.getText().toString();
                String reminder = editReminder.getText().toString();
                if (title.length() > 0) {
                    reminderDataSource.updateNote(note.getId(),title, reminder, note.getImgColor());
                    getFragmentManager().popBackStackImmediate();
                    Log.d("Note Updated", note.toString());
                } else
                    Snackbar.make(v,"Title cannot be left empty",Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
