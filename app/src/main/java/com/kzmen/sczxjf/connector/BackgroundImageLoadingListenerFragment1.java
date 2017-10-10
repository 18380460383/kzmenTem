package com.kzmen.sczxjf.connector;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 满足默认图片居中，下载完后铺满的ImageLoadingListener
 * 需要传入加载图片的ImageView对象
 * Created by 杨操 on 2016/3/17.
 */
public class BackgroundImageLoadingListenerFragment1 implements ImageLoadingListener {
    private ImageView imageView;

    public BackgroundImageLoadingListenerFragment1(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void onLoadingStarted(String s, View view) {
        try {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } catch (Exception e) {

        }
    }

    @Override
    public void onLoadingFailed(String s, View view, FailReason failReason) {
        try {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } catch (Exception e) {

        }
    }

    @Override
    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
        try {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } catch (Exception e) {

        }
    }

    @Override
    public void onLoadingCancelled(String s, View view) {
        try {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } catch (Exception e) {

        }
    }
}
