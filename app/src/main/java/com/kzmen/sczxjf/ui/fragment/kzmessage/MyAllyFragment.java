package com.kzmen.sczxjf.ui.fragment.kzmessage;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.agent.AllyListBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.AgOkhttpUtilManager;
import com.kzmen.sczxjf.test.bean.Music;
import com.kzmen.sczxjf.ui.fragment.basic.SuperFragment;
import com.kzmen.sczxjf.util.TimeFormateUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 我的问答 --偷听
 */
public class MyAllyFragment extends SuperFragment implements PullToRefreshBase.OnRefreshListener2, Serializable {


    @InjectView(R.id.fragment_listview)
    PullToRefreshListView mlistview;
    @InjectView(R.id.iv_img)
    ImageView ivImg;
    @InjectView(R.id.ll_main)
    LinearLayout llMain;
    private String type = "1";
    private int page = 1;
    private CommonAdapter<AllyListBean> adapter;
    private List<AllyListBean> data_list;
    private String mediaName;

    public MyAllyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ally_list_fragment, container, false);
        ButterKnife.inject(this, view);
        isPrepared = true;
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString("type");
        } else {
        }
        lazyLoad();
        return view;

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
        adapter = new CommonAdapter<AllyListBean>(getActivity(), R.layout.ally_list_item, data_list) {

            @Override
            protected void convert(final ViewHolder viewHolder, final AllyListBean item, int position) {
                viewHolder.setText(R.id.tv_name, item.getMember_name())
                        .setText(R.id.tv_datetime, TimeFormateUtil.stampToDate(item.getRegister_date()));
                viewHolder.getView(R.id.iv_state).setBackgroundResource(R.drawable.icon_money2);
                if (!item.getPay_status()) {
                    viewHolder.getView(R.id.iv_state).setBackgroundResource(R.drawable.icon_money1);
                }
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
        params.put("page", "" + page);
        params.put("limit", "20");
        params.put("member_id", AppContext.getInstance().getUserMessageBean().getUid());
        switch (type) {
            case "2":
                params.put("pay_status", "1");
                break;
            case "3":
                params.put("pay_status", "0");
                break;
        }
        AgOkhttpUtilManager.postNoCacah(getActivity(), "users/member_list", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    List<AllyListBean> datalist = gson.fromJson(object.getString("data"), new TypeToken<List<AllyListBean>>() {
                    }.getType());
                    if (datalist.size() == 0) {
                        mlistview.setEmptyView(llMain);
                    } else {
                        data_list.addAll(datalist);
                    }
                } catch (JSONException e) {
                    mlistview.setEmptyView(llMain);
                    e.printStackTrace();
                }
                mlistview.onRefreshComplete();
                adapter.notifyDataSetChanged();
                mlistview.onRefreshComplete();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                mlistview.setEmptyView(llMain);
                mlistview.onRefreshComplete();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
