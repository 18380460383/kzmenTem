package com.kzmen.sczxjf.server;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by pjj18 on 2017/9/30.
 */

public interface ImageDownLoadCallBack {
    void onDownLoadSuccess(File file);

    void onDownLoadSuccess(Bitmap bitmap);

    void onDownLoadFailed();
}
