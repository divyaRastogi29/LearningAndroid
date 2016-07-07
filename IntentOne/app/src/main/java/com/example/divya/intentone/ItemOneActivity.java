package com.example.divya.intentone;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by divya on 15/6/16.
 */
public class ItemOneActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_one);
        TextView txt = (TextView)findViewById(R.id.textTwo);
        ImageView img = (ImageView)findViewById(R.id.imgItemId);
        Mind mind = (Mind) getIntent().getExtras().getSerializable("mind");
        txt.setText(mind.getName());
        img.setImageResource(mind.getImg());
    }
}
