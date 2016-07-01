package com.example.divya.networkingone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by divya on 30/6/16.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    List<String> urls ;
    Context context;

    public ImageAdapter(List<String> urls, Context context){
        this.urls = urls;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        new LoadImages(holder).execute(urls.get(position));
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public ProgressBar progressBar;
        MyViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.imgId);
            progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        }
    }

    private class LoadImages extends AsyncTask<String,Void,RequestCreator> {
        MyViewHolder holder;
        LoadImages(MyViewHolder holder){
            this.holder = holder;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            toggleProgressBar(true);
        }

        @Override
        protected RequestCreator doInBackground(String... params) {
            RequestCreator requestCreator= Picasso.with(context).load(params[0]).resize(700,0);
            return requestCreator;
        }

        @Override
        protected void onPostExecute(RequestCreator requestCreator) {
            super.onPostExecute(requestCreator);
            toggleProgressBar(false);
            requestCreator.into(holder.imageView);
        }

        private void toggleProgressBar(boolean show){
            holder.progressBar.setVisibility(show?View.VISIBLE:View.GONE);
        }
    }
}
