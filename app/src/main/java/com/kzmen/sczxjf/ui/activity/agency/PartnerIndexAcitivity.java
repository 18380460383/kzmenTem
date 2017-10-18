package com.kzmen.sczxjf.ui.activity.agency;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.agent.ParIndexBean;
import com.kzmen.sczxjf.bean.agent.ParProListBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.AgOkhttpUtilManager;
import com.kzmen.sczxjf.popuwidow.MsgShowPopuwindow;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.StringUtils;
import com.kzmen.sczxjf.view.CircleImageViewBorder;
import com.kzmen.sczxjf.view.MyListView;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 合伙人首页
 */
public class PartnerIndexAcitivity extends SuperActivity {

    @InjectView(R.id.sv_main)
    PullToRefreshScrollView mPullRefreshScrollView;
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.iv_add)
    ImageView ivAdd;
    @InjectView(R.id.rl_top)
    RelativeLayout rlTop;
    @InjectView(R.id.iv_user_head)
    CircleImageViewBorder ivUserHead;
    @InjectView(R.id.tv_user_name)
    TextView tvUserName;
    @InjectView(R.id.tv_msg_count)
    TextView tvMsgCount;
    @InjectView(R.id.ll_msg)
    LinearLayout llMsg;
    @InjectView(R.id.tv_all_earing)
    TextView tvAllEaring;
    @InjectView(R.id.ll_all_count)
    LinearLayout llAllCount;
    @InjectView(R.id.tv_today_earing)
    TextView tvTodayEaring;
    @InjectView(R.id.ll_today_count)
    LinearLayout llTodayCount;
    @InjectView(R.id.tv_par_all)
    TextView tvParAll;
    @InjectView(R.id.ll_par_all)
    LinearLayout llParAll;
    @InjectView(R.id.tv_today_par_add)
    TextView tvTodayParAdd;
    @InjectView(R.id.ll_par_today)
    LinearLayout llParToday;
    @InjectView(R.id.iv_percent)
    ImageView ivPercent;
    @InjectView(R.id.lv_leader_list)
    MyListView lvLeaderList;
    @InjectView(R.id.ll_add_new_pro)
    LinearLayout llAddNewPro;
    @InjectView(R.id.activity_partner_index_acitivity)
    RelativeLayout activityPartnerIndexAcitivity;
    @InjectView(R.id.ll_main)
    LinearLayout llMain;

    private ParIndexBean parIndexBean;
    private int page = 1;
    private List<ParProListBean> proList = new ArrayList<>();
    private CommonAdapter<ParProListBean> commAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "合伙人");
        showProgressDialog("加载中");
        final Map<String, String> params = new HashMap<>();
        params.put("member_id", "" + AppContext.getInstance().getUserMessageBean().getUid());
        AgOkhttpUtilManager.postNoCacah(this, "users/partner_statistics", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    Gson gson = new Gson();
                    parIndexBean = gson.fromJson(data, ParIndexBean.class);
                    if (null != parIndexBean) {
                        initView();
                    }
                } catch (Exception e) {

                }
                dismissProgressDialog();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
                dismissProgressDialog();
            }
        });
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
                page++;
                getProList();
            }
        });
        commAdapter = new CommonAdapter<ParProListBean>(this, R.layout.par_project_list_item, proList) {
            @Override
            protected void convert(ViewHolder viewHolder, ParProListBean item, int position) {
                viewHolder.setText(R.id.tv_title, item.getName())
                        .setText(R.id.tv_count, item.getJoin_count())
                        .setText(R.id.tv_price, item.getTotal_fee());
            }
        };
        lvLeaderList.setAdapter(commAdapter);
        getProList();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_partner_index_acitivity);
    }

    private void initView() {
        if (null != parIndexBean) {
            tvAllEaring.setText(StringUtils.addComma(parIndexBean.getTotal_income()));
            tvTodayEaring.setText(StringUtils.addComma(parIndexBean.getToday_income()));
            tvTodayParAdd.setText(StringUtils.addComma(parIndexBean.getToday_new_partner()));
            tvParAll.setText(StringUtils.addComma(parIndexBean.getPartner_count()));
            tvUserName.setText(AppContext.getInstance().getUserMessageBean().getUsername());
            Glide.with(this).load(AppContext.getInstance().getUserMessageBean().getAvatar()).into(ivUserHead);
        }
    }

    private void getFoucus() {
        if (tvAllEaring == null) {
            return;
        }
        tvAllEaring.setFocusable(true);
        tvAllEaring.setFocusableInTouchMode(true);
        tvAllEaring.requestFocus();
    }

    @OnClick({R.id.iv_add, R.id.ll_msg, R.id.ll_all_count, R.id.ll_today_count, R.id.ll_par_all, R.id.ll_par_today, R.id.iv_percent, R.id.ll_add_new_pro})
    public void onViewClicked(View view) {
        Intent intent = null;
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_add:
                showInfoPopu(view);
                break;
            case R.id.ll_msg:
                intent = new Intent(PartnerIndexAcitivity.this, MsgListActivity.class);
                break;
            case R.id.ll_all_count:
                intent = new Intent(PartnerIndexAcitivity.this, MyMoneyDetailActivity.class);
                bundle.putString("distrubution_type", "40");
                intent.putExtras(bundle);
                break;
            case R.id.ll_today_count:
                intent = new Intent(PartnerIndexAcitivity.this, MyMoneyDetailActivity.class);
                bundle.putString("distrubution_type", "40");
                intent.putExtras(bundle);
                break;
            case R.id.ll_par_all:
                break;
            case R.id.ll_par_today:
                break;
            case R.id.iv_percent:
                viewShow = view;
                getRule(Constants.parEx);
                break;
            case R.id.ll_add_new_pro:
                intent = new Intent(PartnerIndexAcitivity.this, CreateNewProActivity.class);
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }
    }

    private MsgShowPopuwindow msgShowPopuwindow;
    private WindowManager.LayoutParams params;

    public void showMsgPopu(View view, String msg) {
        msgShowPopuwindow = new MsgShowPopuwindow(this, msg);
        msgShowPopuwindow.showAtLocation(view, Gravity.CENTER | Gravity.CENTER_VERTICAL, 0, 0);
        params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        msgShowPopuwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    private View viewShow;

    private void getRule(String name) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        AgOkhttpUtilManager.postNoCacah(this, "bases/get_config_explain", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (null != jsonObject.getString("value")) {
                        showMsgPopu(viewShow, jsonObject.getString("value"));
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
            }
        });
    }

    private void getProList() {
        Map<String, String> params = new HashMap<>();
        params.put("page", "" + page);
        params.put("limit", "20");
        params.put("member_id", AppContext.getInstance().getUserMessageBean().getUid());
        params.put("type", "10");
        AgOkhttpUtilManager.postNoCacah(this, "users/partner_project_list", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                getFoucus();
                JSONObject object = null;
                try {
                    Gson gson = new Gson();
                    object = new JSONObject(data);
                    List<ParProListBean> datalist = gson.fromJson(object.getString("data"), new TypeToken<List<ParProListBean>>() {
                    }.getType());
                    if (datalist.size() == 0) {
                        lvLeaderList.setEmptyView(llMain);
                    } else {
                        proList.addAll(datalist);
                    }
                } catch (Exception e) {
                }
                commAdapter.notifyDataSetChanged();
                mPullRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
                lvLeaderList.setEmptyView(llMain);
                commAdapter.notifyDataSetChanged();
                mPullRefreshScrollView.onRefreshComplete();
            }
        });
    }
}
