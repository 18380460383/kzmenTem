package com.kzmen.sczxjf.ui.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.FriendBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.ListViewActivity;
import com.vondear.rxtools.RxLogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class FriendOfmineAcitivty extends ListViewActivity {
    @InjectView(R.id.msg_center_lv)
    PullToRefreshListView mPullRefreshListView;
    @InjectView(R.id.iv_img)
    ImageView ivImg;
    @InjectView(R.id.ll_main)
    LinearLayout llMain;
    @InjectView(R.id.tv_save)
    TextView tvSave;
    @InjectView(R.id.tv_count)
    TextView tv_count;

    private CommonAdapter<FriendBean> adapter;
    private List<FriendBean> data_list;
    private int page;
    private View empty_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "我的好友");
        data_list = new ArrayList<>();
        initData();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_friend_ofmine_acitivty);
    }

    private void initData() {
        adapter = new CommonAdapter<FriendBean>(this, R.layout.kz_friend_list_item, data_list) {
            @Override
            protected void convert(ViewHolder viewHolder, FriendBean item, int position) {
                viewHolder.setText(R.id.tv_name, item.getUsername())
                        .glideImage(R.id.iv_img, item.getAvatar());
                if (item.getRole().equals("1")) {
                    viewHolder.getView(R.id.iv_sign).setBackgroundResource(R.drawable.icon_vip);
                } else {
                    viewHolder.getView(R.id.iv_sign).setBackgroundResource(R.drawable.icon_vip_default);
                }
            }
        };
        setmPullRefreshListView(mPullRefreshListView, adapter);
        setADD();
    }

    /**
     * 下拉刷新
     *
     * @param refreshView
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        getList();
    }

    /**
     * 上滑加载更多
     *
     * @param refreshView
     */
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getList();
    }

    public void getList() {
        data_list.clear();
        OkhttpUtilManager.postNoCacah(this, "User/getUserFriends", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                RxLogUtils.e("tst", data);
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    List<FriendBean> datalist = gson.fromJson(object.getString("data"), new TypeToken<List<FriendBean>>() {
                    }.getType());
                    if (datalist.size() == 0) {
                        mPullRefreshListView.setEmptyView(llMain);
                    } else {
                        tv_count.setText("共"+datalist.size()+"位好友");
                        data_list.addAll(datalist);
                    }
                } catch (JSONException e) {
                    mPullRefreshListView.setEmptyView(llMain);
                    e.printStackTrace();
                }
                mPullRefreshListView.onRefreshComplete();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxLogUtils.e("tst", msg);
                mPullRefreshListView.setEmptyView(llMain);
                mPullRefreshListView.onRefreshComplete();
            }
        });
    }

    /**
     * 设置PullToRefreshListView自动加载
     */
    public void setADD() {
        final Handler h = new Handler();
        ViewTreeObserver vto = mPullRefreshListView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                System.out.println("tag" + "开始加载");
                mPullRefreshListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshListView.onRefreshComplete();
                        mPullRefreshListView.setRefreshing(true);
                    }
                }, 1000);

            }
        });
    }

    @OnClick(R.id.tv_save)
    public void onClick() {
        startActivity(new Intent(FriendOfmineAcitivty.this, FriendInvateActivity.class));
    }
}
