package com.example.divya.networkingone;

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
    List<String> urls = new ArrayList<>();
    public ImageAdapter(List<String> urls){
        this.urls = urls;
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

    private class LoadImages extends AsyncTask<String,Void,Bitmap> {
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
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            InputStream in = null;
            try{
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.connect();
                in = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(in);
            }
            catch(IOException e){
                e.printStackTrace();
            }

            finally {
                if(in!=null)
                    try {
                        in.close();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            toggleProgressBar(false);
            holder.imageView.setImageBitmap(bitmap);
        }

        private void toggleProgressBar(boolean show){
            holder.progressBar.setVisibility(show?View.VISIBLE:View.GONE);
        }
    }
}
