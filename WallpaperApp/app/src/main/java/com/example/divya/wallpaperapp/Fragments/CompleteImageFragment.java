package com.example.divya.wallpaperapp.Fragments;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.divya.wallpaperapp.R;
import com.example.divya.wallpaperapp.VolleyLibrary.RequestQueueServer;
import com.example.divya.wallpaperapp.WallpaperApp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * Created by divya on 22/7/16.
 */
public class CompleteImageFragment extends Fragment {
    public static final String URL="url";
    NetworkImageView imageView;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.complete_image_fragment, container, false);
        setHasOptionsMenu(true);
        final String url = getArguments().getString(URL);
        initViews(view);
        final ImageLoader mImageLoader = RequestQueueServer.getInstance().getmImageLoader();
        imageView.setImageUrl(url,mImageLoader);
        return view;
    }

    private InputStream getInputStreamImage(Drawable drawable){
        BitmapDrawable bitmapDrawable = ((BitmapDrawable) drawable);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);
        return bis;
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
            case R.id.saveId :
                return true;
            case R.id.wallpaperId :
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(WallpaperApp.context);
                Drawable drawable = imageView.getDrawable();
                try {
                    wallpaperManager.setStream(getInputStreamImage(drawable));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
