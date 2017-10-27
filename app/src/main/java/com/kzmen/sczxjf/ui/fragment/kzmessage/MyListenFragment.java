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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.MyToutingBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.test.bean.Music;
import com.kzmen.sczxjf.ui.fragment.basic.SuperFragment;
import com.kzmen.sczxjf.utils.TextUtil;

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
public class MyListenFragment extends SuperFragment implements PullToRefreshBase.OnRefreshListener2, Serializable {


    @InjectView(R.id.fragment_listview)
    PullToRefreshListView mlistview;
    @InjectView(R.id.iv_img)
    ImageView ivImg;
    @InjectView(R.id.ll_main)
    LinearLayout llMain;
    private String type = "1";
    private int page = 1;
    private CommonAdapter<MyToutingBean> adapter;
    private List<MyToutingBean> data_list;
    private String mediaName;

    public MyListenFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_listen, container, false);
        ButterKnife.inject(this, view);
        isPrepared = true;
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
        adapter = new CommonAdapter<MyToutingBean>(getActivity(), R.layout.kz_good_ask_item, data_list) {

            @Override
            protected void convert(final ViewHolder viewHolder, final MyToutingBean item, int position) {
                viewHolder.setText(R.id.tv_user_name, item.getUsername())
                        .setText(R.id.tv_title, item.getContent())
                        .setText(R.id.tv_time, item.getDatetime())
                        .setText(R.id.tv_ask_listen_state2, "点击播放")
                        .glideImageCircle(R.id.iv_user_head, item.getAvatar());
                Glide.with(getActivity()).load(item.getAnswer_avatar()).placeholder(R.drawable.icon_user_normal).into((ImageView) viewHolder.getView(R.id.iv_answer_img));
                viewHolder.getView(R.id.ll_listens).setBackgroundResource(R.drawable.bg_play_blue);
                viewHolder.getView(R.id.iv_zans).setVisibility(View.GONE);
                viewHolder.getView(R.id.tv_zans).setVisibility(View.GONE);
                viewHolder.getView(R.id.imageView4).setVisibility(View.GONE);
                viewHolder.getView(R.id.ll_listens).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setMusic(item.getAnswer_media());
                        AppContext.getPlayService().setIv_anim((ImageView) viewHolder.getView(R.id.iv_anim));
                        if (item.getAid().equals(mediaName)) {
                            AppContext.getPlayService().playPause();
                        } else {
                            AppContext.getPlayService().stop();
                            AppContext.getPlayService().playStart();
                        }
                        mediaName = item.getAid();
                    }
                });
                viewHolder.getView(R.id.ll_txt).setVisibility(View.GONE);
                if (TextUtil.isEmpty(item.getAnswer_media())) {
                    viewHolder.getView(R.id.ll_txt).setVisibility(View.VISIBLE);
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
        showProgressDialog("加载中");
        params.put("data[page]", "" + page);
        params.put("data[limit]", "20");
        OkhttpUtilManager.postNoCacah(getActivity(), "User/getUserEavesdrop", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    List<MyToutingBean> datalist = gson.fromJson(object.getString("data"), new TypeToken<List<MyToutingBean>>() {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
