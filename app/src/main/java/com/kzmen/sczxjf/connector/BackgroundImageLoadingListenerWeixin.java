package com.kzmen.sczxjf.connector;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.kzmen.sczxjf.view.XCRoundRectImageView;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 满足默认图片居中，下载完后铺满的ImageLoadingListener
 * 需要传入加载图片的ImageView对象
 * Created by 杨操 on 2016/3/17.
 */
public class BackgroundImageLoadingListenerWeixin implements ImageLoadingListener {
    private XCRoundRectImageView imageView;

    public BackgroundImageLoadingListenerWeixin(XCRoundRectImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void onLoadingStarted(String s, View view) {
        try {
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        } catch (Exception e) {

        }
    }

    @Override
    public void onLoadingFailed(String s, View view, FailReason failReason) {
        try {
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        } catch (Exception e) {

        }
    }

    @Override
    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
        try {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Bitmap bitmap1 = centerSquareScaleBitmap(bitmap, 98);
            imageView.setImageBitmap(bitmap1);

        } catch (Exception e) {

        }
    }

    @Override
    public void onLoadingCancelled(String s, View view) {
        try {
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        } catch (Exception e) {

        }
    }
    /**

     * @param bitmap      原图
     * @param edgeLength  希望得到的正方形部分的边长
     * @return  缩放截取正中部分后的位图。
     */
    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength)
    {
        if(null == bitmap || edgeLength <= 0)
        {
            return  null;
        }

        Bitmap result = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();
        System.out.println("widthOrg"+widthOrg+"heightOrg"+heightOrg);

        if(widthOrg > edgeLength && heightOrg > edgeLength)
        {
            //压缩到一个最小长度是edgeLength的bitmap
            int longerEdge = (int)(edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
            int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
            int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
            Bitmap scaledBitmap;

            try{
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            }
            catch(Exception e){
                return null;
            }

            //从图中截取正中间的正方形部分。
            int xTopLeft = (widthOrg-edgeLength)/2;
            int yTopLeft = (heightOrg-edgeLength)/2;

            try{
                result = Bitmap.createBitmap(bitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
                scaledBitmap.recycle();
            }
            catch(Exception e){
                return null;
            }
        }

        return result;
    }
}
