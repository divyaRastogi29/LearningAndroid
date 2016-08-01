package com.example.divya.downloadmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.divya.downloadmanager.core.Callback;
import com.example.divya.downloadmanager.core.ImageDownloadManager;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String imgUrl = "";

        ImageDownloadManager.getInstance(getApplicationContext()).downloadAsync(imgUrl, new Callback() {
            @Override
            public void onSuccess(File file) {
                //imageView.setImge
            }

            @Override
            public void onError() {

            }
        });


        new Thread() {
            @Override
            public void run() {
                super.run();
                File file = ImageDownloadManager.getInstance(getApplicationContext()).downloadSync(imgUrl);
            }
        };
    }
}
