package com.example.divya.downloadmanager.core;

import java.io.File;

/**
 * Created by divya on 31/7/16.
 */
public interface DownloadManager {

    void downloadAsync(String url, Callback callback);

    File downloadSync(String url);
}
