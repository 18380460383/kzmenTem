package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.CaseListItemBean;
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

/**
 * 案例列表
 */
public class CaseListActivity extends ListViewActivity {
    @InjectView(R.id.msg_center_lv)
    PullToRefreshListView mPullRefreshListView;
    @InjectView(R.id.ll_main)
    LinearLayout llMain;
    private CommonAdapter<CaseListItemBean> adapter;
    private List<CaseListItemBean> data_list;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "案例");
        initData();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_case_list);
    }

    private void initData() {
        page = 1;
        data_list = new ArrayList<>();
        adapter = new CommonAdapter<CaseListItemBean>(this, R.layout.kz_case_list_item, data_list) {
            @Override
            protected void convert(ViewHolder viewHolder, CaseListItemBean item, int position) {
                viewHolder.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_tiltle2,item.getDescribe())
                .setText(R.id.tv_count,item.getViews()+"人看过")
                .setText(R.id.tv_time,item.getUpdate_time())
                .glideImage(R.id.iv_image,item.getImage());
            }
        };
        setmPullRefreshListView(mPullRefreshListView, adapter);
        mPullRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(CaseListActivity.this, CaseDetailActivity.class);
                intent.putExtra("id",data_list.get(position-1).getId());
                startActivity(intent);
            }
        });
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
        if(page==1){
            data_list.clear();
        }
        Map<String, String> params = new HashMap<>();
        params.put("data[limit]", "" + 10);
        params.put("data[page]", "" + page);
        OkhttpUtilManager.postNoCacah(this, "Newscase/getNewscaseList", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                if (mPullRefreshListView == null) {
                    return;
                }
                Log.e("tst",data);
                JSONObject object= null;
                try {
                    object = new JSONObject(data);
                    Gson gson=new Gson();
                    List<CaseListItemBean>datalist=gson.fromJson(object.getString("data"), new TypeToken<List<CaseListItemBean>>(){}.getType());

                    if(datalist.size()==0){
                        mPullRefreshListView.setEmptyView(llMain);
                    }else{
                        data_list.addAll(datalist);
                    }
                    Log.e("tst",""+data_list.toString());
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
                }, 500);

            }
        });
    }

}
