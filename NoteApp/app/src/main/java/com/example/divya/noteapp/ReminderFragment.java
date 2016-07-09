package com.example.divya.noteapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
    private Button submitButton;
    private Button backButton;
    private ReminderDataSource reminderDataSource;
    private Note note;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        reminderDataSource = ReminderDataSource.newInstance(getActivity());
        reminderDataSource.open();
        initViews(view);
        submitButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
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
        submitButton = (Button)view.findViewById(R.id.submitButton);
        backButton = (Button)view.findViewById(R.id.backButton);
        editTitle = (EditText) view.findViewById(R.id.titleNote);
        editReminder = (EditText)view.findViewById(R.id.reminderNote);
    }

    @Override
    public void onClick(View v) {
        Toast toast;
        if(v.getId()==R.id.submitButton) {
            if((note == null)||(note.getId()==0)) {
                note = new Note();

                String title = editTitle.getText().toString().trim();
                String reminder = editReminder.getText().toString().trim();
                if (title.length() > 0) {
                    note = reminderDataSource.createNote(title, reminder);
                    toast = Toast.makeText(getActivity(), "Reminder Set", Toast.LENGTH_SHORT);
                    Log.d("Note Inserted", note.toString());
                } else
                    toast = Toast.makeText(getActivity(), "Title cannot be left empty", Toast.LENGTH_SHORT);

            }
            else{
                String title = editTitle.getText().toString();
                String reminder = editReminder.getText().toString();
                if (title.length() > 0) {
                    reminderDataSource.updateNote(note.getId(),title, reminder, note.getImgColor());
                    toast = Toast.makeText(getActivity(), "Reminder Updated", Toast.LENGTH_SHORT);
                    Log.d("Note Updated", note.toString());
                } else
                    toast = Toast.makeText(getActivity(), "Title cannot be left empty", Toast.LENGTH_SHORT);
            }
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            editTitle.setText("");
            editReminder.setText("");

        }
        if(v.getId()==R.id.backButton){
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            RecyclerFragment fragment = new RecyclerFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
