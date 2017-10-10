package com.kzmen.sczxjf.ui.fragment.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.CourseDetailBean;
import com.kzmen.sczxjf.bean.kzbean.KzCoursePlayBean;
import com.kzmen.sczxjf.bean.kzbean.ReturnOrderBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.kzmessage.CoursePlayDeatilActivity;
import com.kzmen.sczxjf.ui.fragment.basic.SuperFragment;
import com.kzmen.sczxjf.utils.TextUtil;
import com.kzmen.sczxjf.view.MyListView;
import com.vondear.rxtools.RxLogUtils;
import com.vondear.rxtools.view.RxToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pjj18 on 2017/8/14.
 */

public class CourseDetailPlayListFragment extends SuperFragment {
    MyListView lvPlayList;
    MyListView lv_xiaojiang_list;
    TextView tv_xiaoj;
    TextView tv_des;
    TextView tv_money;
    private LinearLayout ll_main;
    private LinearLayout ll_high;
    private LinearLayout ll_primal;
    private CourseDetailBean.StageListBean stageListBean;
    private LinearLayout ll_buy;
    private TextView tv_stage;
    private TextView tv_days;
    private TextView tv_price;
    private TextView tv_buy;
    private List<KzCoursePlayBean> beanlist;
    private CommonAdapter<KzCoursePlayBean> adapter2;
    private List<CourseDetailBean.StageListBean.XiaojiangListBean> xiaojianglist;
    private CommonAdapter<CourseDetailBean.StageListBean.XiaojiangListBean> xjAdapter;

    @Override
    protected void lazyLoad() {

    }

    public CourseDetailPlayListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kz_course_detail_play_list, container, false);
        beanlist = new ArrayList<>();
        xiaojianglist = new ArrayList<>();
        initView(view);
        initData();
        EventBus.getDefault().register(this);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void funUpdate(ReturnOrderBean bean) {
        if (bean.getType() == 1) {
            RxLogUtils.e("tst", "支付成功");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private int tabPos = 0;
    private String collect = "";
    private String zans = "";
    private String iszan = "";
    private String cid = "";
    private String type = "";
    private String isunlock = "";
    private String unlock_desc = "";
    private String unlock_money = "";

    private void initView(final View view) {
        lvPlayList = (MyListView) view.findViewById(R.id.lv_play_list);
        lv_xiaojiang_list = (MyListView) view.findViewById(R.id.lv_xiaojiang_list);
        ll_main = (LinearLayout) view.findViewById(R.id.ll_main);
        ll_buy = (LinearLayout) view.findViewById(R.id.ll_buy);
        ll_high = (LinearLayout) view.findViewById(R.id.ll_high);
        ll_primal = (LinearLayout) view.findViewById(R.id.ll_primal);
        tv_days = (TextView) view.findViewById(R.id.tv_days);
        tv_stage = (TextView) view.findViewById(R.id.tv_stage);
        tv_price = (TextView) view.findViewById(R.id.tv_price);
        tv_xiaoj = (TextView) view.findViewById(R.id.tv_xiaoj);
        tv_buy = (TextView) view.findViewById(R.id.tv_buy);
        tv_des = (TextView) view.findViewById(R.id.tv_des);
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        tv_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stageListBean == null) {
                    return;
                }
                Map<String, String> params = new HashMap<String, String>();
                params.put("data[cid]", cid);
                params.put("data[sid]", stageListBean.getSid());
                OkhttpUtilManager.setOrder(getActivity(), "Course/addCourseStageOrder", params);
            }
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            tabPos = bundle.getInt("tabPos");
            cid = bundle.getString("cid");
            type = bundle.getString("type");
            zans = bundle.getString("zans");
            iszan = bundle.getString("iszan");
            collect = bundle.getString("collect");
            isunlock = bundle.getString("isunlock");
            unlock_desc = bundle.getString("unlock_desc");
            unlock_money = bundle.getString("unlock_money");
            if (!type.equals("1")) {
                tv_des.setText(unlock_desc);
                tv_money.setText(unlock_money);
            }
            stageListBean = (CourseDetailBean.StageListBean) bundle.getSerializable("stage");
            for (CourseDetailBean.StageListBean.KejianListBean bean : stageListBean.getKejian_list()) {
                KzCoursePlayBean kzCoursePlayBean = new KzCoursePlayBean(bean.getChapter_id(), bean.getChapter_id(), bean.getChapter_name(), 0, "", "");
                beanlist.add(kzCoursePlayBean);
                if (null != bean.getKejian()) {
                    for (CourseDetailBean.StageListBean.KejianListBean.KejianBean kejianBean : bean.getKejian()) {
                        KzCoursePlayBean kzCoursePlayBean1 = new KzCoursePlayBean(kejianBean.getId(), bean.getChapter_id(), kejianBean.getTitle(), 1, kejianBean.getMedia(), kejianBean.getMedia_time());
                        beanlist.add(kzCoursePlayBean1);
                    }
                }
            }
            xiaojianglist.addAll(stageListBean.getXiaojiang_list());
            if (xiaojianglist.size() > 0) {
                tv_xiaoj.setVisibility(View.VISIBLE);
            } else {
                tv_xiaoj.setVisibility(View.GONE);
            }
            ll_buy.setVisibility(View.VISIBLE);
            ll_main.setBackgroundResource(R.color.white);
            if (stageListBean.getIsunlock() == 1) {
                ll_buy.setVisibility(View.GONE);
                ll_main.setBackgroundResource(R.color.white);
            } else {
                ll_main.setBackgroundResource(R.color.gloomy);
                tv_days.setText("" + stageListBean.getUnlock_day());//stageListBean.getUnlock_day()
                tv_price.setText("" + stageListBean.getUnlock_money());//stageListBean.getUnlock_money()
                tv_stage.setText("" + stageListBean.getStage_name());
            }
        }
        if (tabPos == 0) {
            return;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private void initData() {
        adapter2 = new CommonAdapter<KzCoursePlayBean>(getActivity(), R.layout.kz_course_detail_list_item, beanlist) {
            @Override
            protected void convert(ViewHolder viewHolder, KzCoursePlayBean item, final int position) {
                if (stageListBean.getIsunlock() != 1) {
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
                        if (stageListBean.getIsunlock() == 1) {
                            Intent intent = new Intent(getActivity(), CoursePlayDeatilActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("cid", cid);
                            bundle.putString("zans", zans);
                            bundle.putString("iszan", iszan);
                            bundle.putString("collect", collect);
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
        lvPlayList.setAdapter(adapter2);
        xjAdapter = new CommonAdapter<CourseDetailBean.StageListBean.XiaojiangListBean>(getActivity(), R.layout.kz_xiaojiang_list_item, xiaojianglist) {
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
                        if (stageListBean.getIsunlock() == 1) {
                            if (item.getCharge_type().equals("1")) {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("data[type]", "1");
                                params.put("data[aid]", item.getId());
                                OkhttpUtilManager.setOrder(mContext, "Course/addEavesdropOrder", params);
                            } else {
                                Intent intent = new Intent(getActivity(), CoursePlayDeatilActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("cid", cid);
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
        lv_xiaojiang_list.setAdapter(xjAdapter);
    }

}
