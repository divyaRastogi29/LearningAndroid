package com.example.divya.noteapp;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by divya on 6/7/16.
 */
public class NoteTouchHelper extends ItemTouchHelper.SimpleCallback {
    private NoteAdapter noteAdapter;
    public NoteTouchHelper(NoteAdapter noteAdapter) {
        super(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
        this.noteAdapter = noteAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        noteAdapter.removeNote(viewHolder.getAdapterPosition());
    }
}
