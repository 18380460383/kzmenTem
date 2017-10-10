package com.kzmen.sczxjf.util;

import android.content.Context;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.bean.Token7NiuBean;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe:上传图片的辅助类
 * Created by FuPei on 2016/2/19.
 */
public class ImageLoadHelper {

    public static final String IMAGE_WEB = "http://7xnffx.com1.z0.glb.clouddn.com/";
    public OnImageUpload upload;
    public List<String> paths;
    private Context mContext;
    public int currentCount;
    public List<String> urls;

    public ImageLoadHelper(Context context, List<String> paths, OnImageUpload upload) {
        urls = new ArrayList<>();
        currentCount = 0;
        mContext = context;
        this.paths = paths;
        this.upload = upload;
    }

    /**
     * 是否压缩图片
     * @param doZip
     */
    public void startUpload(boolean doZip) {
        if(doZip) {
            zipImage();
        }
        upload.onProgress(1);
        requestToken(paths.get(0));
    }

    public interface OnImageUpload{
        void onFail();
        void onSuccess(List<String> urls);
        void onProgress(int i);
    }

    /**
     * 请求上传图片的token
     */
    private void requestToken(final String filepath) {
        NetworkDownload.jsonGetForCode1(mContext, Constants.URL_POST_QINIUTOKEN, null, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                Token7NiuBean token = new Token7NiuBean();
                token.msg = jsonObject.optString("data");
                EshareLoger.logI("请求token成功，开始上传图片:" + filepath);
                uploadImage(new File(filepath), token);
            }

            @Override
            public void onFailure() {
                upload.onFail();
            }
        });
    }

    /**
     * 上传指定的图片
     * @param file
     * @param token
     */
    private void uploadImage(File file, Token7NiuBean token) {
        if(token == null) {
            upload.onFail();
            return;
        }
        String key = AppContext.getInstance().getTime() + "User";
        try {
            key = key + AppContext.getInstance().getPEUser().getUid() + "Project";
        } catch (Exception e) {

        }
        UploadManager uploadManager = AppContext.getInstance().getUploadManager();
        uploadManager.put(file, key, token.msg, new UpCompletionHandler() {
            @Override
            public void complete(String name, ResponseInfo responseInfo, JSONObject res) {
                try {
                    if (responseInfo != null && responseInfo.isOK()) {
                        String imageUrl = res.optString("key", "");
                        EshareLoger.logI("获取的图片地址为：" + imageUrl);
                        urls.add(IMAGE_WEB + imageUrl);
                        currentCount++;
                        if (currentCount == paths.size()) {
                            upload.onSuccess(urls);
                        } else {
                            upload.onProgress(currentCount + 1);
                            requestToken(paths.get(currentCount));
                        }
                    } else {
                        EshareLoger.logI("上传图片报错了");
                        upload.onFail();
                    }
                } catch (Exception e) {
                    upload.onFail();
                }
            }
        }, null);
    }

    private void zipImage() {
        ArrayList<String> copy_path = new ArrayList<>();
        for(String path : paths) {
            try {
                copy_path.add(EBitmapUtil.getZipImage(path+"zip", new File(path)).getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        paths.clear();
        paths.addAll(copy_path);
    }

}
