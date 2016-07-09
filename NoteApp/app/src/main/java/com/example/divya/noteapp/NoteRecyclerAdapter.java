package com.example.divya.noteapp;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by divya on 5/7/16.
 */
public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.MyViewHolder>{
    private  List<Note> notesList;
    private ReminderDataSource reminderDataSource;
    private FragmentManager fragmentManager;

    public NoteRecyclerAdapter(List<Note> notesList, ReminderDataSource reminderDataSource, FragmentManager fragmentManager){
        this.notesList = notesList;
        this.reminderDataSource = reminderDataSource ;
        this.fragmentManager = fragmentManager;
    }
    public void newData(List<Note> notesList){
        this.notesList = notesList;
    }
    public void removeNotes(List<Note> notesList){
        for(Note note : notesList) {
            reminderDataSource.deleteNote(note);
        }
    }
    public Note getNoteAtPosition(int pos){
        return notesList.get(pos);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Note note = notesList.get(position);
        String newTitleSubString;
        holder.reminderText.setText(note.getReminder());
        int initial = note.getTitle().charAt(0);
        if((initial>=97)&&(initial<=122))
            initial = initial-32;
        holder.imgCircle.setText((char)initial+"");
        if(note.getTitle().length()>1)
            newTitleSubString = (char)initial+note.getTitle().substring(1);
        else
            newTitleSubString = (char)initial+"";
        holder.titleText.setText(newTitleSubString);

        Drawable background = holder.imgCircle.getBackground();

        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background).getPaint().setColor(note.getImgColor());

        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setColor(note.getImgColor());


        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable) background).setColor(note.getImgColor());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReminderFragment fragment = new ReminderFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("note",note);
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView  titleText, reminderText;
        public TextView imgCircle;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleText = (TextView)itemView.findViewById(R.id.titleText);
            reminderText = (TextView)itemView.findViewById(R.id.reminderText);
            imgCircle = (TextView)itemView.findViewById(R.id.imgCircle);
        }

    }
}
