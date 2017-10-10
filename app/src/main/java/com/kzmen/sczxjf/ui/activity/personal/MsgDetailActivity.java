package com.kzmen.sczxjf.ui.activity.personal;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.AppManager;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;

/**
 * 消息详细界面
 * note:需要上一级传入以下参数：title, content, time, id
 * Created by FuPei
 * on 2015/11/20.
 * at 14:57
 */
public class MsgDetailActivity extends SuperActivity {

    /*定义接受Bundle的Key*/
    public static final String KEY_TITLE = "title";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_TIME = "time";

    /*消息的id*/
    public static final String KEY_MSGID = "id";
    public static final String KEY_URL = "url";
    public static final String KEY_ISURL = "isurl";

    /*定义组件*/
    @InjectView(R.id.msg_detail_tv_content)

     TextView tv_content;
    @InjectView(R.id.msg_detail_tv_time)
     TextView tv_time;
    @InjectView(R.id.msg_detail_tv_title)
     TextView tv_title;
    @InjectView(R.id.msg_detail_ly_content)
     LinearLayout ly_content;
    @InjectView(R.id.msg_detail_ly_web)
     WebView mWebView;
    private String msg_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.msg_detail_ly_title,"消息详情");
        AppContext.getInstance().setOneActivity(this);
        AppManager.getAppManager().addActivity(this);
        initData();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_msg_detail);
    }


    private void initData() {
        Bundle bundle = getIntent().getExtras();
        String isurl = bundle.getString(KEY_ISURL);
        if("1".equals(isurl)) {
            final String url1 = bundle.getString(KEY_URL);
            ly_content.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
            initWebView();
            mWebView.loadUrl(url1);
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    view.requestFocus();
                    return true;
                }
            });
            msg_id = bundle.getString(KEY_MSGID);
            getDetailInfo(msg_id);
        } else {
            ly_content.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
            String title = bundle.getString(KEY_TITLE);
            String content = bundle.getString(KEY_CONTENT);
            String time = bundle.getString(KEY_TIME);
            msg_id = bundle.getString(KEY_MSGID);
            tv_content.setText(content);
            tv_title.setText(title);
            tv_time.setText(time);
            getDetailInfo(msg_id);
        }

    }

    private void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setVerticalScrollBarEnabled(true);
        mWebView.setHorizontalScrollbarOverlay(true);
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
    }


    /**
     * 获取详细的内容信息
     */
    public void getDetailInfo(String id) {

        String uid = AppContext.getInstance().getPEUser().getUid();
        String url = Constants.URL_GET_DETAIL_MSG;
        RequestParams params = new RequestParams();
        params.add("uid",uid);
        params.add("id", id);
        NetworkDownload.jsonGetForCode1(this, url, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                jsonObject = jsonObject.optJSONObject("data");
                tv_content.setText(jsonObject.getString("content"));
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkDownload.stopRequest(this);
    }
}
