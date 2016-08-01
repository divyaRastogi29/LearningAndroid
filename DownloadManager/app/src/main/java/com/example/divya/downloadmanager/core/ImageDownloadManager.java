package com.example.divya.downloadmanager.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.MediaStore;
import android.util.Log;

import com.example.divya.downloadmanager.DownloaderApp;
import com.example.divya.downloadmanager.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by divya on 31/7/16.
 * no library should be used
 */
public class ImageDownloadManager implements DownloadManager {

    private static ImageDownloadManager sInstance = null;

    private Context context;

    private  ExecutorService executorService ;

    private ImageDownloadManager(Context context) {

        this.context = context;
        executorService = Executors.newFixedThreadPool(3);
    }

    public static ImageDownloadManager getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new ImageDownloadManager(context);
        }
        return sInstance;
    }

    @Override
    public void downloadAsync(final String urlInput, final Callback callback) {
        /**
         * download should happen on worker thread
         * Executor service
         * download image and store in file
         * download if success - callback.onSuccess(File path)
         * if error - callback.onFailure()
         */
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                if (isConnectingToInternet()) {
                    Bitmap bitmap = downloadImage(urlInput);
                    Log.d("Image Downloaded", "done");
                    int a = DownloaderApp.sharedPreferences.getInt(DownloaderApp.context.getResources().
                            getString(R.string.image_id), 0);
                    DownloaderApp.updateId(++a);
                    /**
                     * Saving Image To Gallery
                     */
                    String path = MediaStore.Images.Media.insertImage(DownloaderApp.context.getContentResolver(),
                            bitmap, "Image" + (a), "Downloaded");
                    Log.d("Image save to path : "+path, "done");

                    callback.onSuccess(new File(path));
                }
                else{
                    callback.onError();
                }
            }
        });

    }

    @Override
    public File downloadSync(String urlInput) {

        /**
         * download happen on calling thread
         * download and save in file
         * return file
         */
        if(isConnectingToInternet()){
            Bitmap bitmap = downloadImage(urlInput);
            Log.d("Image Downloaded", "done");
            int a = DownloaderApp.sharedPreferences.getInt(DownloaderApp.context.getResources().
                    getString(R.string.image_id), 0);
            DownloaderApp.updateId(++a);
            /**
             * Saving Image To Gallery
             */
            String path = MediaStore.Images.Media.insertImage(DownloaderApp.context.getContentResolver(),
                    bitmap, "Image" + (a), "Downloaded");
            Log.d("Image save to path : "+path, "done");
            return new File(path);
        }
        else{
            return null;
        }
    }

    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public Bitmap downloadImage(String urlInput){
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            bitmap = null;
            in = null;
            URL url = new URL(urlInput);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            in = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(in);
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return bitmap;
    }
}
