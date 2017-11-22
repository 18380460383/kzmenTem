package com.kzmen.sczxjf.ui.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.ShopDetailBean;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.view.banner.BannerLayout;
import com.kzmen.sczxjf.view.loading.LoadingView;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

public class ShopDetailActivity extends SuperActivity {

    @InjectView(R.id.bl_main_banner)
    BannerLayout blMainBanner;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.tv_leve)
    TextView tvLeve;
    @InjectView(R.id.iv_like)
    ImageView ivLike;
    @InjectView(R.id.tv_buy)
    LinearLayout tvBuy;
    @InjectView(R.id.wv_content)
    WebView wvContent;
    @InjectView(R.id.iv_history)
    ImageView ivHistory;
    @InjectView(R.id.ll_content)
    LinearLayout llContent;
    @InjectView(R.id.iv_image)
    ImageView ivImage;
    @InjectView(R.id.tv_empty_message)
    TextView tvEmptyMessage;
    @InjectView(R.id.tv_error_message)
    TextView tvErrorMessage;
    @InjectView(R.id.btn_error)
    Button btnError;
    @InjectView(R.id.tv_loading_message)
    TextView tvLoadingMessage;
    @InjectView(R.id.loadView)
    LoadingView loadView;
    @InjectView(R.id.ll_loading)
    LinearLayout llLoading;
    private String id = "";
    private ShopDetailBean shopDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "商品详情");
        initView();
    }

    private void initView() {
        setOnloading(R.id.ll_content);
        mLayout.onLoading();
        initData();
        mHandler.sendEmptyMessageDelayed(1, 2 * 1000);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 数据下载完成，转换状态，显示内容视图
            switch (msg.what) {
                case 0:
                    mLayout.onError();
                    break;
                case 1:
                    mLayout.onDone();
                    break;
            }
            mLayout.onDone();
        }
    };

    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("id", "" + id);
        OkhttpUtilManager.postNoCacah(this, "Goods/goodsShow", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst", data);
                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    shopDetailBean = gson.fromJson(object.getString("data"), ShopDetailBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                initViewData();
                mHandler.sendEmptyMessage(1);
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                mHandler.sendEmptyMessage(0);
            }
        });
    }

    private void initViewData() {
        if (shopDetailBean != null) {
            if (shopDetailBean.getImages().size() > 0) {
                blMainBanner.setViewUrls(shopDetailBean.getImages());
            }
            tvName.setText(shopDetailBean.getTitle());
            tvPrice.setText(shopDetailBean.getScore());
            tvLeve.setText(shopDetailBean.getStocks());
            ivLike.setBackgroundResource(shopDetailBean.getIscollect() == 1 ? R.drawable.btn_collect_current : R.drawable.btn_collect);
            wvContent.loadUrl(shopDetailBean.getContent_url());
            wvContent.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            if (shopDetailBean.getIsexchange() == 0) {
                tvBuy.setBackgroundResource(R.color.title);
            } else {

            }
        }
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_shop_detail);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            id = bundle.getString("id");
        }
    }

    @OnClick({R.id.iv_like, R.id.tv_buy, R.id.iv_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_like:
                int state = shopDetailBean.getIscollect();
                setUserCollect("3", shopDetailBean.getId(), "" + (state == 1 ? 0 : 1));
                break;
            case R.id.tv_buy:
                mLayout.onDone();
                Intent intent = new Intent(ShopDetailActivity.this, ShopOrderComfirActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("shop", shopDetailBean);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_history:
                startActivity(new Intent(ShopDetailActivity.this, ShopHistoryActivity.class));
                break;
        }
    }

    @Override
    public void onOperateSuccess(String opType, String type, String state, String id) {
        super.onOperateSuccess(opType, type, state, id);
        ivLike.setBackgroundResource(state.equals("1") ? R.drawable.btn_collect_current : R.drawable.btn_collect);
    }

    @Override
    public void onError(String type) {
        super.onError(type);
        RxToast.normal("收藏失败");
    }
}
