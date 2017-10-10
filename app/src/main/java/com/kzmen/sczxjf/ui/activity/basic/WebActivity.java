package com.kzmen.sczxjf.ui.activity.basic;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import com.tencent.smtt.sdk.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kzmen.sczxjf.R;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

/**
 * 创建者：杨操
 * 时间：2016/4/6
 * 功能描述：含有WebView的基本设置、
 */
public abstract class WebActivity extends SuperActivity {
    public  String titlename;
    private ProgressBar webViewProgressBar;
    private Dialog b;
    public final static String EXTRA_URL = "url";
    public final static String EXTRA_TITLE = "title";
    private com.tencent.smtt.sdk.WebView mWebView;
    private String  url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO 获取网页的路径和标题（标题可无）
        Intent intent = getIntent();
        if(intent !=null){
            Bundle extras;
            extras = intent.getExtras();
            if(extras !=null){
                this.titlename=extras.getString(EXTRA_TITLE);
               this.url=extras.getString(EXTRA_URL);
            }
        }
        mWebView= (com.tencent.smtt.sdk.WebView) findViewById(setWebViewId());
        setWeb();
        Loading();
    }
    public abstract int setWebViewId();
    /**
     * 设置进度条
     * @param id {@link ProgressBar}控件ID
     */
    public  void setWebViewProgressBar(int id){
        webViewProgressBar= (ProgressBar) findViewById(id);
    }
    /**
     * 杨操
     * 设置{@link com.tencent.smtt.sdk.WebView}
     */
    public void setWeb(){
        com.tencent.smtt.sdk.WebSettings s = mWebView.getSettings();
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.setWebViewClient(new CustomWebViewClient());
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

    /**
     * 杨操
     * 加载网页，如果网页地址为空字符或空提示“无效的链接”
     */
    public void Loading(){
        if(TextUtils.isEmpty(url.trim())){
            Toast.makeText(this,"无效的链接",Toast.LENGTH_SHORT).show();
        }
        mWebView.loadUrl(url);
    }

    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            //TODO 网页加载过程中显示进度条进度
            if (i < 100) {
                if (webViewProgressBar != null && webViewProgressBar.getVisibility() == View.GONE) {
                    webViewProgressBar.setVisibility(View.VISIBLE);
                }
                webViewProgressBar.setProgress(i);
            } else {
                if (webViewProgressBar != null) {
                    webViewProgressBar.setVisibility(View.GONE);
                }

            }
        }

        @Override
        public void onReceivedTitle(WebView webView, String s) {
            super.onReceivedTitle(webView, s);
            if (TextUtils.isEmpty(titlename)) {
                //TODO 如果没有设置标题，设置动态标题
                if(titleNameView!=null){
                    titleNameView.setText(s);
                }
            }

        }
    }

    class CustomWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            //TODO 当重新加载网页的时候显示进度条
            if (webViewProgressBar != null) {
                webViewProgressBar.setVisibility(View.VISIBLE);
            }
            //TODO 判断链接是否为电话号码，如果是执行拨号功能
            if (s.startsWith("tel:")){
                View inflate = LayoutInflater.from(WebActivity.this).inflate(R.layout.dialog_web, null);
                TextView title= (TextView) inflate.findViewById(R.id.dialog_web_title);
                title.setText(s.substring(4));
                TextView t= (TextView) inflate.findViewById(R.id.dialog_web_bh);
                t.setText("拨号");
                final String url=s;
                t.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(url));
                        startActivity(intent);
                        b.dismiss();
                    }
                });
                b = new Dialog(WebActivity.this);
                b.requestWindowFeature(Window.FEATURE_NO_TITLE);
                b.setContentView(inflate);
                b.show();

            }else {
                //TODO 判断链接不是为电话号码执行网页跳转
                webView.loadUrl(s);
                webView.requestFocus();
            }
            return true;
        }
        }

    @Override
    protected boolean isShareActivity() {
        return true;
    }
}