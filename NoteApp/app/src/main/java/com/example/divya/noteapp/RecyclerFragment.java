package com.example.divya.noteapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by divya on 6/7/16.
 */
public class RecyclerFragment extends Fragment {
    List<Note> notesList;
    private RecyclerView recyclerView;
    private ReminderDataSource reminderDataSource;
    private NoteAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        reminderDataSource = ReminderDataSource.newInstance(getActivity());
        reminderDataSource.open();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_fragment_layout, container, false);
        initViews(view);
        bindViews();
        return view;
    }

    private void bindViews() {
        adapter = new NoteAdapter(notesList,reminderDataSource);
        ItemTouchHelper.SimpleCallback callback = new NoteTouchHelper(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        new DbThread().execute("");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initViews(View view) {
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewId);
        notesList = new ArrayList<>();
    }

    public class DbThread extends AsyncTask<String,Void,List<Note>> {
        @Override
        protected List<Note> doInBackground(String... params) {
            notesList = reminderDataSource.getAllNotes();
            return notesList;
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            super.onPostExecute(notes);
            if(notes.size()!=0) {
                adapter.newData(notes);
                adapter.notifyDataSetChanged();
            }
            else{
               FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                NoReminderFragment fragment = new NoReminderFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        }
    }
}
