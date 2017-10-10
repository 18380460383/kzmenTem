package com.kzmen.sczxjf.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.StringUtils;

import butterknife.InjectView;


/**
 * @author wu
 */
public class WebviewActivity extends SuperActivity {

    @InjectView(R.id.m_webview)
    public WebView mWebView;
    @InjectView(R.id.c_home_details_menu)
    public ImageView menu;
    @InjectView(R.id.webview_progressbar)
    public ProgressBar webViewProgressBar;
    private Bundle extras;
    private String title;
    private Dialog b;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        titleNameView.setText("详情");
        if(intent!=null){
            extras = intent.getExtras();
            if(extras !=null){
                title = extras.getString("title");
                if(!TextUtils.isEmpty(title)){
                    titleNameView.setText(title);
                }
            }
        }

        mWebView.setWebViewClient(new CustomWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setVerticalScrollBarEnabled(false);
        WebSettings s = mWebView.getSettings();
        s.setBuiltInZoomControls(true);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSavePassword(true);
        s.setSaveFormData(true);
        s.setJavaScriptEnabled(true);
        s.setGeolocationEnabled(true);
        s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
        s.setDomStorageEnabled(true);
        String url = getIntent().getStringExtra("url");
        System.out.println(url);
        if(!StringUtils.isEmpty(url)) {
            runService(url);
        }
    }

    @Override
    public void onCreateDataForView() {
            setTitle(R.id.web_title);
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.webview);
    }


    private void runService(String url) {
        mWebView.loadUrl(url);
    }


    @Override
        public void onBackPressed() {
        super.onBackPressed();
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
           /* if("商家资讯".equals(titleNameView.getText().toString())){
                WebviewActivity.this.title="商家详情";
                titleNameView.setText(title);
            }*/
        }
    }

    class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            if(progress<100) {
                    webViewProgressBar.setVisibility(View.VISIBLE);
                webViewProgressBar.setProgress(progress);
            }else{
                if(webViewProgressBar!=null){
                    webViewProgressBar.setVisibility(View.GONE);
                }
               
            } super.onProgressChanged(view, progress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }

    class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
            webViewProgressBar.setVisibility(View.VISIBLE);
         /*   if("商家详情".equals(titleNameView.getText().toString())){
                WebviewActivity.this.title="商家资讯";
                titleNameView.setText("商家资讯");
            }*/
            if (url.startsWith("tel:")){
                View inflate = LayoutInflater.from(WebviewActivity.this).inflate(R.layout.dialog_web, null);
                TextView title= (TextView) inflate.findViewById(R.id.dialog_web_title);
                title.setText(url.substring(4));
                TextView t= (TextView) inflate.findViewById(R.id.dialog_web_bh);
                t.setText("拨号");
                t.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(url));
                        startActivity(intent);
                        b.dismiss();
                    }
                });
                b = new Dialog(WebviewActivity.this);
                b.requestWindowFeature(Window.FEATURE_NO_TITLE);
                b.setContentView(inflate);
                b.show();

            }else {
                view.loadUrl(url);
                view.requestFocus();
            }
            return true;
        }
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }
}
