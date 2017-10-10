package com.kzmen.sczxjf.ui.activity.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.AppManager;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.MsgCenterAdapter;
import com.kzmen.sczxjf.bean.MsgBean;
import com.kzmen.sczxjf.bean.returned.MsgReturn;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.ui.activity.basic.ListViewActivity;
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
 * Created by Group
 * on 2015/11/18.
 */
public class MsgCenterActivity extends ListViewActivity implements PullToRefreshBase.OnRefreshListener2{
    public static final String MSGNUM = "MsgNum";
    /*listview组件*/
    @InjectView(R.id.msg_center_lv)
     PullToRefreshListView mPullRefreshListView;
    /*列表数据*/
    private List<MsgBean> data_list;
    /*当前page*/
    private int page;
    /*每次请求的消息数量*/
    private final int COUNT_PAGE = 10;
    private MsgCenterAdapter adapter;
    private int msgnum = 0;
    @InjectView(R.id.markread)
     Button markread;


    @Override
    public void onCreateDataForView() {
        setTitle(R.id.msg_center_title, "消息中心");
        AppManager.getAppManager().addActivity(this);
        AppContext.getInstance().setOneActivity(this);
        initData();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_msg_center);
    }

    private void getMsgNum() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null && extras.get(MSGNUM) != null) {
            msgnum = extras.getInt(MSGNUM);
            if (msgnum == 0) {
                markread.setVisibility(View.GONE);
            } else {
                markread.setVisibility(View.VISIBLE);
            }
        }
    }


    /**
     * 初始化数据
     */
    private void initData() {
        getMsgNum();
        data_list = new ArrayList<>();
        page = 1;
        adapter = new MsgCenterAdapter(MsgCenterActivity.this, data_list);
        setmPullRefreshListView(mPullRefreshListView, adapter);
        setADD();
    }

    /**
     * 设置监听
     */
    private void setListener() {

        markread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                params.add("uid", AppContext.getInstance().getPEUser().getUid());
                params.add("type", "1");
                showProgressDialog(null);
                NetworkDownload.jsonGetForCode1(null, Constants.URL_GET_MSGNUM, params, new NetworkDownload.NetworkDownloadCallBackJson() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                        msgnum = 0;
                        int size = data_list.size();
                        for (int i = 0; i < size; i++) {
                            data_list.get(i).isread = "1";
                        }
                        adapter.notifyDataSetChanged();
                        markread.setVisibility(View.GONE);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onFailure() {
                        dismissProgressDialog();
                    }
                });
            }
        });
    }

    /**
     * 获得消息列表数据
     */
    public void getList() {
        RequestParams params = new RequestParams();
        params.add("uid", AppContext.getInstance().getPEUser().getUid());
        params.add("page", page + "");
        params.add("limit", COUNT_PAGE + "");
        NetworkDownload.jsonGet(this, Constants.URL_GET_MESSAGES, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                MsgReturn msgs = MsgReturn.parseJson(jsonObject);
                EshareLoger.logI("getList:\n" + jsonObject.toString());
                //如果当前请求的事第一页，则处理刷新操作
                if (page == 1) {
                    data_list.clear();
                }
                data_list.addAll(msgs.data);
                adapter.notifyDataSetChanged();
                mPullRefreshListView.onRefreshComplete();
                page++;
                onrequestDone();
            }

            @Override
            public void onFailure() {
                onrequestDone();
            }
        });
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
        getList();
    }

   /**
     * 发送更新红点的广播
     */
    public void sendRedBrocast() {
        Intent intent = new Intent();
        int count = getUnreadCount();
        intent.putExtra("count", getUnreadCount());
        intent.setAction(Constants.BROADCAST_RED);
        sendBroadcast(intent);
        EshareLoger.logI("发送了广播哦! count = " + count);
    }

    public int getUnreadCount() {
        int sum = 0;
        for (MsgBean bean : data_list) {
            if (bean.isread.equals("0")) {
                sum++;
            }
        }
        return sum;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        NetworkDownload.stopRequest(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long itemid) {
        super.onItemClick(parent, view, position, itemid);
        {
            MsgBean bean = data_list.get(position - 1);
            String id = bean.id;
            String nid = bean.nid;
            //如果是资讯，直接跳转
            if(nid != null) {
              /*  Intent intent = new Intent(MsgCenterActivity.this, InformationForDetails.class);
                intent.putExtra(InformationForDetails.NID, nid);
                startActivity(intent);*/
            } else {
                //消息列表为普通的数据
                String title = bean.title;
                String content = bean.content;
                String time = bean.datetime;
                String content_url = bean.content_url;
                String isurl = bean.isurl;
                Intent intent = new Intent(MsgCenterActivity.this, MsgDetailActivity.class);
                intent.putExtra(MsgDetailActivity.KEY_CONTENT, content);
                intent.putExtra(MsgDetailActivity.KEY_TIME, time);
                intent.putExtra(MsgDetailActivity.KEY_TITLE, title);
                intent.putExtra(MsgDetailActivity.KEY_MSGID, id);
                intent.putExtra(MsgDetailActivity.KEY_ISURL, isurl);
                intent.putExtra(MsgDetailActivity.KEY_URL, content_url);
                startActivity(intent);
            }
            if (bean.isread.equals("0")) {
                msgnum--;
                if (msgnum < 0) {
                    msgnum = 0;
                }
                if (msgnum == 0) {
                    markread.setVisibility(View.GONE);
                }
            }
            bean.isread = "1";
            adapter.notifyDataSetChanged();
            getDetailInfo(id);
        }
    }

    public void getDetailInfo(String id) {

        String uid = AppContext.getInstance().getPEUser().getUid();
        String url = Constants.URL_GET_DETAIL_MSG;
        RequestParams params = new RequestParams();
        params.add("uid",uid);
        params.add("id", id);
        NetworkDownload.jsonGetForCode1(this, url, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {

            }

            @Override
            public void onFailure() {

            }
        });
    }

   /*
    * 设置界面的标题栏
    * @param id 标题栏id
    * @param name 标题栏名
    */
    public void setTitle(int id,String name){
        if(title==null&&id!=0){
            title = findViewById(id);
            PercentRelativeLayout viewById = (PercentRelativeLayout) title.findViewById(R.id.back);
            viewById.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int unreadCount = getUnreadCount();
                    Intent data = new Intent(MsgCenterActivity.this,MainTabActivity.class);
                    data.putExtra(MSGNUM, unreadCount);
                    setResult(RESULT_OK, data);
                    System.out.println("sdfsadfasdfasfdasdfasfd" + unreadCount);
                    finish();
                }
            });
            titleNameView = (TextView) findViewById(R.id.title_name);
            if(!TextUtils.isEmpty(name)){
                titleNameView.setText(name);
            }
        }
    }
    /**
     * 杨操
     * 重写方法实现双击返回键退出应用设置前提先关闭菜单栏
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            int unreadCount = getUnreadCount();
            Intent data = new Intent(MsgCenterActivity.this,MainTabActivity.class);
            data.putExtra(MSGNUM, unreadCount);
            setResult(RESULT_OK, data);
            System.out.println("sdfsadfasdfasfdasdfasfd" + unreadCount);
        }

        return super.onKeyDown(keyCode, event);
    }

}
