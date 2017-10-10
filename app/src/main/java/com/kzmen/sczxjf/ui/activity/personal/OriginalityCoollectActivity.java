package com.kzmen.sczxjf.ui.activity.personal;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.Config;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.ui.fragment.personal.BindEmail;
import com.kzmen.sczxjf.utils.JsonUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import butterknife.InjectView;

/**
 * 创建者：Administrator
 * 时间：2016/7/21
 * 功能描述：个人端征集
 */
public class OriginalityCoollectActivity extends SuperActivity {
    @InjectView(R.id.orginality_collect_webview)
    WebView orginalityCollectWebview;
    @InjectView(R.id.orginality_collect_framelayout)
    FrameLayout orginalityCollectFramelayout;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.hanv_meail_ll)
    LinearLayout hanvMeailLl;
    @InjectView(R.id.old_email_title)
    TextView oldEmailTitle;
    @InjectView(R.id.old_email_str)
    TextView oldEmailStr;

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.orginality_collect_title,"环球创意征集令");
        if (TextUtils.isEmpty(AppContext.getInstance().getPEUser().getEmail())) {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            BindEmail fragment = new BindEmail();
            Bundle args = new Bundle();
            args.putInt(BindEmail.TITLETYPE, BindEmail.TYPE_NUM_BIND);
            fragment.setArguments(args);
            fragmentTransaction.replace(R.id.orginality_collect_framelayout, fragment);
            fragmentTransaction.commit();
        } else {
            orginalityCollectFramelayout.setVisibility(View.GONE);
            hanvMeailLl.setVisibility(View.VISIBLE);
            oldEmailTitle.setVisibility(View.VISIBLE);
            oldEmailStr.setVisibility(View.VISIBLE);
            oldEmailStr.setText(AppContext.getInstance().getPEUser().getEmail()+"  (若修改请到个人中心设置)");
        }
        setWeb();
        getUrl(1);
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_originality_collect);
    }
    public void getUrl(final int i) {

        Config appConfig = AppContext.getInstance().getAppConfig();
        if(null!= appConfig){
            orginalityCollectWebview.loadUrl(appConfig.getDevmap().get("creative"));
        }else {
            showProgressDialog(null);
            NetworkDownload.byteGet(this, Constants.SERVER_API_CONFIG, null, new NetworkDownload.NetworkDownloadCallBackbyte() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                    dismissProgressDialog();
                    try {
                        String json = new String(bytes);
                        Config config = JsonUtils.getConfig(new JSONObject(json));
                        orginalityCollectWebview.loadUrl(config.getDevmap().get("creative"));
                        AppContext.getInstance().setAppConfig(config);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure() {
                    dismissProgressDialog();
                }
            });
        }

    }
    public void setWeb(){
        WebSettings s = orginalityCollectWebview.getSettings();
        orginalityCollectWebview.setWebChromeClient(new WebChromeClient());
        orginalityCollectWebview.setWebViewClient(new WebViewClient());
        orginalityCollectWebview.setVerticalScrollBarEnabled(true);
        orginalityCollectWebview.setHorizontalScrollbarOverlay(true);
        s.setJavaScriptEnabled(true);
        s.setBuiltInZoomControls(true);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSaveFormData(true);
        s.setGeolocationEnabled(true);
        s.setCacheMode(WebSettings.LOAD_NO_CACHE);
        s.setDomStorageEnabled(true);

    }

}
