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
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by divya on 5/7/16.
 */
public class ReminderFragment extends Fragment implements View.OnClickListener{
    private EditText editTitle;
    private EditText editReminder;
    private FloatingActionButton submitButton;
    private ReminderDataSource reminderDataSource;
    private LinearLayout linearLayout;
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
        linearLayout = (LinearLayout)getActivity().findViewById(R.id.linearLayout);
    }

    @Override
    public void onClick(View v) {
        if((note == null)||(note.getId()==0)) {
            note = new Note();

            String title = editTitle.getText().toString().trim();
            String reminder = editReminder.getText().toString().trim();
            if (title.length() > 0) {
                note = reminderDataSource.createNote(title, reminder);
                Snackbar.make(v,"Reminder created",Snackbar.LENGTH_SHORT).show();
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
                Snackbar.make(v,"Reminder updated",Snackbar.LENGTH_SHORT).show();
                getFragmentManager().popBackStackImmediate();
                Log.d("Note Updated", note.toString());
            } else
                Snackbar.make(v,"Title cannot be left empty",Snackbar.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if((note == null)||(note.getId()==0)) {
            note = new Note();

            String title = editTitle.getText().toString().trim();
            String reminder = editReminder.getText().toString().trim();
            if (title.length() > 0) {
                note = reminderDataSource.createNote(title, reminder);
                Snackbar.make(linearLayout,"Reminder created",Snackbar.LENGTH_SHORT).show();
                Log.d("Note Inserted", note.toString());
            } else {
                if(reminder.length()>0) {
                    Snackbar.make(linearLayout, "Title cannot be left empty . Note created with default title",
                            Snackbar.LENGTH_SHORT).show();
                    note = reminderDataSource.createNote("Set title", reminder);
                }
            }
        }
        else{
            String title = editTitle.getText().toString();
            String reminder = editReminder.getText().toString();
            if (title.length() > 0) {
                reminderDataSource.updateNote(note.getId(),title, reminder, note.getImgColor());
                Snackbar.make(linearLayout,"Reminder updated",Snackbar.LENGTH_SHORT).show();
                Log.d("Note Updated", note.toString());
            } else {
                if(reminder.length()>0) {
                    Snackbar.make(linearLayout, "Title cannot be left empty . Note updated with default title",
                            Snackbar.LENGTH_SHORT).show();
                    reminderDataSource.updateNote(note.getId(), title, reminder, note.getImgColor());
                }
            }
        }
    }

}
