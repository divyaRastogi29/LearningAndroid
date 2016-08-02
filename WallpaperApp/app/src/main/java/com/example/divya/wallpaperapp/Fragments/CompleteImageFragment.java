package com.example.divya.wallpaperapp.Fragments;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.divya.wallpaperapp.ImageDownloadPool.Callback;
import com.example.divya.wallpaperapp.ImageDownloadPool.ImageDownloadManager;
import com.example.divya.wallpaperapp.NotificationService.NotifyManager;
import com.example.divya.wallpaperapp.R;
import com.example.divya.wallpaperapp.VolleyLibrary.RequestQueueServer;
import com.example.divya.wallpaperapp.WallpaperApp;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by divya on 22/7/16.
 */
public class CompleteImageFragment extends Fragment {
    public static final String URL = "url";
    public static final String HIGH_URL = "highQualityURL";
    String highQualityUrl;
    NetworkImageView imageView;
    private final Uri[] fileUri = new Uri[1];


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.complete_image_fragment, container, false);
        setHasOptionsMenu(true);
        final String url = getArguments().getString(URL);
        highQualityUrl = getArguments().getString(HIGH_URL);
        initViews(view);
        ImageLoader imageLoader = RequestQueueServer.getInstance().getmImageLoader();
        imageView.setImageUrl(url,imageLoader);
        return view;
    }

    private void initViews(View view) {
        imageView = (NetworkImageView) view.findViewById(R.id.fullImageId);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getFragmentManager().popBackStackImmediate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveId:
                Log.d("Save","Save Clicked");
                fileUri[0] = saveToGallery();
                return true;

            case R.id.wallpaperId:
                if(fileUri[0]==null)
                    fileUri[0] = saveToGallery();
                Log.d("Method","Came to onSuccess");
                new Thread(new SetWallPaper()).start();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Uri saveToGallery(){
        int id = WallpaperApp.sharedPreferences.getInt(WallpaperApp.context.getResources().
                getString(R.string.image_id), 0);
        WallpaperApp.updateId(++id);
        NotifyManager.getInstance().setNotification(id);
        final int finalId = id;
        ImageDownloadManager.getInstance(WallpaperApp.context).downloadAsync(id, highQualityUrl, new Callback() {
            @Override
            public void onSuccess(Uri uri) {
                fileUri[0] = uri;
                NotifyManager.getInstance().updateNotification(finalId, "Downloaded Image "+finalId);
                NotifyManager.getInstance().cancelNotification(finalId);
            }
            @Override
            public void onError() {
                Log.d("ERROR","Came to onError");
                NotifyManager.getInstance().updateNotification(finalId, "Network Error");
                NotifyManager.getInstance().cancelNotification(finalId);
            }
        });
        return fileUri[0];
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        float scaleX = ((float)newWidth) /  bitmap.getWidth();
        float scaleY = ((float)newHeight) / bitmap.getHeight();
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.postScale(scaleX, scaleY);
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight() , scaleMatrix, true);
        return scaledBitmap;
    }

    class SetWallPaper implements Runnable{
        @Override
        public void run() {
            InputStream in=null;
            DisplayMetrics metrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int height = metrics.heightPixels;
            int width = metrics.widthPixels;
            Log.d("Height",height+"");
            Log.d("Width",width+"");
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(WallpaperApp.context);
            wallpaperManager.setWallpaperOffsetSteps(1, 1);
            wallpaperManager.suggestDesiredDimensions(width, height);
            try {
                in = WallpaperApp.context.getContentResolver().openInputStream(fileUri[0]);
                BufferedInputStream bis = new BufferedInputStream(in);
                Bitmap bitmap = BitmapFactory.decodeStream(bis);
                bitmap = scaleBitmap(bitmap, width, height);
                wallpaperManager.setBitmap(bitmap);
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}