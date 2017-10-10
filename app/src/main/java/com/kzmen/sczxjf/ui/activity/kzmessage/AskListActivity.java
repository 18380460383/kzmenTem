package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;
import android.os.Handler;
import android.view.ViewTreeObserver;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.MsgCenterAdapter;
import com.kzmen.sczxjf.bean.MsgBean;
import com.kzmen.sczxjf.ui.activity.basic.ListViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 问答列表
 */
public class AskListActivity extends ListViewActivity {
    @InjectView(R.id.msg_center_lv)
    PullToRefreshListView mPullRefreshListView;
    private MsgCenterAdapter adapter;
    private List<MsgBean> data_list;
    private int page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "问答");
        initData();
    }
    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_ask_list);
    }
    private void initData() {
        data_list = new ArrayList<>();
        page = 1;
        adapter = new MsgCenterAdapter(AskListActivity.this, data_list);
        setmPullRefreshListView(mPullRefreshListView, adapter);
        setADD();
    }
    @Override
    protected boolean isShareActivity() {
        return true;
    }
    /**
     * 下拉刷新
     *
     * @param refreshView
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page = 1;
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
        mPullRefreshListView.onRefreshComplete();
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
