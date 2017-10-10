package com.kzmen.sczxjf.ui.fragment.kzmessage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.MyAskBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.control.CustomProgressDialog;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.test.bean.Music;
import com.kzmen.sczxjf.ui.activity.kzmessage.MyAskDetaiAcitivity;
import com.kzmen.sczxjf.ui.fragment.basic.SuperFragment;
import com.kzmen.sczxjf.utils.TextUtil;
import com.kzmen.sczxjf.view.ExPandGridView;
import com.vondear.rxtools.RxLogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 我的问答 --问答
 */
public class MyAskFragment extends SuperFragment implements PullToRefreshBase.OnRefreshListener2, Serializable {
    @InjectView(R.id.fragment_listview)
    PullToRefreshListView mlistview;
    @InjectView(R.id.ll_main)
    LinearLayout llMain;
    @InjectView(R.id.tv_all)
    TextView tvAll;
    @InjectView(R.id.tv_noanswer)
    TextView tvNoanswer;
    @InjectView(R.id.tv_hasanswer)
    TextView tvHasanswer;
    private String type = "1";
    private int page = 1;
    private CommonAdapter<MyAskBean.DataBean> adapter;
    private List<MyAskBean.DataBean> data_list;
    private CustomProgressDialog dialog;
    private String mediaName;

    public MyAskFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_ask, container, false);
        ButterKnife.inject(this, view);
        isPrepared = true;
        lazyLoad();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initDate();
        setADD();
    }

    private void initDate() {
        data_list = new ArrayList<>();
        page = 1;
        adapter = new CommonAdapter<MyAskBean.DataBean>(getActivity(), R.layout.my_ask_list_item, data_list) {

            @Override
            protected void convert(final ViewHolder viewHolder, final MyAskBean.DataBean item, int position) {
                viewHolder.setText(R.id.tv_price, TextUtil.isEmpty(item.getMoney()) ? "" : item.getMoney())
                        .setText(R.id.tv_sep, item.getTitle())
                        .setText(R.id.tv_content, item.getContent())
                        .setText(R.id.tv_state, item.getState())
                        .setText(R.id.tv_msg, item.getState_right());
                ((ExPandGridView) viewHolder.getView(R.id.gv_img)).setAdapter(new CommonAdapter<String>(getActivity(), R.layout.list_item_image, item.getImages()) {
                    @Override
                    protected void convert(ViewHolder viewHolder, String item, int position) {
                        viewHolder.glideImageBlur(R.id.iv_img, item);
                    }
                });

                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), MyAskDetaiAcitivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("qid", item.getQid());
                        bundle.putString("uid", item.getUid());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        };
        mlistview.setMode(PullToRefreshListView.Mode.BOTH);
        mlistview.setOnRefreshListener(this);
        mlistview.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    mlistview.setMode(PullToRefreshBase.Mode.MANUAL_REFRESH_ONLY);
                } else {
                    mlistview.setMode(PullToRefreshBase.Mode.BOTH);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        mlistview.getLoadingLayoutProxy().setRefreshingLabel("正在获取数据");
        mlistview.getLoadingLayoutProxy().setPullLabel("数据更新");
        mlistview.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
        mlistview.setAdapter(adapter);

    }

    public void setMusic(String url) {
        List<Music> mMusicList = new ArrayList<>();
        Music music = new Music();
        music.setType(Music.Type.ONLINE);
        music.setPath(url);
        mMusicList.add(music);
        AppContext.getPlayService().setMusicList(mMusicList);
        //AppContext.getPlayService().setPlayMessage(this);
    }

    public void setADD() {
        final Handler h = new Handler();
        ViewTreeObserver vto = mlistview.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mlistview.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mlistview.setRefreshing(true);
                    }
                }, 500);
            }
        });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page = 1;
        data_list.clear();
        getData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page++;
        getData();
    }

    private void getData() {
        Map<String, String> params = new HashMap<>();
        showProgressDialog("加载中");
        params.put("data[type]", type);
        params.put("data[page]", "" + page);
        params.put("data[limit]", "20");
        OkhttpUtilManager.postNoCacah(getActivity(), "User/getInterlocutionQuestion", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                RxLogUtils.e("tst", data);
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    MyAskBean bean = gson.fromJson(data, MyAskBean.class);
                    if (null == bean) {
                        mlistview.setEmptyView(llMain);
                    } else {
                        data_list.addAll(bean.getData());
                    }
                    if(data_list.size()==0){
                        mlistview.setEmptyView(llMain);
                    }
                } catch (JSONException e) {
                    mlistview.setEmptyView(llMain);
                    e.printStackTrace();
                }
                mlistview.onRefreshComplete();
                adapter.notifyDataSetChanged();
                mlistview.onRefreshComplete();
                dismissProgressDialog();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                mlistview.setEmptyView(llMain);
                mlistview.onRefreshComplete();
                dismissProgressDialog();
            }
        });
    }

    @OnClick({R.id.tv_all, R.id.tv_noanswer, R.id.tv_hasanswer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                tvHasanswer.setTextColor(Color.argb(255, 173, 173, 173));
                tvNoanswer.setTextColor(Color.argb(255, 173, 173, 173));
                tvAll.setTextColor(Color.argb(255, 0, 0, 0));
                type = "0";
                page = 1;
                data_list.clear();
                adapter.notifyDataSetChanged();
                getData();
                break;
            case R.id.tv_noanswer:
                tvAll.setTextColor(Color.argb(255, 173, 173, 173));
                tvHasanswer.setTextColor(Color.argb(255, 173, 173, 173));
                tvNoanswer.setTextColor(Color.argb(255, 0, 0, 0));
                type = "2";
                page = 1;
                data_list.clear();
                adapter.notifyDataSetChanged();
                getData();
                break;
            case R.id.tv_hasanswer:
                tvAll.setTextColor(Color.argb(255, 173, 173, 173));
                tvNoanswer.setTextColor(Color.argb(255, 173, 173, 173));
                tvHasanswer.setTextColor(Color.argb(255, 0, 0, 0));
                type = "1";
                page = 1;
                data_list.clear();
                adapter.notifyDataSetChanged();
                getData();
                break;
        }
    }
}
