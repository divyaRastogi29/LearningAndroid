package com.example.divya.swipeviewone;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by divya on 24/6/16.
 */
public class DemoObjectFragment  extends Fragment {

    private  String page;

   public static DemoObjectFragment newInstance(String text){
       DemoObjectFragment fragment = new DemoObjectFragment();
       Log.d("AgainAdapter","Twice At Adapter After Fragment Call");
       Bundle args = new Bundle();
       args.putString("text",text);
       fragment.setArguments(args);
       return fragment;
   }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
       Log.d("AtFragment","I am on Fragment On Create View");
       View rootView = inflater.inflate(R.layout.item_layout,container,false);
       Bundle args = getArguments();
      ((TextView)rootView.findViewById(R.id.textItem)).setText(page);
      return rootView;
   }
}
