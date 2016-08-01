package com.example.divya.downloadmanager.core;

import java.io.File;

/**
 * Created by divya on 31/7/16.
 */
public interface Callback {

    void onSuccess(File file);

    void onError();
}
