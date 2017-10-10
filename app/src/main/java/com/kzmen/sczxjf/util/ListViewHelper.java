package com.kzmen.sczxjf.util;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kzmen.sczxjf.base.BaseActivity;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:
 * Created by FuPei on 2016/3/15.
 */
public class ListViewHelper<Entity> {

    public PullToRefreshListView listView;
    public ListHelper helper;
    private RequestParams param;
    private BaseAdapter mAdapter;
    private List<Entity> mData;
    private int currentPage;
    private String keyPage;
    private BaseActivity baseActivity;

    public ListViewHelper(BaseActivity activity, PullToRefreshListView listView, ListHelper<Entity> helper) {
        this.listView = listView;
        this.baseActivity = activity;
        this.helper = helper;
        setMode(PullToRefreshBase.Mode.BOTH);
        initData();
        setListener();
    }

    public void refresh() {
        baseActivity.showProgressDialog("加载中...");
        currentPage = 1;
        param = helper.getParam();
        param.put(keyPage, currentPage + "");
        requestData();
    }

    public void setMode(PullToRefreshBase.Mode mode) {
        listView.setMode(mode);
    }

    private void initData() {
        currentPage = 1;
        keyPage = helper.getPageKey();
        mData = new ArrayList<>();
        mAdapter = helper.getActAdatper(mData);
        listView.setAdapter(mAdapter);
    }


    private void setListener() {
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                EshareLoger.logI("refresh");
                currentPage = 1;
                requestData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                EshareLoger.logI("load more");

                requestData();
            }
        });
        listView.setOnItemClickListener(helper.getOnItemClickListener());
    }

    /**
     * 设置此listview数据为空时，显示的文字
     * listview的上级必须为设置中心对齐，才能保证文字显示在中心位置
     * @param text
     */
    public void setEmptText(String text) {
        TextView tv = new TextView(baseActivity);
        tv.setText(text);
        ((ViewGroup)listView.getParent()).addView(tv);
        listView.setEmptyView(tv);
    }

    private void requestData() {
        EshareLoger.logI("params = " + param.toString());
        param = helper.getParam();
        param.put(keyPage, currentPage + "");
        NetworkDownload.jsonGet(baseActivity, helper.getUrl(), param, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                EshareLoger.logI("onSuccess:" + jsonObject.toString());
                if (currentPage == 1) {
                    mData.clear();
                }
                mData.addAll(helper.getData(jsonObject));
                mAdapter.notifyDataSetChanged();
                currentPage++;
                param.put(keyPage, currentPage + "");
                baseActivity.dismissProgressDialog();
                listView.onRefreshComplete();
            }

            @Override
            public void onFailure() {
                EshareLoger.logI("onFail");
                baseActivity.dismissProgressDialog();
                listView.onRefreshComplete();
            }
        });
    }

    public List<Entity> getListData() {
        return mData;
    }

    public interface ListHelper<Entity> {
        BaseAdapter getActAdatper(List<Entity> entity);

        RequestParams getParam();

        String getUrl();

        String getPageKey();

        List<Entity> getData(JSONObject json);

        AdapterView.OnItemClickListener getOnItemClickListener();
    }
}
