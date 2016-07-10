package com.example.divya.noteapp;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by divya on 6/7/16.
 */
public class NoReminderFragment extends Fragment{
    private FloatingActionButton createButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.no_reminder_layout,container,false);
        createButton = (FloatingActionButton)view.findViewById(R.id.createButton);
        createButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReminderFragment fragment = new ReminderFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        });
        return view;
    }
}
