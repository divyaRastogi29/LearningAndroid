package com.example.divya.noteapp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by divya on 6/7/16.
 */
public class RecyclerFragment extends Fragment {
    List<Note> notesList;
    List<Note> notesToDelete;
    private RecyclerView recyclerView;
    private ReminderDataSource reminderDataSource;
    private NoteRecyclerAdapter adapter;
    private FloatingActionButton createButton;

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
        bindViews();
        return view;
    }

    private void bindViews() {
        adapter = new NoteRecyclerAdapter(notesList, reminderDataSource, getActivity().getSupportFragmentManager());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int mAdapterPos = viewHolder.getAdapterPosition();
                final Note note = adapter.getNoteAtPosition(mAdapterPos);
                Snackbar.make(viewHolder.itemView, "Deleted Note", Snackbar.LENGTH_SHORT).setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notesList.add(mAdapterPos, note);
                        adapter.newData(notesList);
                        adapter.notifyItemInserted(mAdapterPos);
                        recyclerView.scrollToPosition(mAdapterPos);
                        notesToDelete.remove(note);
                    }
                }).show();
                notesList.remove(mAdapterPos);
                notesToDelete.add(note);
                adapter.notifyItemRemoved(mAdapterPos);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        new DbThread().execute("");
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewId);
        notesList = new ArrayList<>();
        notesToDelete = new ArrayList<>();
        createButton = (FloatingActionButton)view.findViewById(R.id.createButton);
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.removeNotes(notesToDelete);
        notesToDelete = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public class DbThread extends AsyncTask<String, Void, List<Note>> {
        @Override
        protected List<Note> doInBackground(String... params) {
            notesList = reminderDataSource.getAllNotes();
            return notesList;
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            super.onPostExecute(notes);
            if (notes.size() != 0) {
                adapter.newData(notes);
                adapter.notifyDataSetChanged();
            } else {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                NoReminderFragment fragment = new NoReminderFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        }
    }

}
