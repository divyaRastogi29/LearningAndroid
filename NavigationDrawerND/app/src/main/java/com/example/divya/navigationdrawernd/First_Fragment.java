package com.example.divya.navigationdrawernd;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by divya on 28/6/16.
 */
public class First_Fragment extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout,container,false);
        ImageView img = (ImageView)view.findViewById(R.id.imgId);
        img.setImageResource(R.drawable.picone);
        Button back = (Button)view.findViewById(R.id.back_button);
        back.setOnClickListener(this);
        return view;
}

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(this).commit();
    }
}