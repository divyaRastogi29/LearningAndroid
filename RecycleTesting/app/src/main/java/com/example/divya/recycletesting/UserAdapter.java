package com.example.divya.recycletesting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by divya on 25/5/16.
 */
public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter(Context context, ArrayList<User> users){
        super(context,0,users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        User user = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_item, parent, false);
        }
        TextView tvName = (TextView)convertView.findViewById(R.id.textName);
        TextView tvHomeTown = (TextView)convertView.findViewById(R.id.homeTown);
        tvName.setText(user.userName);
        tvHomeTown.setText(user.homeTown);
        return convertView;
    }
}
