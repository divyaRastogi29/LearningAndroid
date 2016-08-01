package com.example.divya.sharedprefapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;
    SharedPreferences sharedPrefernces;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        textView.setText((sharedPrefernces.getInt(getString(R.string.number),0))+"");
    }

    private void initViews() {
        textView = (TextView)findViewById(R.id.textId);
        editText = (EditText)findViewById(R.id.editTextId);
        sharedPrefernces = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPrefernces.edit();
        int i = 0;
        editor.putInt(getString(R.string.number),i);
        editor.commit();
    }
}
