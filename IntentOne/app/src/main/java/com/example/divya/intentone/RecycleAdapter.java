package com.example.divya.intentone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by divya on 17/6/16.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {
    private List<Mind> mindList = new ArrayList<>();
    private Context context;

    public RecycleAdapter(Context context, List<Mind> mindList){
        this.mindList = mindList;
        this.context = context;
    }

    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_one, parent,false);
        Log.d("RecyclerAdapter", "onCreateViewHolder()");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecycleAdapter.MyViewHolder holder, int position) {
       final Mind mind = mindList.get(position);
        holder.imageView.setImageResource(mind.getImg());
        holder.textView.setText(mind.getName());
        Log.d("RecyclerAdapter", "onBindViewHolder()");

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("mind", mind);
                Intent intent = new Intent(context, ItemOneActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mindList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView ;
        public ImageView imageView;
        public View view;
        MyViewHolder(View v){
            super(v);
            view = v;
            textView = (TextView)v.findViewById(R.id.textTwo);
            imageView = (ImageView)v.findViewById(R.id.imgItemId);
            //v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            Mind mind = mindList.get(getAdapterPosition());
            bundle.putSerializable("mind", mind);
            Intent intent = new Intent(context, ItemOneActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }
}


