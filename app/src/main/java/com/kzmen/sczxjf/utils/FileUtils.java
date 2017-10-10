package com.kzmen.sczxjf.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2015/11/3.
 */
public class FileUtils {
    public static final String PATH_IMG = FileUtil.getSDRoot() + "/"+ Constants.ESHARE_ROOT_PATH+"/"+ Constants.E_PATH_IMG+"/" ;
    public static boolean bitmapToFile(File file, Bitmap bitmap) {
        FileOutputStream fos = null;
        boolean compress=false;
        try {
            fos = new FileOutputStream(file);
            //讲bitmap压缩，直接放在了流里面
             compress = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            System.out.println("===>" + e.toString());
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return compress;
        }

    }
    public static  File getFile() {
        File file=null;
        file=new File(getRootFile().getAbsolutePath()+"/"+System.currentTimeMillis() + ".jpg");
        return file;
    }
    public static  File getDownloadFile() {
        File file=null;
        file=new File(getRootFile().getAbsolutePath()+"/"+Constants.C_PATH_DIMG+"/"+System.currentTimeMillis() + ".jpg");
        return file;
    }

    @NonNull
    public static File getRootFile() {
        File files;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
             files = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+ Constants.ESHARE_ROOT_PATH+"/");
            if(!files.exists()){
                files.mkdirs();
            }
        }else{
             files = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/"+ Constants.ESHARE_ROOT_PATH+"/");
            if(!files.exists()){
                files.mkdirs();
            }

        }
        return files;
    }

    /**
     * 获取剪切图片的路径
     * @return 路径
     */
    public static Uri getCropUri() {
        File file = new File(PATH_IMG);
        //创建保存截图的路径
        if(!file.exists()) {
            file.mkdirs();
        }
        Uri uri = Uri.fromFile(new File(PATH_IMG + System.currentTimeMillis()));
        return uri;
    }
    @NonNull
    public static File getImagePath() {
        File files;
            files = new File(getRootFile().getAbsolutePath()+"/"+Constants.E_PATH_IMG+"/");
            if(!files.exists()) {
                files.mkdirs();
            }

        return files;
    }
}
