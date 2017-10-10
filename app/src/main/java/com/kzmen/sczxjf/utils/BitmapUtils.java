package com.kzmen.sczxjf.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;

/**
 * 创建者：Administrator
 * 时间：2016/4/26
 * 功能描述：
 */
public class BitmapUtils {
    /**
     * 转换图片成圆形
     * @param bitmap 传入Bitmap对象
     * @param wh 宽高
     * @return
     */
    public static  Bitmap toRoundBitmap(Bitmap bitmap,int wh) {
        if(bitmap==null){
            bitmap=AppUtils.readBitMap(AppContext.getInstance(), R.drawable.image_def);
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
        if (width <= height) {
            roundPx = width *0.8f;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
                roundPx = height *0.8f;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
        final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        if(wh!=0){
            return Bitmap.createScaledBitmap(output, wh, wh, true);
        }else {
            return output;
        }
    }
    /**
     * 转换图片成圆形
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static  Bitmap toRoundBitmap(Bitmap bitmap) {
        return toRoundBitmap(bitmap,0);
    }
}
