package com.example.divya.wallpaperapp.ImageDownloadPool;

import android.net.Uri;

import java.io.File;

/**
 * Created by divya on 31/7/16.
 */
public interface Callback {

    void onSuccess(Uri uri);

    void onError();
}
