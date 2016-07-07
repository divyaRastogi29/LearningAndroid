package com.example.divya.recycletesting;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by divya on 25/5/16.
 */
public class ListingActivityTwo extends Activity {
    ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listing);
        users.add(new User("Divya","Lucknow"));
        users.add(new User("Akash","Gurgaon"));
        users.add(new User("Shantanu","Delhi"));
        UserAdapter adapter = new UserAdapter(this, users);

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
