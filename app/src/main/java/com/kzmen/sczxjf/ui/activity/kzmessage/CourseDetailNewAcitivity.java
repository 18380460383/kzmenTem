package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.KzConstanst;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.KzCoursePlayDetailAdapter;
import com.kzmen.sczxjf.adapter.Kz_Course_FragmentNewAdapter;
import com.kzmen.sczxjf.bean.kzbean.CourseDetailBean;
import com.kzmen.sczxjf.bean.kzbean.CouseQuestionBean;
import com.kzmen.sczxjf.bean.kzbean.KzCoursePlayBean;
import com.kzmen.sczxjf.bean.kzbean.OrderBean;
import com.kzmen.sczxjf.bean.kzbean.ReturnOrderBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.cusinterface.PlayMessage;
import com.kzmen.sczxjf.dialog.ShareDialog;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.interfaces.PlayDetailOperate;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.popuwidow.Kz_CourseAskPopu;
import com.kzmen.sczxjf.test.bean.Music;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.ui.activity.menu.PayTypeAcitivity;
import com.kzmen.sczxjf.utils.TextUtil;
import com.kzmen.sczxjf.view.ExpandViewPager;
import com.kzmen.sczxjf.view.ExpandableTextView;
import com.kzmen.sczxjf.view.MyListView;
import com.kzmen.sczxjf.view.loading.LoadingView;
import com.vondear.rxtools.RxLogUtils;
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


/**
 * 课程详情
 */
