package com.kzmen.sczxjf.ui.fragment.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.KzActivGridAdapter;
import com.kzmen.sczxjf.adapter.KzMainColumnAdapter;
import com.kzmen.sczxjf.adapter.Kz_MainAskAdapter;
import com.kzmen.sczxjf.adapter.Kz_MainCourseAdapter;
import com.kzmen.sczxjf.bean.kzbean.HomeActivityBean;
import com.kzmen.sczxjf.bean.kzbean.HomeAskBean;
import com.kzmen.sczxjf.bean.kzbean.HomeBanerBean;
import com.kzmen.sczxjf.bean.kzbean.HomeCourseBean;
import com.kzmen.sczxjf.bean.kzbean.HomepageMenuBean;
import com.kzmen.sczxjf.cusinterface.PlayMessage;
import com.kzmen.sczxjf.interfaces.MainAskListClick;
import com.kzmen.sczxjf.interfaces.MainCourseListClick;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.smartlayout.widgit.CustomLoadingLayout;
import com.kzmen.sczxjf.test.bean.Music;
import com.kzmen.sczxjf.ui.activity.kzmessage.ActivListActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.CaseListActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.CourseDetailNewAcitivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.CourseListActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.CoursePlayDeatilActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.KnowageAskIndexActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.MainTabActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.TestListActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.WebAcitivity;
import com.kzmen.sczxjf.ui.fragment.basic.SuperFragment;
import com.kzmen.sczxjf.view.ExPandGridView;
import com.kzmen.sczxjf.view.MyListView;
import com.kzmen.sczxjf.view.banner.BannerLayout;
import com.vondear.rxtools.RxLogUtils;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * 卡掌门--掌信端
 */
public class KzMessageFragment extends SuperFragment implements PlayMessage, SwipeRefreshLayout.OnRefreshListener {
    @InjectView(R.id.nsv_main)
    NestedScrollView nsv_main;
    @InjectView(R.id.bl_main_banner)
    BannerLayout blMainBanner;
    @InjectView(R.id.gv_column)
    ExPandGridView gvColumn;
    @InjectView(R.id.lv_course)
    MyListView lvCourse;
    @InjectView(R.id.ll_more_ask)
    LinearLayout llMoreAsk;
    @InjectView(R.id.gv_more_activ)
    ExPandGridView gvMoreActiv;
    @InjectView(R.id.ll_more_activ)
    LinearLayout llMoreActiv;
    @InjectView(R.id.ll_content)
    LinearLayout llContent;
    @InjectView(R.id.lv_ask)
    MyListView lvAsk;
    private SwipeRefreshLayout mSwipeLayout;
    private List<HomeBanerBean> bannerList;
    private List<String> bannerListUrl;
    private GridView gv_column;
    private List<HomepageMenuBean> columnItemBeanList;
    private List<HomeActivityBean> activityList;
    private KzMainColumnAdapter kzMainColumnAdapter;
    private KzActivGridAdapter kzActivGridAdapter;
    private List<Music> mMusicList;
    protected CustomLoadingLayout mLayout; //SmartLoadingLayout对象
    private Kz_MainCourseAdapter kz_mainCourseAdapter;
    private List<HomeCourseBean> listCourse;
    public Kz_MainAskAdapter kz_mainAskAdapter;
    private List<HomeAskBean> listAsk;

