package com.kzmen.sczxjf.control;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.utils.BitmapUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * 获取统一配置的ImageLoader的Option
 * Created by 杨操 on 2016/3/18.
 */
public class ImageLoaderOptionsControl {
    public static  DisplayImageOptions getOptions(){
        DisplayImageOptions  options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.image_def) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.image_def)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.image_def)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.EXACTLY)//设置图片以如何的编码方式显示（控件大小）
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return options;
    }   public static  DisplayImageOptions getFragment1Options(){
        DisplayImageOptions  options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.image_def_frgment1) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.image_def_frgment1)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.image_def_frgment1)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.EXACTLY)//设置图片以如何的编码方式显示（控件大小）
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return options;
    }

    public static  DisplayImageOptions getCircleOptions(Context context){
        Bitmap bitmap = BitmapUtils.toRoundBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.image_def));
        Drawable d=new BitmapDrawable(bitmap);
        DisplayImageOptions  options = new DisplayImageOptions.Builder()
                .showImageOnLoading(d) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(d)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(d)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.EXACTLY)//设置图片以如何的编码方式显示（控件大小）
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return options;
    } public static  DisplayImageOptions getUserHead(Context context){

        DisplayImageOptions  options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.userhead) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.userhead)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.userhead)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.EXACTLY)//设置图片以如何的编码方式显示（控件大小）
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return options;
    }
}
