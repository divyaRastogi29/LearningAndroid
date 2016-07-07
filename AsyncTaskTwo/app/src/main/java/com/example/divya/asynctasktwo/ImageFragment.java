package com.example.divya.asynctasktwo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by divya on 26/6/16.
 */
public class ImageFragment extends Fragment {

    private ImageView imageView;
    private ProgressBar progressBar;
    private ImageDownload imageDownloadTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        initViews(view);
        bindViews();
        return view;
    }

    private void bindViews() {
        String url = "https://i.imgur.com/tGbaZCY.jpg";
        imageDownloadTask = new ImageDownload();
        imageDownloadTask.execute(url);
    }

    private void initViews(View view) {
        imageView = (ImageView) view.findViewById(R.id.imageView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }

    private class ImageDownload extends AsyncTask<String, Void,Bitmap> {


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
            catch (IOException e){
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
            toggleProgressBar(false);
            imageView.setImageBitmap(bitmap);
        }
    }

    private void toggleProgressBar(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
