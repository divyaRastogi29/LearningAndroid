package com.example.divya.recycletesting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

/**
 * Created by divya on 12/6/16.
 */
public class ListingActivityThree extends Activity implements View.OnClickListener {
    TextView tv ;
    ImageView img;
    static int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.images_first);
        Button button = (Button)findViewById(R.id.button);
        img = (ImageView)findViewById(R.id.imgId);
        tv = (TextView)findViewById(R.id.textID);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      i = (i+1)%3;
        if(i==0) {
            tv.setText("pragati");
            img.setImageResource(R.drawable.pragati);
        }
        if(i==1){
            tv.setText("College");
            img.setImageResource(R.drawable.college);
        }
        if(i==2){
            tv.setText("Office");
            img.setImageResource(R.drawable.office);
        }
    }
}


