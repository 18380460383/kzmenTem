package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kzmen.sczxjf.KzConstanst;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.XiaoListItemBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.ListViewActivity;
import com.kzmen.sczxjf.util.TimeFormateUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;

public class XiaoJListActivity extends ListViewActivity {
    @InjectView(R.id.msg_center_lv)
    PullToRefreshListView mPullRefreshListView;
    @InjectView(R.id.ll_main)
    LinearLayout llMain;
    private CommonAdapter<XiaoListItemBean> adapter;
    private List<XiaoListItemBean> data_list;
    private int page;
    private String cid = "";
    private String sid = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "小讲列表");
        initData();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_xiao_jlist);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            cid = bundle.getString("cid");
            sid = bundle.getString("sid");
        }
    }

    private void initData() {
        data_list = new ArrayList<>();
        page = 1;
        adapter = new CommonAdapter<XiaoListItemBean>(this, R.layout.xiaojiang_list_item, data_list) {
            @Override
            protected void convert(ViewHolder viewHolder, final XiaoListItemBean item, int position) {
                viewHolder.setText(R.id.tv_title, item.getTitle())
                        .setText(R.id.tv_xiaogjiangtime1, TimeFormateUtil.formateTime(item.getMedia_time()));
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.getCharge_type().equals("1")) {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("data[type]", "1");
                            params.put("data[aid]", item.getId());
                            OkhttpUtilManager.setOrder(mContext, KzConstanst.addEavesdropOrder, params);
                        } else {
                            Intent intent = new Intent(XiaoJListActivity.this, CoursePlayDeatilActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("title", item.getTitle());
                            bundle.putString("opType", "1");
                            bundle.putString("cid", cid);
                            bundle.putString("sid", sid);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                });
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
        page = 1;
        getList();
    }

    public void getList() {
        if (page == 1) {
            data_list.clear();
        }
        Map<String, String> params = new HashMap<>();
        params.put("data[cid]", "" + cid);
        params.put("data[sid]", "" + sid);
        OkhttpUtilManager.postNoCacah(this, "Course/getCourseXiaojiang/", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst", data);
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    List<XiaoListItemBean> datalist = gson.fromJson(object.getString("data"), new TypeToken<List<XiaoListItemBean>>() {
                    }.getType());
                    if (datalist.size() == 0) {
                        mPullRefreshListView.setEmptyView(llMain);
                    } else {
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
