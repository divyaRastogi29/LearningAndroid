package com.example.divya.wallpaperapp.ImageDownloadPool;

import java.io.File;

/**
 * Created by divya on 31/7/16.
 */
public interface DownloadManager {

    void downloadAsync(int id, String url, Callback callback);

    File downloadSync(String url);
}
