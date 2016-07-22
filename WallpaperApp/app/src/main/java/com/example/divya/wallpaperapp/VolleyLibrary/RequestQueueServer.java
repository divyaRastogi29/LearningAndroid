package com.example.divya.wallpaperapp.VolleyLibrary;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.divya.wallpaperapp.WallpaperApp;

/**
 * Created by divya on 22/7/16.
 */
public class RequestQueueServer {
    private static RequestQueueServer mInstance;
    private RequestQueue mRequestQueue ;
    private ImageLoader mImageLoader;

    private RequestQueueServer(){
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache(){

                    private final LruCache<String, Bitmap> cache = new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized RequestQueueServer getInstance(){
        if(mInstance==null)
            mInstance = new RequestQueueServer();
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue==null)
            mRequestQueue = Volley.newRequestQueue(WallpaperApp.context);
        return mRequestQueue;
    }

    public void addToRequestQueue(Request request){
        mRequestQueue.add(request);
    }

    public ImageLoader getmImageLoader(){
        return mImageLoader;
    }

}
