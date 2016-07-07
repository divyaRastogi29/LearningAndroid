package com.example.divya.networkingone;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    List<String> images  = new ArrayList<>();
    RecyclerView recyclerView;
    ImageAdapter imageAdapter;
    ProgressBar progressId;
    Button nextButton;
    String nextPageUrl ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        new ResponseLoader().execute(nextPageUrl);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        nextButton.setOnClickListener(this);
    }

    private void initViews() {
        nextPageUrl = "http://api.pexels.com/v1/search?query=people";
        progressId = (ProgressBar)findViewById(R.id.progressId);
        nextButton = (Button)findViewById(R.id.next_button);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewId);
    }

    @Override
    public void onClick(View v) {
        images  = new ArrayList<>();
        new ResponseLoader().execute(nextPageUrl);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
    }


    private class ResponseLoader extends AsyncTask<String,Void,JSONObject>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            toggleProgress(true);
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JsonParser jsonParser = new JsonParser();
            JSONObject jsonObject = jsonParser.getJsonFromUrl(params[0]);
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            toggleProgress(false);
            getImagesOfPage(jsonObject);
            imageAdapter = new ImageAdapter(images);
            recyclerView.setAdapter(imageAdapter);

        }
        private void toggleProgress(boolean show){
          progressId.setVisibility(show? View.VISIBLE:View.GONE);
        }
    }


    public void getImagesOfPage(JSONObject jsonObject) {
        try {
            JSONArray array = jsonObject.getJSONArray("photos");
             nextPageUrl = (String)jsonObject.getString("next_page");
            for (int i=0 ;i<array.length();i++){
                Log.d("JSONOBJECT",jsonObject.toString());
                JSONObject photo = (JSONObject)array.get(i);
                Log.d("PHOTO",photo+"");
                JSONObject src = (JSONObject)photo.get("src");
                Log.d("SRC",src+"");
                String imgUrl = src.getString("tiny");
                imgUrl.replace("\\","");
                Log.d("ImageUrl",imgUrl);
                images.add(imgUrl);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}
