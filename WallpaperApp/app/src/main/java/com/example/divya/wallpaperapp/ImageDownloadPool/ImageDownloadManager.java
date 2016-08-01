package com.example.divya.wallpaperapp.ImageDownloadPool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import com.example.divya.wallpaperapp.R;
import com.example.divya.wallpaperapp.WallpaperApp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
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
    public void downloadAsync(final int id, final String urlInput, final Callback callback) {
        /**
         * download should happen on worker thread
         * Executor service
         * download image and store in file
         * download if success - callback.onSuccess(File path)
         * if error - callback.onFailure()
         */
        if (!isConnectingToInternet()) {
            callback.onError();
        } else {
            final String[] path = new String[1];
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = downloadImage(urlInput);
                    Log.d("Image Downloaded", "done");
                    /**
                     * Saving Image To Gallery
                     */
                    path[0] = MediaStore.Images.Media.insertImage(WallpaperApp.context.getContentResolver(),
                            bitmap, "Image" + (id), "Downloaded");
                    Log.d("Image save to path : " + path[0], "done");
                    Uri uri = Uri.parse(path[0]);
                    Log.d("URI",uri.getPath()) ;
                    callback.onSuccess(uri);
                    Log.d("OnSuccess","OnSuccess Called");
                }
            });
        }
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
            int a = WallpaperApp.sharedPreferences.getInt(WallpaperApp.context.getResources().
                    getString(R.string.image_id), 0);
            WallpaperApp.updateId(++a);
            /**
             * Saving Image To Gallery
             */
            String path = MediaStore.Images.Media.insertImage(WallpaperApp.context.getContentResolver(),
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
