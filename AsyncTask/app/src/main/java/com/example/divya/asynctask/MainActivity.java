package com.example.divya.asynctask;

import android.media.MediaPlayer;
import android.media.MediaTimestamp;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.AsyncListUtil;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button bt;ProgressBar pb;TextView txt;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (Button)findViewById(R.id.btId);
        txt = (TextView) findViewById(R.id.textId);
        pb = (ProgressBar)findViewById(R.id.progressBar);

        mp = MediaPlayer.create(this, R.raw.filhaal);
        pb.setMax(mp.getDuration());
        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("CLICKED","I am again clicked");
        if(mp.isPlaying()){
            mp.pause();

        }
        else {
            new LongOperation().execute(0);
        }
    }

    private class LongOperation extends AsyncTask<Integer, Integer, String>{

        @Override
        protected String doInBackground(Integer... params) {
            mp.start();
            while(mp.isPlaying()){
                try{
                    Thread.sleep(500);
                    publishProgress(mp.getCurrentPosition());
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            txt.setText("Pause"); // txt.setText(result);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {
            txt.setText("in Progress");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pb.setProgress(values[0]);
        }
    }
}

