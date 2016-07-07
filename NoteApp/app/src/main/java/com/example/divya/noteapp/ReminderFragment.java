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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout,container,false);
        reminderDataSource = ReminderDataSource.newInstance(getActivity());
        reminderDataSource.open();
        initViews(view);
        submitButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
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
        if(v.getId()==R.id.submitButton) {
            String title = editTitle.getText().toString();
            String reminder = editReminder.getText().toString();
            Note note = reminderDataSource.createNote(title, reminder);
            Toast toast=Toast.makeText(getActivity(),"Reminder Set",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            editTitle.setText("");
            editReminder.setText("");
            Log.d("Note Inserted", note.toString());
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
