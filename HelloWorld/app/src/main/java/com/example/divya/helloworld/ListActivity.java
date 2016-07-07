package com.example.divya.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by divya on 23/5/16.
 */
public class ListActivity extends Activity {

    String[] mobile = {"android", "windows", "iphone"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listest_activity);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_two, mobile);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
