package com.example.divya.networkingone;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    List<String> images  = new ArrayList<>();
    RecyclerView recyclerView;
    ImageAdapter imageAdapter;
    ProgressBar progressId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressId = (ProgressBar)findViewById(R.id.progressId);
        new ResponseLoader().execute("http://api.pexels.com/v1/search?query=people");
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewId);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(imageAdapter);
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

        }
        private void toggleProgress(boolean show){
          progressId.setVisibility(show? View.VISIBLE:View.GONE);
        }
    }


    public void getImagesOfPage(JSONObject jsonObject) {
        try {
            JSONArray array = jsonObject.getJSONArray("photos");
            for (int i=0 ;i<array.length();i++){
                JSONObject photo = (JSONObject)array.get(i);
                Log.d("PHOTO",photo+"");
                JSONObject src = (JSONObject)photo.get("src");
                Log.d("SRC",src+"");
                String imgUrl = src.getString("original");
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
