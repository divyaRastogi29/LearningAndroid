package com.example.divya.intenttwo;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by divya on 23/6/16.
 */
public class ItemOne extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_one);
        TextView txt = (TextView)findViewById(R.id.textItem);
        ImageView img = (ImageView)findViewById(R.id.imgItem);
        Green green = (Green)getIntent().getExtras().getSerializable("green");
        txt.setText(green.getText());
        img.setImageResource(green.getImg());
    }
}
