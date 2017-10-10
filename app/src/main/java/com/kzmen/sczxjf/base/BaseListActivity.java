package com.kzmen.sczxjf.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.util.EshareLoger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Describe:基础的单列表界面
 * Created by FuPei on 2016/2/25.
 */
public abstract class BaseListActivity<Entity> extends BaseActivity {

    @InjectView(R.id.listview)
    public PullToRefreshListView listView;
    @InjectView(R.id.title_back)
    public ImageView iv_back;
    @InjectView(R.id.title_name)
    public TextView tv_title;
    @InjectView(R.id.iv_title_right)
    public ImageView iv_right;

    private RequestParams param;
    private BaseAdapter mAdapter;
    private List<Entity> mData;
    private int currentPage;
    private String keyPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setListener();
        showProgressDialog("加载中...");
        requestData();
    }

    private void initView() {
        setContentView(R.layout.activity_list);
        tv_title.setText(getActTitle());
        listView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void initData() {
        currentPage = 1;
        keyPage = getPageKey();
        param = getParam();
        param.put(keyPage, currentPage + "");
        mData = new ArrayList<>();
        mAdapter = getActAdatper(mData);
        listView.setAdapter(mAdapter);
    }

    private void setListener() {
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 1;
                param.put(keyPage, currentPage + "");
                requestData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                requestData();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBack();
            }
        });

        listView.setOnItemClickListener(getOnItemClickListener());
    }

    /**
     * 设置没有数据时显示的view
     * @param view
     */
    public void setEmptyView(View view) {
        ((ViewGroup)listView.getParent()).addView(view);
        listView.setEmptyView(view);
    }

    private void requestData() {
        NetworkDownload.jsonGet(this, getUrl(), param, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                EshareLoger.logI("onSuccess:" + jsonObject.toString());
                if (currentPage == 1) {
                    mData.clear();
                }
                mData.addAll(getData(jsonObject));
                mAdapter.notifyDataSetChanged();
                currentPage++;
                param.put(keyPage, currentPage + "");
                dismissProgressDialog();
                listView.onRefreshComplete();
            }

            @Override
            public void onFailure() {
                EshareLoger.logI("onFail");
                dismissProgressDialog();
                listView.onRefreshComplete();
            }
        });
    }

    public List<Entity> getListData() {
        return mData;
    }


    public abstract BaseAdapter getActAdatper(List<Entity> entity);

    public abstract RequestParams getParam();


    public abstract String getUrl();


    public abstract void onClickBack();

    public abstract String getActTitle();


    public abstract String getPageKey();

    public abstract List<Entity> getData(JSONObject json);

    public abstract AdapterView.OnItemClickListener getOnItemClickListener();
}
