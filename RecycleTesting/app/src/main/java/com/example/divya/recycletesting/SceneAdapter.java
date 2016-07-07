package com.example.divya.recycletesting;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by divya on 13/6/16.
 */
public class SceneAdapter extends ArrayAdapter<Scene> {
    public SceneAdapter(Context context, ArrayList<Scene> scenes){
        super(context,0,scenes);
    }
    @Override
    public View getView(int position , View convertView, ViewGroup parent){
        Scene scene = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_one, parent, false);
        }
        ImageView imgView = (ImageView)convertView.findViewById(R.id.imgId);
        TextView textView = (TextView)convertView.findViewById(R.id.txtId);
        imgView.setImageResource(scene.getImg());
        textView.setText(scene.getName());
        return convertView;
    }
}
