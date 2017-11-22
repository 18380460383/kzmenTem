package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.KzConstanst;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.KzCoursePlayDetailAdapter;
import com.kzmen.sczxjf.bean.kzbean.CourseDetailBean;
import com.kzmen.sczxjf.bean.kzbean.CouseQuestionBean;
import com.kzmen.sczxjf.bean.kzbean.KzCoursePlayBean;
import com.kzmen.sczxjf.bean.kzbean.ReturnOrderBean;
import com.kzmen.sczxjf.consta.PlayState;
import com.kzmen.sczxjf.cusinterface.PlayMessage;
import com.kzmen.sczxjf.cusinterface.PlayPopuInterface;
import com.kzmen.sczxjf.dialog.ShareDialog;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.interfaces.PlayDetailOperate;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.popuwidow.Kz_CourseAskPopu;
import com.kzmen.sczxjf.popuwidow.Kz_PlayListPopu;
import com.kzmen.sczxjf.test.bean.Music;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.EToastUtil;
import com.kzmen.sczxjf.utils.TextUtil;
import com.kzmen.sczxjf.view.MyListView;
import com.vondear.rxtools.view.RxToast;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;


public class CoursePlayDeatilActivity extends SuperActivity implements PlayMessage {

    @InjectView(R.id.tv_sub)
    TextView tv_sub;
    @InjectView(R.id.tv_des)
    TextView tv_des;
    @InjectView(R.id.tv_media_start_time)
    TextView tvMediaStartTime;
    @InjectView(R.id.tv_zans)
    TextView tv_zans;
    @InjectView(R.id.tv_media_end_time)
    TextView tvMediaEndTime;
    @InjectView(R.id.sb_play)
    SeekBar sbPlay;
    @InjectView(R.id.lv_goodask)
    MyListView lvGoodask;
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.iv_share)
    ImageView ivShare;
    @InjectView(R.id.iv_play_list)
    ImageView ivPlayList;
    @InjectView(R.id.iv_play_pre)
    ImageView ivPlayPre;
    @InjectView(R.id.iv_play_play)
    ImageView ivPlayPlay;
    @InjectView(R.id.iv_play_next)
    ImageView ivPlayNext;
    @InjectView(R.id.iv_play_best)
    ImageView ivPlayBest;
    @InjectView(R.id.iv_collect)
    ImageView iv_collect;
    @InjectView(R.id.sv_main)
    PullToRefreshScrollView mPullRefreshScrollView;
    private Kz_PlayListPopu playPop;
    private int bufferPercent = 0;
    private int playPos = 0;
    private String cid = "1";
    private String sid = "1";
    private String zans = "1";
    private String iszan = "1";
    private String collect = "0";
    private int page = 1;
    private List<KzCoursePlayBean> kjList;
    private List<CouseQuestionBean> beanlist;
    private KzCoursePlayDetailAdapter kzCoursePlayDetailAdapter;
    private Bundle bundle;
    private CourseDetailBean.StageListBean stageListBean;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "初级课程");
        xiaojiangList = new ArrayList<>();
        // setShare(R.id.iv_share,knowageIndexItem.getShare_title(),knowageIndexItem.getShare_des(),knowageIndexItem.getShare_image(),knowageIndexItem.getShare_linkurl());
        initView();
        initData();
        checkService();
        //这几个刷新Label的设置
        mPullRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel("lastUpdateLabel");
        mPullRefreshScrollView.getLoadingLayoutProxy().setPullLabel("PULLLABLE");
        mPullRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel("refreshingLabel");
        mPullRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel("releaseLabel");

        //上拉、下拉设定
        mPullRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

        //上拉监听函数
        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //执行刷新函数
                page++;
                getQuestion();
                Log.e("tst", "" + page);
            }
        });
        getQuestion();
    }

    private void getFocus() {
        if (tv_name == null) {
            return;
        }
        tv_name.setFocusable(true);
        tv_name.setFocusableInTouchMode(true);
        tv_name.requestFocus();
    }

    private void getQuestion() {
        Map<String, String> params1 = new HashMap<>();
        params1.put("page", "" + page);
        params1.put("limit", "20");
        params1.put("cid", cid);
        OkhttpUtilManager.postNoCacah(this, "Course/getCourseQuestion", params1, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst", data);
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    List<CouseQuestionBean> datalist = gson.fromJson(object.getString("data"), new TypeToken<List<CouseQuestionBean>>() {
                    }.getType());
                    if (datalist.size() == 0) {
                    } else {
                        beanlist.addAll(datalist);
                    }
                    Log.e("tst", beanlist.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                kzCoursePlayDetailAdapter.notifyDataSetChanged();
                // adapter2.notifyDataSetChanged();
                mPullRefreshScrollView.onRefreshComplete();
                getFocus();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                kzCoursePlayDetailAdapter.notifyDataSetChanged();
                // adapter2.notifyDataSetChanged();
                mPullRefreshScrollView.onRefreshComplete();
                getFocus();
            }
        });
    }

    private void initView() {
        sbPlay.setOnSeekBarChangeListener(new SeekBarChangeEvent());
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

    @Override
    public void connectSuccess() {
    }

    private String isXiaojiang = "0";//0 不是 1是
    private String opType = "";
    private String title = "";

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_small_talk_deatil);
        bundle = getIntent().getExtras();
        mMusicList = new ArrayList<>();
        kjList = new ArrayList<>();
        tempList = new ArrayList<>();
        if (bundle != null) {
            stageListBean = (CourseDetailBean.StageListBean) bundle.getSerializable("stage");
            title = bundle.getString("title");
            opType = bundle.getString("opType");
            isXiaojiang = bundle.getString("isXiaojiang");
            media = bundle.getString("media");
            media_time = bundle.getString("media_time");
            position = bundle.getInt("position");
            cid = bundle.getString("cid");
            sid = bundle.getString("sid");
            zans = bundle.getString("zans");
            iszan = bundle.getString("iszan");
            collect = bundle.getString("collect");
            if (null != isXiaojiang && isXiaojiang.equals("1")) {
                ivPlayNext.setVisibility(View.INVISIBLE);
                ivPlayList.setVisibility(View.INVISIBLE);
                ivPlayPre.setVisibility(View.INVISIBLE);
                Music music = new Music();
                music.setType(Music.Type.ONLINE);
                music.setPath(media);
                music.setFileName(title);
                music.setDuration(Long.valueOf(media_time));
                mMusicList.add(music);
                tempList.addAll(mMusicList);
                AppContext.getPlayService().setMusicList(mMusicList);
                AppContext.getPlayService().setPlayMessage(CoursePlayDeatilActivity.this);
                AppContext.getPlayService().play(0);
            }
            if (stageListBean != null) {
                setKjList();
                getCourseDetail();
            } else {
                getCourseDetail();
            }
            /*if (!TextUtil.isEmpty(opType) && opType.equals("1")) {
                getXiaojiangList();
            }*/
            if (!TextUtil.isEmpty(zans)) {
                tv_zans.setText(zans);
            }
            if (!TextUtil.isEmpty(iszan)) {
                ivPlayBest.setBackgroundResource(iszan.equals("1") ? R.drawable.icon_best_current : R.drawable.btn_player_best);
            }
            if (!TextUtil.isEmpty(collect)) {
                iv_collect.setBackgroundResource(collect.equals("1") ? R.drawable.btn_collect_current : R.drawable.btn_collect);
            }
        }
    }

    private String media;
    private String media_time;

    private void getXiaojiangList() {
        Map<String, String> params = new HashMap<>();
        params.put("cid", "" + cid);
        params.put("sid", "" + sid);
        params.put("page", "1");
        params.put("limit", "100");
        OkhttpUtilManager.postNoCacah(this, "Course/getCourseXiaojiang/", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    List<CourseDetailBean.StageListBean.XiaojiangListBean> datalist = gson.fromJson(object.getString("data"), new TypeToken<List<CourseDetailBean.StageListBean.XiaojiangListBean>>() {
                    }.getType());
                    if (datalist.size() == 0) {
                    } else {
                        xiaojiangList.addAll(datalist);
                        for (CourseDetailBean.StageListBean.XiaojiangListBean bean : datalist) {
                            Music music = new Music();
                            music.setType(Music.Type.ONLINE);
                            music.setPath(bean.getMedia());
                            music.setFileName(bean.getTitle());
                            music.setDuration(Long.valueOf(bean.getMedia_time()));
                            mMusicList.add(music);
                        }
                        AppContext.getPlayService().setMusicList(mMusicList);
                        AppContext.getPlayService().setPlayMessage(CoursePlayDeatilActivity.this);
                        if (mMusicList.size() > 0) {
                            tv_title.setText(mMusicList.get(0).getFileName());
                            tempList.addAll(mMusicList);
                            tv_title.setText(title);
                            AppContext.getPlayService().play(getPlayPostiton(title));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
            }
        });
    }

    private CourseDetailBean courseDetailBean;
    private List<Music> tempList;

    private void setKjList() {
        tvMediaEndTime.setText(modifyTime(stageListBean.getKejian_list().get(0).getKejian().get(0).getMedia_time()));
        for (CourseDetailBean.StageListBean.KejianListBean bean : stageListBean.getKejian_list()) {
            KzCoursePlayBean kzCoursePlayBean = new KzCoursePlayBean(bean.getChapter_id(), bean.getChapter_id(), bean.getChapter_name(), 0, "", "");
            kjList.add(kzCoursePlayBean);
            if (null != bean.getKejian()) {
                for (CourseDetailBean.StageListBean.KejianListBean.KejianBean kejianBean : bean.getKejian()) {
                    KzCoursePlayBean kzCoursePlayBean1 = new KzCoursePlayBean(kejianBean.getId(), bean.getChapter_id(), kejianBean.getTitle(), 1, kejianBean.getMedia(), kejianBean.getMedia_time());
                    kjList.add(kzCoursePlayBean1);
                    Music music = new Music();
                    music.setType(Music.Type.ONLINE);
                    music.setPath(kejianBean.getMedia());
                    music.setFileName(kejianBean.getTitle());
                    music.setDuration(Long.valueOf(kejianBean.getMedia_time()));
                    mMusicList.add(music);
                }
            }
        }
        if (null != stageListBean.getXiaojiang_list() && stageListBean.getXiaojiang_list().size() > 0) {
            for (CourseDetailBean.StageListBean.XiaojiangListBean kejianBean : stageListBean.getXiaojiang_list()) {
                Music music = new Music();
                music.setType(Music.Type.ONLINE);
                music.setPath(kejianBean.getMedia());
                music.setFileName(kejianBean.getTitle());
                music.setDuration(Long.valueOf(kejianBean.getMedia_time()));
                mMusicList.add(music);
            }
        }
        AppContext.getPlayService().setMusicList(mMusicList);
        AppContext.getPlayService().setPlayMessage(this);
        if (mMusicList.size() > 0) {
            tempList.addAll(mMusicList);
            if (!TextUtil.isEmpty(title)) {
                tv_title.setText(title);
                AppContext.getPlayService().play(getPlayPostiton(title));
            } else {
                tv_title.setText(mMusicList.get(0).getFileName());
                AppContext.getPlayService().play(0);
            }
        }
    }

    private void getCourseDetail() {
        Map<String, String> params = new HashMap<>();
        params.put("cid", cid);
        OkhttpUtilManager.postNoCacah(this, "Course/getCourseShow", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                mHandler.sendEmptyMessage(1);//CourseDetailBean
                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    courseDetailBean = gson.fromJson(object.getString("data"), CourseDetailBean.class);
                    if (null == stageListBean && !TextUtil.isEmpty(opType) && opType.equals("2")) {
                        for (CourseDetailBean.StageListBean bean : courseDetailBean.getStage_list()) {
                            if (bean.getSid().equals(sid)) {
                                stageListBean = bean;
                                setKjList();
                                break;
                            }
                        }
                    }
                    if (courseDetailBean != null) {
                        iszan = "" + courseDetailBean.getIszan();
                        collect = "" + courseDetailBean.getIscollect();
                        iv_collect.setBackgroundResource(courseDetailBean.getIscollect() == 1 ? R.drawable.btn_collect_current : R.drawable.btn_collect);
                        ivPlayBest.setBackgroundResource(courseDetailBean.getIszan() == 1 ? R.drawable.icon_best_current : R.drawable.btn_player_best);
                        tv_name.setText(courseDetailBean.getTid_title() + " " + courseDetailBean.getName());
                        tv_sub.setText(courseDetailBean.getQuestions_button());
                        tv_des.setText(courseDetailBean.getQuestions_desc());
                        tv_zans.setText(courseDetailBean.getZans());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                initView();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst", msg);
                mHandler.sendEmptyMessage(0);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (AppContext.getPlayService() != null) {
            AppContext.getPlayService().stop();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initData() {
        beanlist = new ArrayList<>();
        kzCoursePlayDetailAdapter = new KzCoursePlayDetailAdapter(beanlist, this, new PlayDetailOperate() {
            @Override
            public void doPlay(String id, String url) {
                setMusic(url, 0);
                // playStart();
                if (kzCoursePlayDetailAdapter.getMedia_name().equals(url) && kzCoursePlayDetailAdapter.getPlayPosition().equals(id)) {
                    AppContext.getPlayService().playPause();
                } else {
                    AppContext.getPlayService().stop();
                    AppContext.getPlayService().playStart();
                }
                playtype = 2;
            }

            @Override
            public void doInput(View view, String qid) {
                showInputFormBottom(view);
                kz_courseAskPopu.setQid(qid);
            }

            @Override
            public void doZan(String opType, String id, String state) {
                setZans(opType, id, state);
            }

            @Override
            public void doCollect(String opType, String id, String state) {
            }
        });
        lvGoodask.setAdapter(kzCoursePlayDetailAdapter);
        setListViewHeightBasedOnChildren(lvGoodask);
    }

    private String media_name = "";
    private List<Music> mMusicList;

    private void setMusic(String url, long duration) {
        mMusicList.clear();
        Music musicp = new Music();
        musicp.setType(Music.Type.ONLINE);
        musicp.setPath(url);//baseUrl2 + "贫民百万歌星伴奏.mp3"
        musicp.setDuration(duration);
        mMusicList.add(musicp);
        AppContext.getPlayService().setMusicList(mMusicList);
        AppContext.getPlayService().setPlayMessage(this);
    }

    private void setMusilList() {
        mMusicList.clear();
        mMusicList.addAll(tempList);
        AppContext.getPlayService().setMusicList(mMusicList);
        AppContext.getPlayService().setPlayMessage(this);
    }

    private void setMusilList(List<String> urlList) {
        mMusicList.clear();
        for (String str : urlList) {
            Music music = new Music();
            music.setType(Music.Type.ONLINE);
            music.setPath(str);
            mMusicList.add(music);
        }
        AppContext.getPlayService().setMusicList(mMusicList);
        AppContext.getPlayService().setPlayMessage(this);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        //获得adapter
        KzCoursePlayDetailAdapter adapter = (KzCoursePlayDetailAdapter) listView.getAdapter();
        if (adapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            //计算总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //计算分割线高度
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        //给listview设置高度
        listView.setLayoutParams(params);
    }

    WindowManager.LayoutParams params;

    Kz_CourseAskPopu kz_courseAskPopu;

    public void showInputFormBottom(View view) {
        kz_courseAskPopu = new Kz_CourseAskPopu(this, courseDetailBean);
//        设置Popupwindow显示位置（从底部弹出）
        kz_courseAskPopu.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        params = getWindow().getAttributes();
        //当弹出Popupwindow时，背景变半透明
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        kz_courseAskPopu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    private List<CourseDetailBean.StageListBean.XiaojiangListBean> xiaojiangList;

    public void showPopFormBottom(View view) {
        if (TextUtil.isEmpty(opType)) {
            return;
        }
        if (opType.equals("2") && xiaojiangList.size() == 0) {
            xiaojiangList.addAll(stageListBean.getXiaojiang_list());
        }
        if (playPop == null) {
            playPop = new Kz_PlayListPopu(this, kjList, xiaojiangList);
        }
        playPop.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        playPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
        playPop.setPopuInterface(new PlayPopuInterface() {
            @Override
            public void onItemClic(String title, String mediaName, long duration, int type, int position) {
                if (type == 1) {
                    setMusic(mediaName, duration);
                    playStart();
                } else {
                    AppContext.getPlayService().setMusicList(tempList);
                    AppContext.getPlayService().play(getPlayPostiton(title));
                }
                tv_title.setText(title);
                playtype = 3;
            }
        });
        playPop.setPlayPos(playPos);
    }

    private int getPlayPostiton(String name) {
        int positon = 0;
        for (Music music : tempList) {
            if (music.getFileName().equals(name)) {
                break;
            }
            positon++;
        }
        return positon;
    }

    private int getMusicPos(String title, String mediaName) {
        int pos = 0;
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).getFileName().equals(title) && tempList.get(i).getPath().equals(mediaName)) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    @OnClick({R.id.iv_share, R.id.iv_play_list, R.id.iv_play_pre, R.id.iv_play_play, R.id.iv_play_next, R.id.iv_play_best, R.id.iv_collect
            , R.id.tv_sub})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_play_list:
                showPopFormBottom(view);
                break;
            case R.id.iv_play_pre:
                if (playPos < 1) {
                    EToastUtil.show(CoursePlayDeatilActivity.this, "当前是第一首");
                } else {
                    playPos--;
                    AppContext.getPlayService().prev();
                }
                playtype = 1;
                break;
            case R.id.iv_play_play:
                if (mMusicList.size() <= 1) {
                    setMusilList();
                }
                playtype = 1;
                playPause();
                break;
            case R.id.iv_play_next:
                playtype = 1;
                if (playPos >= mMusicList.size() - 1) {
                    EToastUtil.show(CoursePlayDeatilActivity.this, "当前是最后一首");
                    ivPlayNext.setBackgroundResource(R.drawable.btn_player_next_unclick);
                } else {
                    playPos++;
                    // playPostion(playPos);
                    AppContext.getPlayService().next();
                }

                break;
            case R.id.iv_play_best:
                String zan = "";
                if (!TextUtil.isEmpty(iszan)) {
                    zan = iszan.equals("1") ? "0" : "1";
                } /*else {
                    zan = courseDetailBean.getIszan() == 1 ? "0" : "1";
                }*/
                setZans("1", TextUtil.isEmpty(cid) ? courseDetailBean.getCid() : cid, zan);
                break;
            case R.id.iv_share:
                if (null == courseDetailBean) {
                    return;
                }
                shareDialog = new ShareDialog(this, courseDetailBean.getShare_title(), courseDetailBean.getShare_des(), courseDetailBean.getShare_des(), courseDetailBean.getShare_linkurl());
                shareDialog.setCancelButtonOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareDialog.dismiss();
                    }
                });

                break;
            case R.id.iv_collect:
                String collect1 = "";
                if (!TextUtil.isEmpty(collect)) {
                    collect1 = collect.equals("1") ? "0" : "1";
                }/* else {
                    collect1 = courseDetailBean.getIscollect() == 1 ? "0" : "1";
                }*/
                setUserCollect("1", TextUtil.isEmpty(cid) ? courseDetailBean.getCid() : cid, collect1);//   protected void setUserCollect(final String optype, String aid, String state){
                break;
            case R.id.tv_sub:
                showInputFormBottom(view);
                kz_courseAskPopu.setQid("");
                break;
        }
    }

    private ShareDialog shareDialog;

    private void playPause() {
        AppContext.getPlayService().playPause();
    }

    private void playStart() {
        AppContext.getPlayService().playStart();
    }

    private void playPostion(int position) {
        AppContext.getPlayService().setMusicList(tempList);
        AppContext.getPlayService().play(position);
    }

    private int playtype = 1;//1 上部播放  2。问题播放

    @Override
    public void prePercent(int percent) {
        if (playtype == 1 || playtype == 3) {
            bufferPercent = percent;
            sbPlay.setSecondaryProgress(percent);
        }
    }

    @Override
    public void time(String start, String end, int pos) {
        if (playtype == 1 || playtype == 3) {
            tvMediaStartTime.setText(start);
            tvMediaEndTime.setText(end);
            sbPlay.setProgress(pos);
            sbPlay.setSecondaryProgress(bufferPercent);
        }
    }

    @Override
    public void playposition(int position) {
        if (playtype == 1) {
            playPos = position;
            if (playPos == 0) {
                ivPlayPre.setBackgroundResource(R.drawable.btn_player_prev_unclick);
            } else if (playPos >= tempList.size() - 1) {
                ivPlayNext.setBackgroundResource(R.drawable.btn_player_next_unclick);
            } else {
                ivPlayNext.setBackgroundResource(R.drawable.btn_player_next);
                ivPlayPre.setBackgroundResource(R.drawable.btn_player_prev);
            }
            tv_title.setText(tempList.get(position).getFileName());
        }
        if (null != playPop && opType.equals("2")) {
            playPop.setMediaName(tempList.get(position).getPath());
            playPop.setTitle(tempList.get(position).getFileName());
            playPop.updateNo();
        }
    }

    @Override
    public void state(int state) {
        if (isDestroyed()) {
            return;
        }
        switch (state) {
            case PlayState.PLAY_PLAYING:
                ivPlayPlay.setBackgroundResource(R.drawable.btn_player_pause);
                break;
            case PlayState.PLAY_PAUSE:
                ivPlayPlay.setBackgroundResource(R.drawable.btn_player_play);
                break;
        }
        if (playPop != null && opType.equals("2")) {
            playPop.setTitle(tempList.get(playPos).getFileName());
            playPop.setMediaName(tempList.get(playPos).getPath());
        }
        if (playPop != null) {
            playPop.setState(state);
        }
    }

    @Override
    public void onError(String type) {
        super.onError(type);
        RxToast.normal("失败");
    }

    @Override
    public void onOperateSuccess(String opType, String type, String state, String id) {
        super.onOperateSuccess(opType, type, state, id);
        switch (type) {
            case "1"://收藏
                collect = state;
                iv_collect.setBackgroundResource(state.equals(KzConstanst.IS_FASLE) ? R.drawable.btn_collect : R.drawable.btn_collect_current);
                break;
            case "2"://举报

                break;//点赞
            case "3":
                switch (opType) {
                    case "1":
                        iszan = state;
                        ivPlayBest.setBackgroundResource(state.equals("1") ? R.drawable.icon_best_current : R.drawable.btn_player_best);
                        updateZan();
                        break;
                    default:
                        kzCoursePlayDetailAdapter.upZan();
                        break;
                }
                break;
        }
    }

    private void updateZan() {
        String count = tv_zans.getText().toString();
        int coun = Integer.valueOf(count);
        if (iszan.equals("1")) {
            coun++;
        } else {
            coun--;
        }
        tv_zans.setText("" + coun);
    }

    @Override
    public void funOrder(ReturnOrderBean bean) {
        super.funOrder(bean);
        if (bean.getType() == 1) {
            kzCoursePlayDetailAdapter.updateOpen();
        }
    }

    private String modifyTime(String time) {
        int musicTime = Integer.valueOf(time);
        int min = musicTime / 60;
        int sec = musicTime % 60;
        String show = (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
        return show;
    }
}
