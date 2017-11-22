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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.MyCourseBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.control.CustomProgressDialog;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.test.bean.Music;
import com.kzmen.sczxjf.ui.activity.kzmessage.CourseDetailAcitivity;
import com.kzmen.sczxjf.ui.activity.menu.CourseAskActivity;
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
import butterknife.OnClick;

/**
 * 我的问答 --课程
 */
public class MyCourseFragment extends SuperFragment implements PullToRefreshBase.OnRefreshListener2, Serializable {
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
    private CommonAdapter<MyCourseBean> adapter;
    private List<MyCourseBean> data_list;
    private CustomProgressDialog dialog;
    private String mediaName;

    public MyCourseFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_question, container, false);
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
        adapter = new CommonAdapter<MyCourseBean>(getActivity(), R.layout.kz_mycourse_listitem, data_list) {

            @Override
            protected void convert(final ViewHolder viewHolder, final MyCourseBean item, int position) {
                Glide.with(getActivity()).load(item.getAnswer_tid_avatar()).placeholder(R.drawable.icon_user_normal).into((ImageView) viewHolder.getView(R.id.iv_userhead));
                //.glideImage(R.id.iv_userhead, item.getAnswer_tid_avatar())
                viewHolder
                        .setText(R.id.tv_title, item.getTitle())
                        .setText(R.id.tv_ques_content, item.getContent())
                        .setText(R.id.tv_name, item.getAnswer_tid_name())
                        .setText(R.id.tv_tid_title, item.getAnswer_tid_title())
                        .setText(R.id.tv_time, item.getAnswer_datetime())
                        .setText(R.id.tv_views, item.getViews() + "人听过");
                viewHolder.getView(R.id.rl_answer).setVisibility(View.VISIBLE);
                viewHolder.getView(R.id.ll_answer).setVisibility(View.VISIBLE);
                if (item.getOk().equals("1")) {
                    viewHolder.getView(R.id.rl_answer).setVisibility(View.GONE);
                } else if (AppContext.getInstance().getUserMessageBean().getTeacher().equals("1")) {
                    viewHolder.getView(R.id.ll_teacher).setVisibility(View.GONE);
                    viewHolder.getView(R.id.tv_state).setVisibility(View.GONE);
                    viewHolder.getView(R.id.ll_answer).setVisibility(View.GONE);
                    viewHolder.getView(R.id.tv_state_answer).setVisibility(View.VISIBLE);
                } else {
                    viewHolder.getView(R.id.ll_answer).setVisibility(View.GONE);
                    viewHolder.getView(R.id.ll_teacher).setVisibility(View.GONE);
                    viewHolder.getView(R.id.tv_state).setVisibility(View.VISIBLE);
                    viewHolder.getView(R.id.tv_state_answer).setVisibility(View.GONE);
                }
                viewHolder.getView(R.id.tv_state_answer).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), CourseAskActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putCharSequence("qid", item.getQid());
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);
                    }
                });
                viewHolder.getView(R.id.ll_course).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), CourseDetailAcitivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putCharSequence("cid", item.getCid());
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);
                    }
                });
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* Intent intent = new Intent(getActivity(), CourseAskActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putCharSequence("qid", item.getQid());
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);*/
                    }
                });
                viewHolder.getView(R.id.ll_listen).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setMusic(item.getAnswer_media());
                        AppContext.getPlayService().setIv_anim((ImageView) viewHolder.getView(R.id.iv_anim));
                        if (item.getQid().equals(mediaName)) {
                            AppContext.getPlayService().playPause();
                        } else {
                            AppContext.getPlayService().stop();
                            AppContext.getPlayService().playStart();
                        }
                        mediaName = item.getCid();

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
        params.put("type", type);
        params.put("page", "" + page);
        params.put("limit", "20");
        OkhttpUtilManager.postNoCacah(getActivity(), "User/getCourseQuestion", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    List<MyCourseBean> datalist = gson.fromJson(object.getString("data"), new TypeToken<List<MyCourseBean>>() {
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
                RxLogUtils.e("tst", data);
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
