package com.kzmen.sczxjf.connector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzmen.sczxjf.control.ScreenControl;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 满足默认图片居中，下载完后瀑布流样式铺满的ImageLoadingListener
 * 需要传入加载图片的ImageView对象
 * Created by 杨操 on 2016/3/17.
 */
public class BackgroundImageLoadingListenerForWaterfall implements ImageLoadingListener {
    private ImageView imageView;
    private TextView image_argb;
    public BackgroundImageLoadingListenerForWaterfall(ImageView imageView,TextView image_argb) {
        this.imageView = imageView;
        this.image_argb=image_argb;
    }

    @Override
    public void onLoadingStarted(String s, View view) {
        try {
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            image_argb.setVisibility(View.GONE);
        } catch (Exception e) {

        }
    }

    @Override
    public void onLoadingFailed(String s, View view, FailReason failReason) {
        try {
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            image_argb.setVisibility(View.GONE);
        } catch (Exception e) {

        }
    }

    @Override
    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
        if(bitmap!=null){
            try {
                BitmapFactory.Options opts = new BitmapFactory.Options();
                int height = bitmap.getHeight();
                int width = bitmap.getWidth();
                int pixel = bitmap.getPixel(4, 4);
                int red = Color.red(pixel); // same as (pixel >> 16) &0xff
                int green = Color.green(pixel); // same as (pixel >> 8) &0xff
                int blue = Color.blue(pixel); // same as (pixel & 0xff)
                int alpha = Color.alpha(pixel); // same as (pixel >>> 24)
                ScreenControl screenControl = new ScreenControl();
                int i = screenControl.getscreenWide();
                int j = screenControl.getscreenHigh();
                float desWidth = 0;
                float desHeight = 0;
                desWidth = (i * 0.5f) - 20;
                desHeight = height * desWidth / width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) desWidth, i/2-8, false);
                image_argb.setVisibility(View.VISIBLE);
                image_argb.setBackgroundColor(Color.argb(255,red,green,blue));
                Bitmap bitmap1 = GetRoundedCornerBitmap(scaledBitmap);

                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageBitmap(bitmap1);
            }catch (Exception e){

            }


        }


    }

    @Override
    public void onLoadingCancelled(String s, View view) {
        try {
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            image_argb.setVisibility(View.GONE);
        } catch (Exception e) {

        }
    }
    //生成圆角图片
    public static Bitmap GetRoundedCornerBitmap(Bitmap bitmap) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()));
            final float roundPx = 8;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            final Rect src = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());

            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
        }
    }
}
