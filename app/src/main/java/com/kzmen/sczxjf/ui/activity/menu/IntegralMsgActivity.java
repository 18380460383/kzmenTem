package com.kzmen.sczxjf.ui.activity.menu;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.ListViewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;

/**
 * 兑换记录
 */
public class IntegralMsgActivity extends ListViewActivity {
    @InjectView(R.id.msg_center_lv)
    PullToRefreshListView mPullRefreshListView;
    @InjectView(R.id.iv_img)
    ImageView ivImg;
    @InjectView(R.id.ll_main)
    LinearLayout llMain;
    private CommonAdapter<String> adapter;
    private List<String> data_list;
    private int page;
    private View empty_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "兑换记录");
        initData();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_integral_msg);
    }

    private void initData() {
        data_list = new ArrayList<>();
        page = 1;
        adapter = new CommonAdapter<String>(this, R.layout.kz_activi_list_item, data_list) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.tv_title, item);
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
        page = 1;
        getList();
    }

    /**
     * 上滑加载更多
     *
     * @param refreshView
     */
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page++;
        getList();
    }

    public void getList() {
       /* for (int i = page; i < 10 + page; i++) {
            data_list.add("测试" + i);
        }
        data_list.clear();*/
        Map<String, String> params = new HashMap<>();
        params.put("limit", "" + 10);
        params.put("page", "" + page);
        OkhttpUtilManager.postNoCacah(this, "Goods/UserOrderList", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                if (mPullRefreshListView == null) {
                    return;
                }
                adapter.notifyDataSetChanged();
                mPullRefreshListView.onRefreshComplete();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                if (mPullRefreshListView == null) {
                    return;
                }
                mPullRefreshListView.setEmptyView(llMain);
                mPullRefreshListView.onRefreshComplete();
                adapter.notifyDataSetChanged();
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
}
