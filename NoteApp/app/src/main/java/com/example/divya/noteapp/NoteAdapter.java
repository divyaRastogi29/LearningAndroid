package com.example.divya.noteapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

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
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Note note = notesList.get(position);
        holder.idText.setText(note.getId()+"");
        holder.titleText.setText(note.getTitle());
        holder.reminderText.setText(note.getReminder());
        holder.imageButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                reminderDataSource.deleteNote(note);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView idText, titleText, reminderText;
        public ImageButton imageButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            idText = (TextView)itemView.findViewById(R.id.idText);
            titleText = (TextView)itemView.findViewById(R.id.titleText);
            reminderText = (TextView)itemView.findViewById(R.id.reminderText);
            imageButton = (ImageButton)itemView.findViewById(R.id.deleteReminder);
        }
    }
}
