package com.kzmen.sczxjf.ui.fragment.kzmessage;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.KzCollectionAnswerAdapter;
import com.kzmen.sczxjf.bean.kzbean.CollectionAnswerBean;
import com.kzmen.sczxjf.control.CustomProgressDialog;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.interfaces.PlayDetailOperate;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.fragment.basic.SuperFragment;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class AnswerCollectionFragment extends SuperFragment implements PullToRefreshBase.OnRefreshListener2, Serializable {
    @InjectView(R.id.fragment_listview)
    PullToRefreshListView mlistview;
    @InjectView(R.id.ll_main)
    LinearLayout llMain;
    private String type = "4";
    private int page = 1;
    private KzCollectionAnswerAdapter kzCollectionAnswerAdapter;
    private List<CollectionAnswerBean> data_list;
    private CustomProgressDialog dialog;

    /**
     * 标志位，标志已经初始化完成
     */
    public AnswerCollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kz_fragment_listview, container, false);
        ButterKnife.inject(this, view);
        initView(view);
        isPrepared = true;
        lazyLoad();
        return view;
    }

    private void initView(final View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString("type");
        } else {
        }
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
        kzCollectionAnswerAdapter = new KzCollectionAnswerAdapter(getActivity(), data_list, new PlayDetailOperate() {
            @Override
            public void doPlay(String id, String url) {

            }

            @Override
            public void doInput(View view, String qid) {

            }

            @Override
            public void doZan(String opType, String id, String state) {

            }

            @Override
            public void doCollect(String opType, String id, String state) {

            }
        });
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
        mlistview.setAdapter(kzCollectionAnswerAdapter);

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
        params.put("data[type]", type);
        params.put("data[page]", "" + page);
        params.put("data[limit]", "20");
        OkhttpUtilManager.postNoCacah(getActivity(), "User/getCollectList", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                RxLogUtils.e("tst", data);
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    List<CollectionAnswerBean> datalist = gson.fromJson(object.getString("data"), new TypeToken<List<CollectionAnswerBean>>() {
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
                kzCollectionAnswerAdapter.notifyDataSetChanged();
                mlistview.onRefreshComplete();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxLogUtils.e("tst", msg);
                kzCollectionAnswerAdapter.notifyDataSetChanged();
                mlistview.onRefreshComplete();
            }
        });
    }
}
