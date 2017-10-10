package com.kzmen.sczxjf.control;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.bean.Advertisement;
import com.kzmen.sczxjf.utils.FileUtils;
import com.kzmen.sczxjf.net.NetworkDownload;

import org.apache.http.Header;

import java.io.File;


/**
 * 广告管理类 广告的获取，存储广告图片
 * Created by 杨操 on 2015/12/31.
 */
public class AdvertisementControl {
    /**
     * 广告管理类单例对象
     */
    private static AdvertisementControl advertisementControl;
    /**
     * 广告类 对象
     */
    private Advertisement advertisement;
    /**
     * 广告图片对象
     */
    private File imagefile;
    /**
     * 广告图片路径
     */
    private String imageFilePath;
    /**
     * 存放广告图片的文件夹路径
     */
    private String adPath;
    private AdvertisementControl(){
        advertisement= AppContext.getInstance().getAdvertisement();
        adPath= FileUtils.getRootFile().getAbsolutePath()+"/ad";
        imageFilePath= adPath+"/adimage.jpg";
        imagefile=new File(imageFilePath);
    }
    public static AdvertisementControl getAdvertisementControl(){
        if(null== advertisementControl){
            advertisementControl =new AdvertisementControl();
        }
        return advertisementControl;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public boolean isHaveAdvertisement(){
        if(TextUtils.isEmpty(advertisement.getImgurl())||TextUtils.isEmpty(advertisement.getLinkurl())){
            return false;
        }else if(isHaveImage()){
            return true;
        }
        return false;
    }
    private boolean isHaveImage(){
       return imagefile.exists();
    }
    public void saveAdvertisement(Advertisement advertisement){
        if(advertisement==null){
            return;
        }
        this.advertisement=advertisement;
        if(!isHaveImage()){
            new File(adPath).mkdirs();
            dowImage(advertisement);
        }else if(TextUtils.isEmpty(AppContext.getInstance().getAdvertisement().getImgurl())){
            dowImage(advertisement);
        }else if(!AppContext.getInstance().getAdvertisement().getImgurl().equals(advertisement.getImgurl())){
            dowImage(advertisement);
        }
        AppContext.getInstance().setAdvertisement(advertisement);
    }

    private void dowImage(Advertisement advertisement) {
        NetworkDownload.byteGet(null, advertisement.getImgurl(), null, new NetworkDownload.NetworkDownloadCallBackbyte() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                boolean b = FileUtils.bitmapToFile(imagefile, bitmap);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    public File getImagefile() {
        return imagefile;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }
}
