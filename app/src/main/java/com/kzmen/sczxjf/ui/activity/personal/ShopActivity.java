package com.kzmen.sczxjf.ui.activity.personal;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.ShopAdapter;
import com.kzmen.sczxjf.adapter.ShopBannerAdapter;
import com.kzmen.sczxjf.bean.AdBean;
import com.kzmen.sczxjf.bean.ShopBean;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.ui.activity.basic.ListViewActivity;
import com.kzmen.sczxjf.utils.JsonUtils;
import com.kzmen.sczxjf.view.PointListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshStaggeredGridLayout;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 杨操 on 2016/1/22.
 */
public class ShopActivity extends ListViewActivity {
    @InjectView(R.id.shop_list)
    PullToRefreshStaggeredGridLayout shopList;
    ViewPager shopBanner;
    TextView myIntegralNum;
    TextView balance;
    PointListView shopPointListView;
    ImageView bjNullIv;
    TextView biTitle;
    LinearLayout bjLl;
    @InjectView(R.id.activity_shop_record)
    TextView activityShopRecord;
    private List<ShopBean> list;
    private ShopAdapter adapter;
    private View head;
    private List<AdBean> bannerlist;
    private ShopBannerAdapter shopBannerAdapter;
    private int page = 1;


    /**
     * 当手指触控ViewPager 计时器滑动失效
     */
    private boolean slide = true;
    private View inflate;

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_shop);
        list = new ArrayList<>();
        adapter = new ShopAdapter(this,list);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.shop_title, getResources().getString(R.string.shopactivity_title_cstr));
        inflate = LayoutInflater.from(this).inflate(R.layout.listview_null_bj, null);
        bjLl = (LinearLayout) inflate.findViewById(R.id.bj_ll);
        bjNullIv = (ImageView) inflate.findViewById(R.id.bj_null_iv);
        biTitle = (TextView) inflate.findViewById(R.id.bi_title);
        setNullListView(bjLl, bjNullIv, R.drawable.no_g_start, biTitle, "暂无数据", 0);
        setPullToRefreshListView();
        bannerlist = new ArrayList<>();
        adapter.addHeadView(R.layout.head_shop_gridview, new ShopAdapter.HeadBack() {
            @Override
            public void setHeadView(View head) {
                shopBanner = (ViewPager) head.findViewById(R.id.shop_banner);
                myIntegralNum = (TextView) head.findViewById(R.id.my_integral_num);
                balance = (TextView) head.findViewById(R.id.activity_shop_balance);
                shopPointListView = (PointListView) head.findViewById(R.id.shop_pointlistview);
                myIntegralNum.setText(AppContext.getInstance().getPEUser().getScore());
                balance.setText(AppContext.getInstance().getPEUser().getBalance()+"");
                shopPointListView.setViewPager(shopBanner);

                getBanner();
            }
        });
    }

    public void getData() {
        getGoods();
    }

    private void getBanner() {
        RequestParams params = new RequestParams();
        params.add("type", "2");
        NetworkDownload.jsonGetForCode1(null, Constants.URL_GET_AD, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {

                List<AdBean> data = JsonUtils.getBeanList(jsonObject.optJSONArray("data"), AdBean.class);
                if (data != null && data.size() > 0) {
                    bannerlist.clear();
                    bannerlist.addAll(data);
                    shopBannerAdapter = new ShopBannerAdapter(bannerlist, ShopActivity.this);
                    shopBanner.setAdapter(shopBannerAdapter);
                    shopPointListView.setListsize(bannerlist.size());
                    shopBanner.setVisibility(View.VISIBLE);
                    shopPointListView.setVisibility(View.VISIBLE);
                    shopBanner.setCurrentItem(400);
                    shopPointListView.select(400);
                    if (bannerlist != null && bannerlist.size() > 1) {
                        shopPointListView.startpageslide();
                    }
                } else {
                    shopBanner.setVisibility(View.GONE);
                    shopPointListView.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure() {
            }
        });


    }

    private void getGoods() {
        RequestParams params = new RequestParams();
        params.add("page", page + "");
        NetworkDownload.jsonGetForCode1(this, Constants.URL_GET_GOODS, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                dismissProgressDialog();
                List<ShopBean> data = JsonUtils.getBeanList(jsonObject.optJSONArray("data"), ShopBean.class);
                if (data != null && data.size() > 0) {
                    page++;
                    list.addAll(data);
                }
                refre();
            }

            @Override
            public void onFailure() {
                dismissProgressDialog();
                refre();
            }
        });
    }

    private void refre() {
        shopList.onRefreshComplete();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(myIntegralNum!=null){
            myIntegralNum.setText(AppContext.getInstance().getPEUser().getScore());
            balance.setText(AppContext.getInstance().getPEUser().getBalance()+"");
        }

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        shopPointListView.startpageslide();
    }
    @Override
    protected void onPause() {
        super.onPause();
        shopPointListView.stopPagesLide();
    }
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        shopPointListView.stopPagesLide();
        page = 1;
        list.clear();
        getBanner();
        getGoods();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getGoods();
    }

    public void setPullToRefreshListView() {
        shopList.getRefreshableView().setAdapter(adapter);
        shopList.setMode(PullToRefreshBase.Mode.BOTH);
        shopList.getLoadingLayoutProxy().setRefreshingLabel("正在获取数据");
        shopList.getLoadingLayoutProxy().setPullLabel("数据更新");
        shopList.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
        shopList.setOnRefreshListener(this);
        final Handler h=new Handler();
        ViewTreeObserver vto = shopList.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                System.out.println("tag" + "开始加载");
                shopList.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        shopList.onRefreshComplete();
                        shopList.setRefreshing(true);
                    }
                }, 1000);

            }
        });
    }

    private void setNullListView(LinearLayout bjLL, ImageView iv, int rid, TextView bj_title, String str, int itemnum) {
        if (ShopActivity.this.adapter != null && ShopActivity.this.adapter.getItemCount() > itemnum) {
            bjLL.setVisibility(View.GONE);
        } else {
            iv.setImageResource(rid);
            bj_title.setText(str);
            bjLL.setVisibility(View.VISIBLE);
        }
    }
    @OnClick({R.id.activity_shop_record})
    public void OnclickTextView(View view){
        switch (view.getId()){
            case R.id.activity_shop_record:
                startActivity(new Intent(ShopActivity.this, ExchangeActivity.class));
                break;
        }
    }

}
