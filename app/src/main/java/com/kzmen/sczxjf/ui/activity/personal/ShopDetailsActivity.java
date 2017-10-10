package com.kzmen.sczxjf.ui.activity.personal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.ShopBannerAdapter;
import com.kzmen.sczxjf.bean.AdBean;
import com.kzmen.sczxjf.bean.returned.ShopDetailsBean;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.AppUtils;
import com.kzmen.sczxjf.utils.JsonUtils;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.view.PointListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;


/**
 * Created by Administrator on 2016/1/26.
 */
public class ShopDetailsActivity extends SuperActivity {
    public static final String SHOPID = "shopid";
    @InjectView(R.id.shop_details_banner)
    ViewPager shopDetailsBanner;
    @InjectView(R.id.shop_details_point)
    PointListView shopDetailsPoint;
    @InjectView(R.id.shop_details_scrollview)
    PullToRefreshScrollView scrollview;
    @InjectView(R.id.shaop_details_name)
    TextView shaopDetailsName;
    @InjectView(R.id.shop_integral)
    TextView shopIntegral;
    @InjectView(R.id.shop_price)
    TextView shopPrice;
    @InjectView(R.id.shop_num)
    TextView shopNum;
    @InjectView(R.id.shop_details_content)
    WebView shopDetailsContent;
    @InjectView(R.id.shop_details_submit)
    Button shopDetailsSubmit;
    @InjectView(R.id.shop_detail_rl)
    RelativeLayout shopDetailsRl;
    private String shopid;
    private ShopBannerAdapter adapter;
    private ArrayList<AdBean> list;
    private boolean slide=true;
    private boolean RUN=false;
    private ShopDetailsBean data;
    private BroadcastReceiver play;

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_shop_details);
        registerBroadcastReceiver();
    }

    private void registerBroadcastReceiver() {
        play=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                    finish();
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.SHOP_PLAY_OK);
        registerReceiver(play, filter);

    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.shop_details_title, "商品详情");
        initView();
        getData();
    }


    public void initView() {
        scrollview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (!TextUtils.isEmpty(shopid)) {
                    RUN = false;
                    getShopDetails();
                } else {
                    scrollview.onRefreshComplete();
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }
        });
        shopDetailsPoint.setViewPager(shopDetailsBanner);
        shopDetailsBanner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                slide = false;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        slide = false;
                        System.out.println("不 可滑动");
                        break;
                    case MotionEvent.ACTION_UP:
                        slide = true;
                        System.out.println("可滑动");
                        break;
                }
                return false;
            }
        });
        shopDetailsSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(data.getType())) {
                    showHit();
                } else if ("0".equals(data.getType())) {
                   /* Intent intent = new Intent(ShopDetailsActivity.this, BuyShopSetActivity.class);
                    BuyShopSetActivity.setDetailsData(data);
                    intent.putExtra(SHOPID, shopid);
                    startActivity(intent);*/
                }
            }
        });

    }

    /**
     * 兑换虚拟商品时提示对话框
     */
    private void showHit() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this).setTitle("提示").setMessage("是否确认兑换？").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("来了");
                dialog.dismiss();
                conversionForVirtual();
            }
        });
        dialog.create();
        dialog.show();

    }

    private void conversionForVirtual() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", AppContext.getInstance().getPEUser().getUid());
        map.put("gid", data.getId());
        map.put("num", "1");
        RequestParams params = AppUtils.getParm(map);
        showProgressDialog(null);
        NetworkDownload.jsonPostForCode1(this, Constants.URL_POST_USERADDORDER, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                dismissProgressDialog();
                showCouponDialoge();
            }

            @Override
            public void onFailure() {
                dismissProgressDialog();

            }
        });
    }

    private void showCouponDialoge() {
        System.out.println("显示完成对话框");
       final Dialog dia=new Dialog(this);
        dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dia.setContentView(R.layout.dialog_coupon);
        TextView viewById = (TextView) dia.findViewById(R.id.dialog_coupon_textview);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia.dismiss();
                startActivity(new Intent(ShopDetailsActivity.this, ExchangeActivity.class));
                ShopDetailsActivity.this.finish();
            }
        });
        Window window = dia.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        dia.show();
    }

    private void setWebView() {
        shopDetailsContent.setWebViewClient(new WebViewClient());
        shopDetailsContent.setWebChromeClient(new WebChromeClient());
        shopDetailsContent.setVerticalScrollBarEnabled(false);
        WebSettings s = shopDetailsContent.getSettings();
        s.setBuiltInZoomControls(true);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSavePassword(true);
        s.setSaveFormData(true);
        s.setJavaScriptEnabled(true);
        s.setGeolocationEnabled(true);
        s.setDomStorageEnabled(true);
    }

    public void getData() {
        list = new ArrayList<>();
        getShopId();
        if(!TextUtils.isEmpty(shopid)){
            showProgressDialog(null);
            getShopDetails();
        }

    }

    private void getShopId() {
        Intent intent = getIntent();
        Bundle extras = null;
        if (intent != null) {
            extras = intent.getExtras();
            if (extras != null) {
                shopid = extras.getString(SHOPID);
                System.out.println("商品ID"+shopid);
            }
        }

    }

    public void getShopDetails() {
        RequestParams params = new RequestParams();
        params.add("uid", AppContext.getInstance().getPEUser().getUid());
        params.add("id", shopid);
        NetworkDownload.jsonGetForCode1(this, Constants.URL_GET_GOODSDETLIS, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                scrollview.onRefreshComplete();
                System.out.println("商品详情：" + jsonObject.optJSONObject("data").optString("content"));
                data = JsonUtils.getBean(jsonObject.optJSONObject("data"), ShopDetailsBean.class);
                setData(data);
            }

            @Override
            public void onFailure() {
                dismissProgressDialog();
                scrollview.onRefreshComplete();
            }
        });
    }

    public void setData(final ShopDetailsBean data) {
        shaopDetailsName.setText(data.getTitle());
        shopIntegral.setText(data.getScore() + "");
         String score = AppContext.getInstance().getPEUser().getScore();
        if(score != null &&data.getScore()> Integer.valueOf(score)){
            shopDetailsSubmit.setText("积分不足");
            shopDetailsSubmit.setBackgroundResource(R.color.grey);
            shopDetailsSubmit.setEnabled(false);
        }
        shopPrice.setText("+" + data.getMoney() + "");
        shopNum.setText(data.getStocks() + "件");
        if(null!=data.getContent_url()){
            setWebView();
            shopDetailsContent.loadUrl(data.getContent_url());
        }

        setViewPager(data);
        dismissProgressDialog();
    }

    private void setViewPager(ShopDetailsBean data) {
        list.clear();
        AdBean object;
        if(null==data.getImages()||data.getImages().size()<1){
            shopDetailsRl.setVisibility(View.GONE);
            return;
        }
        shopDetailsRl.setVisibility(View.VISIBLE);
        List<String> images = data.getImages();
        int size = images.size();
        for(int i=0;i<size;i++){
            object= new AdBean();
            object.imageurl= images.get(i);
            list.add(object);
        }
        ShopBannerAdapter sbadapter = new ShopBannerAdapter(list, this);
        adapter=null;
        adapter=sbadapter;
        shopDetailsBanner.setAdapter(adapter);
        shopDetailsPoint.setListsize(size);
            shopDetailsBanner.setCurrentItem(400);
        shopDetailsPoint.select(shopDetailsBanner.getCurrentItem());
        RUN=true;
        if(size>1){
            shopDetailsPoint.startpageslide();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(play);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
         String score = AppContext.getInstance().getPEUser().getScore();
        if(score !=null&&data.getScore()> Integer.valueOf(score)){
            shopDetailsSubmit.setText("积分不足");
            shopDetailsSubmit.setBackgroundResource(R.color.grey);
            shopDetailsSubmit.setEnabled(false);
        }
        shopDetailsPoint.startpageslide();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }
    @Override
    protected void onPause() {
        super.onPause();
        shopDetailsPoint.stopPagesLide();
    }

}