    private boolean isCourseClick = false;


    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mSwipeLayout.setRefreshing(false);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // if (view == null) {
        View view = inflater.inflate(R.layout.fragment_kz_message, container, false);
        //}
        ButterKnife.inject(this, view);
        initView(view);
        isPrepared = true;
        //lazyLoad();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        // initData();
    }

    private void initView(View vew) {
        mSwipeLayout = (SwipeRefreshLayout) vew.findViewById(R.id.sp_main);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        blMainBanner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (bannerList.get(position).getLinktype()) {
                    case "0":
                        Intent intent = new Intent(getActivity(), WebAcitivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("title", bannerList.get(position).getTitle());
                        bundle.putString("url", bannerList.get(position).getLinkurl());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case "1":
                        try {
                            Intent intent2 = new Intent();
                            intent2.setClassName(getActivity(), "com.kzmen.sczxjf.ui.activity.kzmessage." + bannerList.get(position).getClassName());
                            startActivity(intent2);
                        } catch (Exception e) {

                        }
                        break;
                }

            }
        });
        mMusicList = new ArrayList<>();
        bannerList = new ArrayList<>();
        bannerListUrl = new ArrayList<>();
        columnItemBeanList = new ArrayList<>();
        activityList = new ArrayList<>();
        listAsk = new ArrayList<>();
        initData();
        initData1();
    }

    private void getFoucus() {
        if (blMainBanner == null) {
            return;
        }
        blMainBanner.setFocusable(true);
        blMainBanner.setFocusableInTouchMode(true);
        blMainBanner.requestFocus();
    }

    private void initData1() {

        OkhttpUtilManager.postNoCacah(getActivity(), "Index/getHomePageList", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                RxLogUtils.e("tst", data);
                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    bannerList = gson.fromJson(object.getString("data"), new TypeToken<List<HomeBanerBean>>() {
                    }.getType());
                    bannerListUrl.clear();
                    for (HomeBanerBean bean : bannerList) {
                        bannerListUrl.add(bean.getImageurl());
                    }
                    if (blMainBanner != null) {
                        blMainBanner.setViewUrls(bannerListUrl);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getFoucus();
                ((MainTabActivity) getActivity()).mHandler.sendEmptyMessage(1);
                mHandler.sendEmptyMessage(0);
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst", msg);
                ((MainTabActivity) getActivity()).mHandler.sendEmptyMessage(0);
            }
        });
        OkhttpUtilManager.postNoCacah(getActivity(), "Index/getHomepageMenu", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {

                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    List<HomepageMenuBean> list = gson.fromJson(object.getString("data"), new TypeToken<List<HomepageMenuBean>>() {
                    }.getType());
                    columnItemBeanList.clear();
                    columnItemBeanList.addAll(list);
                    kzMainColumnAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getFoucus();
                if ((MainTabActivity) getActivity() != null) {
                    ((MainTabActivity) getActivity()).mHandler.sendEmptyMessage(1);
                }
                mHandler.sendEmptyMessage(0);
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst", msg);
            }
        });
        OkhttpUtilManager.postNoCacah(getActivity(), "Index/getHomepageCourse", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst", "课程：：：" + data);
                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    List<HomeCourseBean> list = gson.fromJson(object.getString("data"), new TypeToken<List<HomeCourseBean>>() {
                    }.getType());
                    if (list.size() > 0) {
                        listCourse.clear();
                        listCourse.addAll(list);
                        kz_mainCourseAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getFoucus();
                ((MainTabActivity) getActivity()).mHandler.sendEmptyMessage(1);

            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst", msg);
                ((MainTabActivity) getActivity()).mHandler.sendEmptyMessage(0);
            }

        });
        OkhttpUtilManager.postNoCacah(getActivity(), "Index/getHomeQuestion", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst", data);
                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    List<HomeAskBean> list = gson.fromJson(object.getString("data"), new TypeToken<List<HomeAskBean>>() {
                    }.getType());
                    if (list.size() > 0) {
                        listAsk.clear();
                        listAsk.addAll(list);
                        kz_mainAskAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getFoucus();
                ((MainTabActivity) getActivity()).mHandler.sendEmptyMessage(1);

            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst", msg);
                ((MainTabActivity) getActivity()).mHandler.sendEmptyMessage(0);
            }
        });
        OkhttpUtilManager.postNoCacah(getActivity(), "Index/getHomeActivity", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    List<HomeActivityBean> list = gson.fromJson(object.getString("data"), new TypeToken<List<HomeActivityBean>>() {
                    }.getType());
                    if (list.size() > 0) {
                        activityList.clear();
                        activityList.addAll(list);
                        kzActivGridAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getFoucus();
                ((MainTabActivity) getActivity()).mHandler.sendEmptyMessage(1);
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst", msg);
                ((MainTabActivity) getActivity()).mHandler.sendEmptyMessage(0);
            }
        });


    }

    private List<String> musicList;

    private void initData() {
        kzMainColumnAdapter = new KzMainColumnAdapter(getActivity(), columnItemBeanList);
        gvColumn.setAdapter(kzMainColumnAdapter);
        kzActivGridAdapter = new KzActivGridAdapter(getActivity(), activityList);
        gvMoreActiv.setAdapter(kzActivGridAdapter);
        gvColumn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (columnItemBeanList.get(position).getPlate()) {
                    case "course":
                        intent = new Intent(getActivity(), CourseListActivity.class);
                        break;
                    case "interlocution":
                        intent = new Intent(getActivity(), KnowageAskIndexActivity.class);
                        break;
                    case "evaluation":
                        intent = new Intent(getActivity(), TestListActivity.class);
                        break;
                    case "activity":
                        intent = new Intent(getActivity(), ActivListActivity.class);
                        break;
                    case "case":
                        intent = new Intent(getActivity(), CaseListActivity.class);
                        break;
                }
                if (intent != null) {
                    startActivity(intent);
                }
            }
        });

        //mHandler.sendEmptyMessage(1);
        listCourse = new ArrayList<>();
        musicList = new ArrayList<>();
        kz_mainCourseAdapter = new Kz_MainCourseAdapter(getActivity(), listCourse, new MainCourseListClick() {
            @Override
            public void onPlay(int position) {
                isCourseClick = true;
                musicList.clear();
               /* for (HomeCourseBean.KejianArrBean bean : listCourse.get(position).getKejian_arr()) {
                    musicList.add(bean.getMedia());
                }*/
                setMusilList(listCourse.get(position).getKejian_arr());
                if (position == kz_mainCourseAdapter.getPlayPosition()) {
                    playPause();
                } else {
                    playStart();
                }
            }

            @Override
            public void onClickXiaoJiang(int position, int pos) {
                Intent intent = new Intent(getActivity(), CoursePlayDeatilActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("opType", "2");
                bundle.putString("title", listCourse.get(position).getXiaojiang_arr().get(pos).getTitle());
                bundle.putString("cid", listCourse.get(position).getCid());
                bundle.putString("sid", listCourse.get(position).getSid());
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onClickMore(int position) {
                Intent intent = new Intent(getActivity(), CourseDetailNewAcitivity.class);
                Bundle bundle = new Bundle();
                bundle.putCharSequence("cid", listCourse.get(position).getCid());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        lvCourse.setAdapter(kz_mainCourseAdapter);
        kz_mainAskAdapter = new Kz_MainAskAdapter(getActivity(), listAsk, new MainAskListClick() {
            @Override
            public void onPosClick(int position) {
                RxToast.error("" + position);
                isCourseClick = false;
                setMusic(listAsk.get(position).getAnswer_media(), Integer.valueOf(listAsk.get(position).getAnswer_media_time()));
                if (position == kz_mainAskAdapter.getPlayPosition()) {
                    playPause();
                } else {
                    playStart();
                }
            }
        });
        lvAsk.setAdapter(kz_mainAskAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        if (AppContext.getPlayService() != null) {
            AppContext.getPlayService().stop();
        }
    }

    private int bufferPercent = 0;

    private void setMusic(String url, int duration) {
        mMusicList.clear();
        Music musicp = new Music();
        musicp.setType(Music.Type.ONLINE);
        musicp.setPath(url);//baseUrl2 + "贫民百万歌星伴奏.mp3"
        musicp.setDuration(duration);
        mMusicList.add(musicp);
        AppContext.getPlayService().setMusicList(mMusicList);
        AppContext.getPlayService().setPlayMessage(this);
    }

    private void setMusilList(List<HomeCourseBean.KejianArrBean> urlList) {
        mMusicList.clear();
        for (HomeCourseBean.KejianArrBean bean : urlList) {
            Music music = new Music();
            music.setType(Music.Type.ONLINE);
            music.setPath(bean.getMedia());
            music.setDuration(Long.valueOf(bean.getMedia_time()));
            mMusicList.add(music);
        }
        AppContext.getPlayService().setMusicList(mMusicList);
        AppContext.getPlayService().setPlayMessage(this);
    }

    private void playPause() {
        AppContext.getPlayService().playPause();
    }

    private void playStart() {
        AppContext.getPlayService().playStart();
    }

    private void playPostion(int position) {
        AppContext.getPlayService().play(position);
    }

    @Override
    public void prePercent(int percent) {
        if (kz_mainCourseAdapter != null && isCourseClick) {
            kz_mainCourseAdapter.prePercent(percent);
        }
    }

    @Override
    public void time(String start, String end, int pos) {
        if (kz_mainCourseAdapter != null && isCourseClick) {
            kz_mainCourseAdapter.time(start, end, pos);
        }
    }

    @Override
    public void playposition(int position) {
        kz_mainCourseAdapter.setPlPosition(position);
    }

    @Override
    public void state(int state) {
        if (kz_mainCourseAdapter != null && isCourseClick) {
            kz_mainCourseAdapter.state(state);
            kz_mainAskAdapter.setPlayPosition(-1);
            kz_mainAskAdapter.state(-1);
        } else if (kz_mainAskAdapter != null) {
            kz_mainAskAdapter.state(state);
            kz_mainCourseAdapter.setPlayPosition(-1);
            kz_mainCourseAdapter.state(-1);
        }
    }

    @OnClick({R.id.ll_more_ask, R.id.ll_more_activ})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_more_ask:
                intent = new Intent(getActivity(), KnowageAskIndexActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_more_activ:
                intent = new Intent(getActivity(), ActivListActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRefresh() {

        initData1();
        // mHandler.sendEmptyMessageDelayed(1, 3000);
    }


    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            this.progress = progress * (AppContext.getPlayService().mPlayer.getDuration())
                    / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            AppContext.getPlayService().mPlayer.seekTo(progress);
        }
    }
}