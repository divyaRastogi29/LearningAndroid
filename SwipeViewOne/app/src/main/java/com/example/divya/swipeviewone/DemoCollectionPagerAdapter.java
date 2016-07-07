package com.example.divya.swipeviewone;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by divya on 24/6/16.
 */
public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
    List<String> textList = new ArrayList<>();
    public DemoCollectionPagerAdapter(FragmentManager manager , List<String> textList){
        super(manager);
        Log.d("HelloTwo","At constructor of adapter");
    }
    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        Log.d("AtAdapter","I am at adapter getItem");
        Fragment fragment = DemoObjectFragment.newInstance(textList.get(position));
        return fragment;
    }

    @Override
    public int getCount() {
        return textList.size();
    }
}
