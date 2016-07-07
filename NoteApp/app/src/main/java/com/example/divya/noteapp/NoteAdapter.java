package com.example.divya.noteapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

/**
 * Created by divya on 5/7/16.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder>{
    private  List<Note> notesList;
    private ReminderDataSource reminderDataSource;

    public  NoteAdapter(List<Note> notesList, ReminderDataSource reminderDataSource){
        this.notesList = notesList;
        this.reminderDataSource = reminderDataSource ;
    }
    public void newData(List<Note> notesList){
        this.notesList = notesList;
    }
    public void removeNote(int position){
        reminderDataSource.deleteNote(notesList.get(position));
        notesList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,notesList.size());
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Note note = notesList.get(position);
        holder.reminderText.setText(note.getReminder());
        int initial = note.getTitle().charAt(0);
        if((initial>=97)&&(initial<=122))
            initial = initial-32;
        holder.imgCircle.setText((char)initial+"");
        String newTitleSubString = (char)initial+note.getTitle().substring(1);
        holder.titleText.setText(newTitleSubString);

        Drawable background = holder.imgCircle.getBackground();

        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background).getPaint().setColor(note.getImgColor());

        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setColor(note.getImgColor());


        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable) background).setColor(note.getImgColor());
        }
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
