package com.example.divya.recycletesting;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by divya on 24/5/16.
 */
public class ListingActivity extends Activity {
    String[] mobile = {"lenovo","LG","Motorola","Samsung","Apple"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listing);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_item,mobile);

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
