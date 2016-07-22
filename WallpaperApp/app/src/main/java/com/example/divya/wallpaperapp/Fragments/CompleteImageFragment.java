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
import android.view.View;
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

/**
 * Created by divya on 22/7/16.
 */
public class CompleteImageFragment extends Fragment {
    public static final String URL="url";
    NetworkImageView imageView;
    ImageView menuImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.complete_image_fragment, container, false);
        final String url = getArguments().getString(URL);
        initViews(view);
        final ImageLoader mImageLoader = RequestQueueServer.getInstance().getmImageLoader();
        imageView.setImageUrl(url,mImageLoader);
        menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(WallpaperApp.context);
                Drawable drawable = imageView.getDrawable();
                BitmapDrawable bitmapDrawable = ((BitmapDrawable) drawable);
                Bitmap bitmap = bitmapDrawable .getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageInByte = stream.toByteArray();
                ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);
                try {
                    wallpaperManager.setStream(bis);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    private void initViews(View view) {
        imageView = (NetworkImageView) view.findViewById(R.id.fullImageId);
        menuImage = (ImageView)getActivity().findViewById(R.id.menuImage);
        menuImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        menuImage.setVisibility(View.GONE);
        getFragmentManager().popBackStackImmediate();
    }
}
