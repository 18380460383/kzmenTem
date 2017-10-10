package com.kzmen.sczxjf.ui.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshStaggeredGridLayout;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.Kz_ShopAdapter;
import com.kzmen.sczxjf.adapter.ShopAdapter;
import com.kzmen.sczxjf.bean.kzbean.JiFenShopListItemBean;
import com.kzmen.sczxjf.bean.kzbean.UserBean;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.ListViewActivity;
import com.kzmen.sczxjf.util.glide.GlideCircleTransform;
import com.kzmen.sczxjf.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;

public class ShopOfIntegralActivity extends ListViewActivity implements View.OnClickListener {

    @InjectView(R.id.shop_list)
    PullToRefreshStaggeredGridLayout shopList;
    ImageView bjNullIv;
    TextView biTitle;
    LinearLayout bjLl;
    @InjectView(R.id.iv_history)
    ImageView ivHistory;
    private LinearLayout ll_jifen;
    private LinearLayout ll_package;
    private LinearLayout ll_area;
    private TextView tv_jifen;
    private TextView tv_package;
    private ImageView c_menu_user_head_iv;
    private List<JiFenShopListItemBean> list;
    private Kz_ShopAdapter adapter;
    private int page = 1;
    private UserBean bean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 当手指触控ViewPager 计时器滑动失效
     */
    private boolean slide = true;
    private View inflate;

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_shop_of_integral);
        list = new ArrayList<>();
        adapter = new Kz_ShopAdapter(this, list);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, getResources().getString(R.string.shopactivity_title_cstr));
        bean = AppContext.getInstance().getUserLogin();

        inflate = LayoutInflater.from(this).inflate(R.layout.listview_null_bj, null);
        bjLl = (LinearLayout) inflate.findViewById(R.id.bj_ll);
        bjNullIv = (ImageView) inflate.findViewById(R.id.bj_null_iv);
        biTitle = (TextView) inflate.findViewById(R.id.bi_title);
        ivHistory.setOnClickListener(this);
        setNullListView(bjLl, bjNullIv, R.drawable.no_g_start, biTitle, "暂无数据", 0);
        setPullToRefreshListView();
        adapter.addHeadView(R.layout.kz_shop_head, new ShopAdapter.HeadBack() {
            @Override
            public void setHeadView(View head) {
                ll_jifen = (LinearLayout) head.findViewById(R.id.ll_jifen);
                ll_package = (LinearLayout) head.findViewById(R.id.ll_package);
                ll_area = (LinearLayout) head.findViewById(R.id.ll_area);
                tv_jifen = (TextView) head.findViewById(R.id.tv_jifen);
                tv_package = (TextView) head.findViewById(R.id.tv_package);
                c_menu_user_head_iv = (ImageView) head.findViewById(R.id.c_menu_user_head_iv);
                tv_jifen.setText(bean.getScore());
                tv_package.setText(bean.getBalance());
                Glide.with(ShopOfIntegralActivity.this).load(bean.getAvatar()).placeholder(R.drawable.icon_user_normal).transform(new GlideCircleTransform(ShopOfIntegralActivity.this)).into(c_menu_user_head_iv);
                ll_jifen.setOnClickListener(ShopOfIntegralActivity.this);
                ll_package.setOnClickListener(ShopOfIntegralActivity.this);
                ll_area.setOnClickListener(ShopOfIntegralActivity.this);
            }
        });
    }

    public void getData() {
        getGoods();
    }


    private void getGoods() {
        Map<String, String> params = new HashMap<>();
        params.put("data[limit]", "" + 10);
        params.put("data[page]", "" + page);

        OkhttpUtilManager.postNoCacah(this, "Goods/index", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                dismissProgressDialog();
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    List<JiFenShopListItemBean> listdata = JsonUtils.getBeanList(object.optJSONArray("data"), JiFenShopListItemBean.class);
                    if (listdata != null && listdata.size() > 0) {
                        page++;
                        list.addAll(listdata);
                    }
                    refre();
                } catch (JSONException e) {
                    e.printStackTrace();
                    refre();
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
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


    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page = 1;
        list.clear();
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
        final Handler h = new Handler();
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
        if (ShopOfIntegralActivity.this.adapter != null && ShopOfIntegralActivity.this.adapter.getItemCount() > itemnum) {
            bjLL.setVisibility(View.GONE);
        } else {
            iv.setImageResource(rid);
            bj_title.setText(str);
            bjLL.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ll_jifen:
                intent = new Intent(ShopOfIntegralActivity.this, MyIntegralActivity.class);
                break;
            case R.id.ll_package:
                intent = new Intent(ShopOfIntegralActivity.this, MyPackageAcitivity.class);
                break;
            case R.id.ll_area:
                intent = new Intent(ShopOfIntegralActivity.this, ShopAddAddressActivity.class);
                break;
            case R.id.iv_history:
                intent = new Intent(ShopOfIntegralActivity.this, ShopHistoryActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
