package com.kzmen.sczxjf.control;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import com.kzmen.sczxjf.R;
import com.mob.MobSDK;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;


/**
 * 用于集成了ShareSDK 后分享的控制器
 * Created by 杨操 on 2016/1/7.
 * 2016/6/21开始使用
 */
public class ShareControl {


    private   OnekeyShare oks;
    private Context context;
    private shareonCompleteBack back;
    private  Handler handler=new Handler();
    public ShareControl(Context con){
        context=con;
        MobSDK.init(context);
        oks = new  OnekeyShare();
    }

    public void setBack(shareonCompleteBack back) {
        this.back = back;
    }

    /**
     * @param title 标题
     * @param text 内容
     * @param bitmap 图片
     * @param Imageurl 图片地址
     * @param url url仅在微信（包括好友和朋友圈）中使用
     * @param siteUrl 是分享此内容的网站地址，仅在QQ空间使用
     * @return
     */
    public OnekeyShare getOnekeyShare(String title,final String text, final Bitmap bitmap, final String url, final String bigImageurl,final String Imageurl,String siteUrl){
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
         oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(text);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数

        //oks.setImagePath("/sdcard/6bdc9af49014a29e82de799f31076712.png");//确保SDcard下面存在此张图片
        //oks.setImageUrl(imageurl);
        // url仅在微信（包括好友和朋友圈）中使用
        if(!TextUtils.isEmpty(url)){
            oks.setUrl(url);
        }
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        //oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        if(!TextUtils.isEmpty(siteUrl)){
            oks.setSiteUrl(siteUrl);
        }

        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"转发成功",Toast.LENGTH_SHORT).show();
                        back.onComplete();
                    }
                });

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                final Throwable t = throwable;
                t.printStackTrace();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String expName = t.getClass().getSimpleName();
                        if ("WechatClientNotExistException".equals(expName)
                                || "WechatTimelineNotSupportedException".equals(expName)
                                || "WechatFavoriteNotSupportedException".equals(expName)) {
                            Toast.makeText(context, context.getText(R.string.ssdk_wechat_client_inavailable), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "分享失败", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(context, "分享取消了", Toast.LENGTH_LONG).show();
            }

        });
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if(platform.getName().equals(SinaWeibo.NAME)) {
                    paramsToShare.setText(paramsToShare.getText() + url);
                    paramsToShare.setImageUrl(bigImageurl);
                }else if(platform.getName().equals(QQ.NAME)){
                    paramsToShare.setImageUrl(Imageurl);
                    paramsToShare.setSite("卡掌门");
                }else{
                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                    paramsToShare.setImageData(bitmap);
                }
            }
        });
        return oks;
    }
  public interface  shareonCompleteBack{
       void onComplete();
  }
}
