package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
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
import com.kzmen.sczxjf.bean.kzbean.TestListItemBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.ListViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

public class TestListActivity extends ListViewActivity {
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.lv_test_list)
    PullToRefreshListView lvTestList;
    @InjectView(R.id.ll_main)
    LinearLayout llMain;
    @InjectView(R.id.tv_sort)
    TextView tvSort;
    @InjectView(R.id.iv_img)
    ImageView ivImg;
    @InjectView(R.id.tv_msg)
    TextView tvMsg;
    private int page = 1;
    private CommonAdapter<TestListItemBean> adapter;
    private List<TestListItemBean> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "测评");
        data_list = new ArrayList<>();
        page = 1;
        adapter = new CommonAdapter<TestListItemBean>(this, R.layout.kz_test_list_item, data_list) {
            @Override
            protected void convert(ViewHolder viewHolder, final TestListItemBean item, int position) {
                viewHolder.setText(R.id.tv_title, item.getTitle())
                        .setText(R.id.tv_count, item.getViews() + "人参与测评")
                        .glideImage(R.id.iv_image, item.getImage())
                        .setText(R.id.textView6, item.getCepin_text());
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TestListActivity.this, TestDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("id", item.getId());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        };
        setmPullRefreshListView(lvTestList, adapter);
        setADD();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_test_list);
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
        page = 1;
        getList();
    }

    public void getList() {
        if (page == 1) {
            data_list.clear();
        }
        Map<String, String> params = new HashMap<>();
        params.put("limit", "" + 10);
        params.put("page", "" + page);
        OkhttpUtilManager.postNoCacah(this, "Evaluation/getEvaluationList", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                if (lvTestList == null) {
                    return;
                }
                Log.e("tst", data);
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    List<TestListItemBean> datalist = gson.fromJson(object.getString("data"), new TypeToken<List<TestListItemBean>>() {
                    }.getType());
                    data_list.addAll(datalist);
                    if (data_list.size() == 0) {
                        lvTestList.setEmptyView(llMain);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lvTestList.onRefreshComplete();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                if (lvTestList == null) {
                    return;
                }
                Log.e("tst", msg);
                lvTestList.setEmptyView(llMain);
                lvTestList.onRefreshComplete();
            }
        });
    }

    /**
     * 设置PullToRefreshListView自动加载
     */
    public void setADD() {
        final Handler h = new Handler();
        ViewTreeObserver vto = lvTestList.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                System.out.println("tag" + "开始加载");
                lvTestList.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lvTestList.onRefreshComplete();
                        lvTestList.setRefreshing(true);
                    }
                }, 1000);

            }
        });
    }

    @OnClick(R.id.tv_sort)
    public void onViewClicked() {
        startActivity(new Intent(TestListActivity.this, IntegralListActivity.class));
    }
}