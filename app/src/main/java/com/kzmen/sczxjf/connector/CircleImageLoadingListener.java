package com.kzmen.sczxjf.connector;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.utils.BitmapUtils;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 满足默认图片居中，下载完后铺满的ImageLoadingListener
 * 需要传入加载图片的ImageView对象
 * Created by 杨操 on 2016/3/17.
 * 2016年8月1日 停止使用（图片耗内存部分手机bitmap.recycle();产生异常）替代品{@link com.kzmen.sczxjf.view.RoundImageView}
 */
public class CircleImageLoadingListener implements ImageLoadingListener {
    private ImageView imageView;
    public CircleImageLoadingListener(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void onLoadingStarted(String s, View view) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(R.drawable.userhead);
    }

    @Override
    public void onLoadingFailed(String s, View view, FailReason failReason) {
        imageView.setImageResource(R.drawable.userhead);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }

    @Override
    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if(bitmap!=null){
            Bitmap bm = BitmapUtils.toRoundBitmap(bitmap,80);
            imageView.setImageBitmap(bm);
            if(!bitmap.isRecycled()&&bm!=bitmap){
                bitmap.recycle();
            }
        }
    }

    @Override
    public void onLoadingCancelled(String s, View view) {
        imageView.setImageResource(R.drawable.userhead);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }
}
