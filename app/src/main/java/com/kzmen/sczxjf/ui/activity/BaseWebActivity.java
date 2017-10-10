package com.kzmen.sczxjf.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.YaoBean;
import com.kzmen.sczxjf.control.CustomProgressDialog;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.ui.activity.personal.ShopDetailsActivity;
import com.kzmen.sczxjf.util.EToastUtil;
import com.kzmen.sczxjf.util.EshareLoger;
import com.kzmen.sczxjf.utils.FileUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.apache.http.Header;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 说明：
 * note：
 * Created by FuPei
 * on 2016/1/14 at 9:24
 */
public class BaseWebActivity extends Activity {
    private com.tencent.smtt.sdk.WebView mWebView;
    private ImageView iv_back;
    private TextView tv_title;
    private IWXAPI api;
    private Bitmap bitmap;
    private ImageView back_all;
    private CustomProgressDialog dialog;
    private ProgressBar yaoProgressBar;
    public final static String EXTRA_URL = "url";
    public final static String EXTRA_TITLE = "title";
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                dismissDialog();
            }else if(msg.what == 0){
                    showDialog(msg.obj.toString());
            }

        }
    };
    /**
     * 第一个界面的title
     */
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContext.getInstance().setOneActivity(this);
        initView();
        initWeixin();
        setListener();
        loadUrl();
    }

    protected void initView() {
        dialog = new CustomProgressDialog(BaseWebActivity.this);
        setProgressBarVisibility(true);
        setContentView(R.layout.activity_yao);
        AppContext.getInstance().mBaseWebAct = this;
        mWebView = (WebView) findViewById(R.id.activity_yao_wb);
//        mWebView.clearCache(true);
        iv_back = (ImageView) findViewById(R.id.title_back);
        tv_title = (TextView) findViewById(R.id.title_name);
        yaoProgressBar = (ProgressBar) findViewById(R.id.yao_progressbar);
        back_all= (ImageView) findViewById(R.id.back_all);
        string = getIntent().getExtras().getString(EXTRA_TITLE);
        if(!TextUtils.isEmpty(string)){
            tv_title.setText(string);
            back_all.setVisibility(View.GONE);
        }

    }

    protected void initWeixin() {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        }
        api.registerApp(Constants.APP_ID);
    }

    protected void setListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });
        back_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mWebView.addJavascriptInterface(new WebJavaScriptObj(), "Android");
    }

    protected void initWebView() {
        com.tencent.smtt.sdk.WebSettings s = mWebView.getSettings();
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (i < 100) {
                    if (yaoProgressBar != null && yaoProgressBar.getVisibility() == View.GONE) {
                        yaoProgressBar.setVisibility(View.VISIBLE);
                    }
                    yaoProgressBar.setProgress(i);
                } else {
                    if (yaoProgressBar != null) {
                        yaoProgressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                if (TextUtils.isEmpty(string)) {
                    tv_title.setText(s);
                }
            }
        });
        mWebView.setVerticalScrollBarEnabled(true);
        mWebView.setHorizontalScrollbarOverlay(true);
        s.setJavaScriptEnabled(true);
        s.setBuiltInZoomControls(true);
        s.setLayoutAlgorithm(com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSaveFormData(true);
        s.setGeolocationEnabled(true);
        s.setCacheMode(com.tencent.smtt.sdk.WebSettings.LOAD_NO_CACHE);
        s.setDomStorageEnabled(true);
    }
    protected void loadUrl() {
        String openId = AppContext.getInstance().getPEUser().getWeixin();
        if (null != openId) {
            initWebView();
            mWebView.loadUrl(getIntent().getExtras().getString(EXTRA_URL));
            EshareLoger.logI("加载的url = " + getIntent().getExtras().getString(EXTRA_URL));
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                    webView.loadUrl(s);
                    return true;
                }

                @Override
                public void onReceivedError(WebView webView, int i, String s, String s1) {
                    super.onReceivedError(webView, i, s, s1);
                    EshareLoger.logI("加载活动失败");
                    EToastUtil.show(BaseWebActivity.this, "加载活动失败");
                }

                @Override
                public void onPageFinished(WebView webView, String s) {
                    super.onPageFinished(webView, s);
                    EshareLoger.logI("页面加载完成");
                }

            });
        } else {
            EshareLoger.logI("没有openId");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //处理WebView跳转返回
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void onBack() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    public class WebJavaScriptObj {

        @JavascriptInterface
        public void ShakeShare(String data) {
            EshareLoger.logI("点击了来分享:" + data);
            try {
                share(data);
            } catch (JSONException e) {
                EshareLoger.logI("分享发生异常");
            }
        }

        @JavascriptInterface
        public void getGoodsDetail(String id) {
            //EToastUtil.show(BaseWebActivity.this, id);
            Intent intent = new Intent(BaseWebActivity.this, ShopDetailsActivity.class);
            intent.putExtra(ShopDetailsActivity.SHOPID, id);
            startActivity(intent);
        }

        @JavascriptInterface
        public void getCompanyRelevant(String type, String uid) {
            System.out.println((type == null) + "" + (uid == null));
            if (type != null && uid != null) {
                if ("1".equals(type)) {
                    //跳到企业资讯界面
                   /* Intent intent = new Intent(BaseWebActivity.this, EnterpriseInformation.class);
                    intent.putExtra(EnterpriseInformation.EUID, uid);
                    startActivity(intent);*/
                } else if ("2".equals(type)) {
                    //跳到企业商品界面
                  /*  Intent intent = new Intent(BaseWebActivity.this, EnterpriseGoods.class);
                    intent.putExtra(ShopDetailsActivity.SHOPID, uid);
                    startActivity(intent);*/
                }
            }

        }
        @JavascriptInterface
        public void getWeixinQrcode(String imgurl){
            try {
                System.out.println(imgurl);
                Message msg = new Message();
                msg.what=0;
                msg.obj="下载图片中。。";
                handler.sendMessage(msg);
                NetworkDownload.byteGet(BaseWebActivity.this, imgurl.replace("\"", "") + ".jpg", null, new NetworkDownload.NetworkDownloadCallBackbyte() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        handleDismiss();
                        saveImage(BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length));
                    }

                    @Override
                    public void onFailure() {
                        handleDismiss();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(BaseWebActivity.this,"图片下载失败",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @JavascriptInterface
        public void getWeixinFannum(String  jsonstr){
            /*try {
                String replace = jsonstr.replace("\"{", "{");
                String replace1 = replace.replace("}\"", "}");
                String replace2 = replace.replace("\\", "");
                JSONObject json=new JSONObject(replace2);
                Intent data = new Intent();
                System.out.println("回来的信息" + replace2);
                data.putExtra(UserDetailInfoActivity.FANSNUM,json.optString("fannum"));
                data.putExtra(UserDetailInfoActivity.MONEY, json.optString("roles_money"));
                BaseWebActivity.this.setResult(RESULT_OK, data);

            } catch (JSONException e) {
                e.printStackTrace();
            }finally {

            }*/

        }
    }

    protected void share(String data) throws JSONException {
        IWXAPI android = WXAPIFactory.createWXAPI(this, "android");
        //判断是否安装了微信
        if(!android.isWXAppInstalled()) {
            EToastUtil.show(this, "请安装微信客户端!");
            return;
        }
        YaoBean bean = YaoBean.parseJson(data);
        String type = bean.shareType;
        if (Constants.SHARE_TYPE_WEIXIN.equals(type)) {
            shareToWeiXin(bean);
        } else {
            EToastUtil.show(this, "暂无分享途径");
        }
    }

    protected void shareToWeiXin(final YaoBean bean) {
        //showDialog("正在分享");
        NetworkDownload.byteGet(this, bean.imgUrl, null, new NetworkDownload.NetworkDownloadCallBackbyte() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                /*BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length, opts);
                opts.inJustDecodeBounds = false;
                opts.inSampleSize = opts.outWidth / 150;
                bitmap = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length, opts);
                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl = bean.Link;
                WXMediaMessage msg = new WXMediaMessage(webpage);
                msg.title = bean.Title;
                msg.description = bean.desc;
                if (bitmap != null) {
                    msg.setThumbImage(bitmap);
                } else {
                    EshareLoger.logI("图片为空");
                }
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;
                req.scene = 1;
                if (api.sendReq(req)) {
                    Message msghander = new Message();
                    msghander.what = 0;
                    msghander.obj = "等待微信响应。。";
                    handler.sendMessage(msghander);
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(BaseWebActivity.this, "访问微信失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }*/
            }
            @Override
            public void onFailure() {
                EshareLoger.logI("获取图片失败");
            }
        });
    }

    /**
     * 分享成功后调用
     */
    public void onShareSuccess() {
        handleDismiss();
        EshareLoger.logI("分享成功调用了");
        mWebView.loadUrl("javascript:ShareSuccess()");
    }

    /**
     *
     * 取消了分享
     */
    public void onShareCancel() {
        EshareLoger.logI("取消调用了");
        handleDismiss();
        mWebView.loadUrl("javascript:nowShareFun()");


    }

    public void dismissDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }catch (Exception e){

        }finally {
            dialog.dismiss();
        }
    }

    public void showDialog(String text) {
        dialog.setText(text);
        dialog.show();
    }

    public void handleDismiss() {
        Message msg = handler.obtainMessage();
        msg.what = 1;
        handler.sendMessage(msg);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissDialog();
    }

    @Override
    protected void onDestroy() {
        mWebView.removeAllViews();
        mWebView.destroy();
        super.onDestroy();
    }
    private void saveImage(Bitmap bitmap) {
        File file = FileUtils.getFile();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmap != null) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file.getPath());
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.close();
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                sendBroadcast(intent);
                String path = file.getPath();
                Toast.makeText(BaseWebActivity.this, "保存图片完成,路径为:" + path, Toast.LENGTH_LONG).show();
                BaseWebActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("weixin://dl/scan")));
            } catch (Exception e) {
                EshareLoger.logI("保存图片出错");
            }
        } else {
            Toast.makeText(BaseWebActivity.this, "还没有图片呢", Toast.LENGTH_LONG).show();
        }
    }
}
