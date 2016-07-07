package com.example.divya.intenttwo;

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

import java.util.List;

/**
 * Created by divya on 23/6/16.
 */
public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.TreeViewHolder> {
    private Context context;
    private List<Green> list;

    GreenAdapter(Context context, List<Green> list){
        this.context = context ;
        this.list = list ;
    }
    @Override
    public TreeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_one, parent, false);
        return new TreeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TreeViewHolder holder, int position) {
      final Green greenItem = list.get(position);
        holder.img.setImageResource(greenItem.getImg());
        holder.text.setText(greenItem.getText());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemOne.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("green",greenItem);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TreeViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView text;
        View v;
        TreeViewHolder(View v){
            super(v);
            this.v = v;
            img = (ImageView)v.findViewById(R.id.imgItem);
            text = (TextView)v.findViewById(R.id.textItem);
        }
    }
}