public class CourseDetailNewAcitivity extends SuperActivity implements PlayMessage {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.iv_collect)
    ImageView ivCollect;
    @InjectView(R.id.iv_share)
    ImageView ivShare;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.ll_more)
    LinearLayout ll_more;
    @InjectView(R.id.iv_user_bg)
    ImageView ivUserBg;
    @InjectView(R.id.tv_time)
    TextView tvTime;
    @InjectView(R.id.textView4)
    TextView textView4;
    @InjectView(R.id.ll_hudong)
    LinearLayout llHudong;
    @InjectView(R.id.ll_xiaojianghudong)
    LinearLayout llXiaojianghudong;
    @InjectView(R.id.textView5)
    TextView textView5;
    @InjectView(R.id.expandable_text)
    TextView expandableText;
    @InjectView(R.id.expand_collapse)
    ImageButton expandCollapse;
    @InjectView(R.id.ex_message)
    TextView exMessage;
    @InjectView(R.id.expand_text_view)
    ExpandableTextView expandTextView;
    @InjectView(R.id.tv_course_stage)
    TextView tvCourseStage;
    @InjectView(R.id.tv_course_section)
    TextView tvCourseSection;
    @InjectView(R.id.tv_views)
    TextView tvViews;
    @InjectView(R.id.tab_layout)
    TabLayout tabLayout;
    @InjectView(R.id.info_viewpager)
    ExpandViewPager infoViewpager;
    @InjectView(R.id.lv_play_list)
    MyListView lvPlayList;
    @InjectView(R.id.tv_xiaoj)
    TextView tvXiaoj;
    @InjectView(R.id.lv_xiaojiang_list)
    MyListView lvXiaojiangList;
    @InjectView(R.id.ll_main)
    LinearLayout llMain;
    @InjectView(R.id.tv_days)
    TextView tvDays;
    @InjectView(R.id.tv_stage)
    TextView tvStage;
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.ll_primal)
    LinearLayout llPrimal;
    @InjectView(R.id.tv_des)
    TextView tvDes;
    @InjectView(R.id.tv_money)
    TextView tvMoney;
    @InjectView(R.id.ll_high)
    LinearLayout llHigh;
    @InjectView(R.id.tv_buy)
    TextView tvBuy;
    @InjectView(R.id.ll_buy)
    LinearLayout llBuy;
    @InjectView(R.id.ll_buycouse)
    LinearLayout ll_buycouse;
    @InjectView(R.id.tv_ask)
    TextView tvAsk;
    @InjectView(R.id.tv_questions_desc)
    TextView tvQuestionsDesc;
    @InjectView(R.id.lv_question)
    MyListView lv_question;
    @InjectView(R.id.ll_content)
    LinearLayout llContent;
    @InjectView(R.id.iv_image)
    ImageView ivImage;
    @InjectView(R.id.tv_empty_message)
    TextView tvEmptyMessage;
    @InjectView(R.id.tv_error_message)
    TextView tvErrorMessage;
    @InjectView(R.id.btn_error)
    Button btnError;
    @InjectView(R.id.tv_loading_message)
    TextView tvLoadingMessage;
    @InjectView(R.id.loadView)
    LoadingView loadView;
    @InjectView(R.id.ll_loading)
    LinearLayout llLoading;
    @InjectView(R.id.sv_main)
    PullToRefreshScrollView mPullRefreshScrollView;
    private String[] titles;//= new String[]{"阶段一", "阶段二", "阶段三", "阶段四", "阶段五"}
    private ShareDialog shareDialog;
    private Kz_Course_FragmentNewAdapter adapter;
    private String cid = "";
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 数据下载完成，转换状态，显示内容视图
            switch (msg.what) {
                case 0:
                    mLayout.onError();
                    break;
                case 1:
                    mLayout.onDone();
                    break;
                default:
                    mLayout.onEmpty();
                    break;
            }
        }
    };
    private CourseDetailBean courseDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "课程详情");
        couseList = new ArrayList<>();
        xiaojianglist = new ArrayList<>();
        setOnloading(R.id.ll_content);
        mLayout.onLoading();
        initData();
        initAdapter();
    }

    private void initAdapter() {
        courseAdapter = new CommonAdapter<KzCoursePlayBean>(CourseDetailNewAcitivity.this, R.layout.kz_course_detail_list_item, couseList) {
            @Override
            protected void convert(ViewHolder viewHolder, final KzCoursePlayBean item, final int position) {
                if (null != stageListBean && stageListBean.getIsunlock() != 1) {
                    viewHolder.getConvertView().setBackgroundResource(R.color.gloomy);
                }
                viewHolder.getView(R.id.top_line).setVisibility(View.VISIBLE);
                viewHolder.getView(R.id.bottom_line).setVisibility(View.VISIBLE);
                if (TextUtil.isEmpty(item.getMedia_time())) {
                    item.setMedia_time("0");
                }
                int musicTime = Integer.valueOf(item.getMedia_time());
                int min = musicTime / 60;
                int sec = musicTime % 60;
                String show = (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
                if (position == 0) {
                    viewHolder.getView(R.id.top_line).setVisibility(View.INVISIBLE);
                }
                if (position == (beanlist.size() - 1)) {
                    viewHolder.getView(R.id.bottom_line).setVisibility(View.INVISIBLE);
                }
                if (item.getType() == 0) {
                    viewHolder.getView(R.id.iv_play_state).setVisibility(View.GONE);
                    viewHolder.getView(R.id.v_cricle_point).setVisibility(View.VISIBLE);
                    viewHolder.getView(R.id.tv_title).setVisibility(View.VISIBLE);
                    viewHolder.setText(R.id.tv_title, item.getName());
                    viewHolder.getView(R.id.ll_play_state).setVisibility(View.INVISIBLE);
                    viewHolder.getView(R.id.tv_second).setVisibility(View.INVISIBLE);
                } else {
                    viewHolder.getView(R.id.v_cricle_point).setVisibility(View.GONE);
                    viewHolder.getView(R.id.iv_play_state).setVisibility(View.GONE);
                    viewHolder.setText(R.id.tv_second, item.getName())
                            .setText(R.id.tv_time, show);
                    viewHolder.getView(R.id.tv_second).setVisibility(View.VISIBLE);
                    viewHolder.getView(R.id.tv_title).setVisibility(View.INVISIBLE);
                    viewHolder.getView(R.id.ll_play_state).setVisibility(View.VISIBLE);
                }
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != stageListBean && stageListBean.getIsunlock() == 1) {
                            Intent intent = new Intent(CourseDetailNewAcitivity.this, CoursePlayDeatilActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("opType", "2");
                            bundle.putString("title", item.getName());
                            bundle.putString("cid", courseDetailBean.getCid());
                            bundle.putString("zans", courseDetailBean.getZans());
                            bundle.putString("iszan", "" + courseDetailBean.getIszan());
                            bundle.putString("collect", "" + courseDetailBean.getIscollect());
                            bundle.putSerializable("stage", stageListBean);
                            bundle.putInt("position", position);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            RxToast.normal("当前阶段未解锁");
                        }
                    }
                });
            }
        };
        lvPlayList.setAdapter(courseAdapter);
        xjAdapter = new CommonAdapter<CourseDetailBean.StageListBean.XiaojiangListBean>(CourseDetailNewAcitivity.this, R.layout.kz_xiaojiang_list_item, xiaojianglist) {
            @Override
            protected void convert(ViewHolder viewHolder, final CourseDetailBean.StageListBean.XiaojiangListBean item, final int position) {
                int musicTime = Integer.valueOf(item.getMedia_time());
                int min = musicTime / 60;
                int sec = musicTime % 60;
                String show = (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
                viewHolder.setText(R.id.tv_xiaojiang_title1, item.getTitle())
                        .setText(R.id.tv_xiaogjiangtime1, show);
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != stageListBean && stageListBean.getIsunlock() == 1) {
                            if (item.getCharge_type().equals("1")) {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("data[type]", "1");
                                params.put("data[aid]", item.getId());
                                OkhttpUtilManager.setOrder(mContext, "Course/addEavesdropOrder", params);
                            } else {
                                Intent intent = new Intent(CourseDetailNewAcitivity.this, CoursePlayDeatilActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("cid", cid);
                                bundle.putString("title", item.getTitle());
                                bundle.putString("opType", "2");
                                bundle.putSerializable("stage", stageListBean);
                                bundle.putInt("position", position);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        } else {
                            RxToast.normal("当前阶段未解锁");
                        }
                    }
                });
            }
        };
        lvXiaojiangList.setAdapter(xjAdapter);
    }

    private View.OnClickListener mTabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int pos = (int) view.getTag();
            setStage(pos);
        }
    };

    private List<KzCoursePlayBean> couseList;
    private CommonAdapter<KzCoursePlayBean> courseAdapter;
    private List<CourseDetailBean.StageListBean.XiaojiangListBean> xiaojianglist;
    private CommonAdapter<CourseDetailBean.StageListBean.XiaojiangListBean> xjAdapter;
    private CourseDetailBean.StageListBean stageListBean;

    private void setStage(int position) {
        couseList.clear();
        xiaojianglist.clear();
        stageListBean = courseDetailBean.getStage_list().get(position);
        for (CourseDetailBean.StageListBean.KejianListBean bean : stageListBean.getKejian_list()) {
            KzCoursePlayBean kzCoursePlayBean = new KzCoursePlayBean(bean.getChapter_id(), bean.getChapter_id(), bean.getChapter_name(), 0, "", "");
            couseList.add(kzCoursePlayBean);
            if (null != bean.getKejian()) {
                for (CourseDetailBean.StageListBean.KejianListBean.KejianBean kejianBean : bean.getKejian()) {
                    KzCoursePlayBean kzCoursePlayBean1 = new KzCoursePlayBean(kejianBean.getId(), bean.getChapter_id(), kejianBean.getTitle(), 1, kejianBean.getMedia(), kejianBean.getMedia_time());
                    couseList.add(kzCoursePlayBean1);
                }
            }
        }
        xiaojianglist.addAll(stageListBean.getXiaojiang_list());
        if (xiaojianglist.size() > 0) {
            tvXiaoj.setVisibility(View.VISIBLE);
        } else {
            tvXiaoj.setVisibility(View.GONE);
        }
        llBuy.setVisibility(View.VISIBLE);
        llMain.setBackgroundResource(R.color.white);
        if (stageListBean.getIsunlock() == 1) {
            llBuy.setVisibility(View.GONE);
            llMain.setBackgroundResource(R.color.white);
        } else {
            llMain.setBackgroundResource(R.color.gloomy);
            tvDays.setText("" + stageListBean.getUnlock_day());//stageListBean.getUnlock_day()
            tvPrice.setText("" + stageListBean.getUnlock_money());//stageListBean.getUnlock_money()
            tvStage.setText("" + stageListBean.getStage_name());
        }
        courseAdapter.notifyDataSetChanged();
        xjAdapter.notifyDataSetChanged();
    }

    private void initView() {
        if (courseDetailBean != null) {
            if (courseDetailBean.getStage_list() != null) {
                titles = new String[courseDetailBean.getStage_list().size()];
                for (int i = 0; i < courseDetailBean.getStage_list().size(); i++) {
                    titles[i] = courseDetailBean.getStage_list().get(i).getStage_name();
                }
                adapter = new Kz_Course_FragmentNewAdapter(getSupportFragmentManager(), CourseDetailNewAcitivity.this, titles, courseDetailBean);
                adapter.setTitles(titles);
                infoViewpager.setAdapter(adapter);
                tabLayout.setupWithViewPager(infoViewpager);
                tabLayout.setTabMode(TabLayout.MODE_FIXED);
                for (int i = 0; i < titles.length; i++) {
                    TabLayout.Tab tab = tabLayout.getTabAt(i);
                    if (tab != null) {
                        tab.setCustomView(adapter.getTabView(i));
                        if (tab.getCustomView() != null) {
                            View tabView = (View) tab.getCustomView().getParent();
                            tabView.setTag(i);
                            tabView.setOnClickListener(mTabOnClickListener);
                        }
                    }
                }
            }
            if (courseDetailBean.getIscollect() == 1) {
                ivCollect.setBackgroundResource(R.drawable.btn_collect_current);
            }
            Glide.with(this).load(courseDetailBean.getBanner()).placeholder(R.drawable.icon_image_normal)
                    .into(ivUserBg);
            tvTime.setText(courseDetailBean.getCourse_time() + "分钟语音");
            if (courseDetailBean.getIsquestion().equals("0")) {
                llHudong.setVisibility(View.GONE);
            }
            if (courseDetailBean.getIsxiaojiang().equals("0")) {
                llXiaojianghudong.setVisibility(View.GONE);
            }
            tvCourseStage.setText("" + courseDetailBean.getCourse_stage() + "");
            tvCourseSection.setText(courseDetailBean.getCourse_section() + "");
            tvViews.setText(courseDetailBean.getViews() + "人学习");
            tvAsk.setText(courseDetailBean.getQuestions_button());
            tvQuestionsDesc.setText(courseDetailBean.getQuestions_desc());
            expandTextView.setText(courseDetailBean.getDescribe());
        }
    }

    private List<CouseQuestionBean> beanlist;
    private KzCoursePlayDetailAdapter adapter2;

    private void initData() {
        beanlist = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("data[cid]", cid);
        OkhttpUtilManager.postNoCacah(this, "Course/getCourseShow", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst", data);
                mHandler.sendEmptyMessage(1);//CourseDetailBean
                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    courseDetailBean = gson.fromJson(object.getString("data"), CourseDetailBean.class);
                    if (!courseDetailBean.getType().equals("1")) {
                        ll_buycouse.setVisibility(View.VISIBLE);
                    }
                    if (null != courseDetailBean && null != courseDetailBean.getStage_list() && courseDetailBean.getStage_list().size() > 0) {
                        setStage(0);
                    } else {
                        llBuy.setVisibility(View.GONE);
                    }
                    Log.e("tst", courseDetailBean.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                initView();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                mHandler.sendEmptyMessage(0);
            }
        });
        adapter2 = new KzCoursePlayDetailAdapter(beanlist, this, new PlayDetailOperate() {
            @Override
            public void doPlay(String id, String url) {
                Music musicp = new Music();
                musicp.setType(Music.Type.ONLINE);
                musicp.setPath(url);
                AppContext.getPlayService().setMusic(musicp);
                AppContext.getPlayService().setPlayMessage(CourseDetailNewAcitivity.this);
                if (adapter2.getMedia_name().equals(url) && adapter2.getPlayPosition().equals(id)) {
                    AppContext.getPlayService().playPause();
                } else {
                    AppContext.getPlayService().stop();
                    AppContext.getPlayService().playStart();
                }
            }

            @Override
            public void doInput(View view, String qid) {
                showPopFormBottom(view);
                playPop.setQid(qid);
            }

            @Override
            public void doZan(String opType, String id, String state) {
                setZans(opType, id, state);
            }

            @Override
            public void doCollect(String opType, String id, String state) {

            }
        });
        lv_question.setAdapter(adapter2);
        getQuestion();
    }

    private int page = 1;

    private void getQuestion() {
        Map<String, String> params1 = new HashMap<>();
        params1.put("data[page]", "" + page);
        params1.put("data[limit]", "20");
        params1.put("data[cid]", cid);
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
                    //beanlist.clear();
                    if (datalist.size() == 0) {
                        // mPullRefreshListView.setEmptyView(llMain);
                    } else {
                        beanlist.addAll(datalist);
                    }
                    Log.e("tst", beanlist.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getFoucus();
                adapter2.notifyDataSetChanged();
                mPullRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                getFoucus();
                adapter2.notifyDataSetChanged();
                mPullRefreshScrollView.onRefreshComplete();
            }
        });
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_course_detail_new_acitivity);
        cid = getIntent().getExtras().getString("cid");
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
    }

    private void getFoucus() {
        if (ivUserBg == null) {
            return;
        }
        ivUserBg.setFocusable(true);
        ivUserBg.setFocusableInTouchMode(true);
        ivUserBg.requestFocus();
    }

    @OnClick({R.id.iv_share, R.id.tv_ask, R.id.iv_collect, R.id.ll_buycouse, R.id.tv_buy, R.id.ll_more})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_more:
                intent = new Intent(CourseDetailNewAcitivity.this, XiaoJListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("cid", courseDetailBean.getCid());
                bundle.putString("sid", stageListBean.getSid());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.iv_share:
                shareDialog = new ShareDialog(this, courseDetailBean.getShare_title(), courseDetailBean.getShare_des(), courseDetailBean.getShare_des(), courseDetailBean.getShare_linkurl());
                shareDialog.setCancelButtonOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareDialog.dismiss();
                    }
                });
                break;
            case R.id.tv_ask:
                showPopFormBottom(view);
                playPop.setQid("");
                break;
            case R.id.iv_collect:
                setUserCollect("1", courseDetailBean.getCid(), courseDetailBean.getIscollect() == 1 ? "0" : "1");//   protected void setUserCollect(final String optype, String aid, String state){
                break;
            case R.id.ll_buycouse:
                setCourseOrder();
                break;
            case R.id.tv_buy:
                if (stageListBean == null) {
                    return;
                }
                Map<String, String> params = new HashMap<String, String>();
                params.put("data[cid]", courseDetailBean.getCid());
                params.put("data[sid]", stageListBean.getSid());
                OkhttpUtilManager.setOrder(CourseDetailNewAcitivity.this, "Course/addCourseStageOrder", params);
                break;
        }
    }

    private void setCourseOrder() {
        showProgressDialog("生成订单中");
        Map<String, String> params = new HashMap<>();
        params.put("data[cid]", courseDetailBean.getCid());
        params.put("data[tid]", courseDetailBean.getTid());
        OkhttpUtilManager.postNoCacah(this, "Course/addCourseOrder", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("order", data);
                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    OrderBean orderBean = gson.fromJson(object.getString("data"), OrderBean.class);
                    Intent intent = new Intent(CourseDetailNewAcitivity.this, PayTypeAcitivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("orderBean", orderBean);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissProgressDialog();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxLogUtils.e("order", msg);
                dismissProgressDialog();
            }
        });
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
                ivCollect.setBackgroundResource(state.equals(KzConstanst.IS_FASLE) ? R.drawable.btn_collect : R.drawable.btn_collect_current);
                break;
            case "2"://举报
                break;//点赞
            case "3":
                adapter2.upZan();
                break;
        }
    }

    private Kz_CourseAskPopu playPop;
    WindowManager.LayoutParams params;

    public void showPopFormBottom(View view) {
        playPop = new Kz_CourseAskPopu(this, courseDetailBean);
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
    }

    @Override
    public void prePercent(int percent) {

    }

    @Override
    public void time(String start, String end, int pos) {

    }

    @Override
    public void playposition(int position) {

    }

    @Override
    public void state(int state) {
        adapter2.setState(state);
        adapter2.notifyDataSetChanged();
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
    public void funOrder(ReturnOrderBean bean) {
        super.funOrder(bean);
        if (bean.getType() == 1) {
            adapter2.updateOpen();
        }
    }
}
