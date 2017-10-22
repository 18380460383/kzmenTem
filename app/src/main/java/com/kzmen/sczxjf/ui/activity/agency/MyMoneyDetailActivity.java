package com.kzmen.sczxjf.ui.activity.agency;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.agent.IncomeBean;
import com.kzmen.sczxjf.bean.agent.MyMoneyIndexBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.AgOkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.StringUtils;
import com.kzmen.sczxjf.view.MyListView;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;

public class MyMoneyDetailActivity extends SuperActivity {
    @InjectView(R.id.sv_main)
    PullToRefreshScrollView mPullRefreshScrollView;
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.rl_top)
    RelativeLayout rlTop;
    @InjectView(R.id.tv_all_earing)
    TextView tvAllEaring;
    @InjectView(R.id.tv_pay_out)
    TextView tvPayOut;
    @InjectView(R.id.tv_ally_pay)
    TextView tvAllyPay;
    @InjectView(R.id.tv_champ_pay)
    TextView tvChampPay;
    @InjectView(R.id.tv_par_pay)
    TextView tvParPay;
    @InjectView(R.id.lv_detail)
    MyListView lvDetail;
    @InjectView(R.id.activity_my_money_detail)
    RelativeLayout activityMyMoneyDetail;
    @InjectView(R.id.tv_month)
    TextView tvMonth;
    @InjectView(R.id.ll_main)
    LinearLayout llMain;
    private MyMoneyIndexBean myMoneyIndexBean;
    private int page = 1;
    private CommonAdapter<IncomeBean> commAdapter;
    private List<IncomeBean> incomeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "我的收支明细");
        getIndexData();
        getMonthIncome();
        getChampList();
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
                getChampList();
            }
        });
        commAdapter = new CommonAdapter<IncomeBean>(this, R.layout.money_list_item, incomeList) {
            @Override
            protected void convert(ViewHolder viewHolder, IncomeBean item, int position) {
                viewHolder.setText(R.id.tv_date, item.getCreate_time())
                        .setText(R.id.tv_price, item.getIncome());
            }
        };
        lvDetail.setAdapter(commAdapter);
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_my_money_detail);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            distrubution_type = bundle.getString("distrubution_type");
        }
    }

    private void getIndexData() {
        //users/member_extension
        showProgressDialog("加载中");
        Map<String, String> params = new HashMap<>();
        params.put("member_id", "" + AppContext.getInstance().getUserMessageBean().getUid());
        AgOkhttpUtilManager.postNoCacah(this, "users/member_extension", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                dismissProgressDialog();
                try {
                    Gson gson = new Gson();
                    myMoneyIndexBean = gson.fromJson(data, MyMoneyIndexBean.class);
                    if (null != myMoneyIndexBean) {
                        initView();
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                dismissProgressDialog();
            }
        });
    }

    private int monthIncome = 0;

    private void getMonthIncome() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        int month = cal.get(Calendar.MONTH)+1;//获取月份
        Map<String, String> params = new HashMap<>();
        params.put("year", "" + year);
        params.put("month", "" + month);
        params.put("member_id", "" + AppContext.getInstance().getUserMessageBean().getUid());
        AgOkhttpUtilManager.postNoCacah(this, "users/member_month_income", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                dismissProgressDialog();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(data);
                    monthIncome = jsonObject.getInt("month_income");
                    tvMonth.setText("" + monthIncome);
                } catch (Exception e) {
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                dismissProgressDialog();
            }
        });
    }

    private void initView() {
        tvAllEaring.setText(StringUtils.addComma(myMoneyIndexBean.getTotal_income()));
        tvPayOut.setText(StringUtils.addComma(myMoneyIndexBean.getTotal_expend()));
        tvAllyPay.setText(StringUtils.addComma(myMoneyIndexBean.getLeader_money()));
        tvChampPay.setText(StringUtils.addComma(myMoneyIndexBean.getLeader_money()));
        tvParPay.setText(StringUtils.addComma(myMoneyIndexBean.getPartner_money()));
    }


    private void getFoucus() {
        if (tvAllEaring == null) {
            return;
        }
        tvAllEaring.setFocusable(true);
        tvAllEaring.setFocusableInTouchMode(true);
        tvAllEaring.requestFocus();
    }

    private String distrubution_type = "10";

    private void getChampList() {
        Map<String, String> params1 = new HashMap<>();
        params1.put("page", "" + page);
        params1.put("limit", "20");
        params1.put("distrubution_type", distrubution_type);
        params1.put("member_id", "" + AppContext.getInstance().getUserMessageBean().getUid());
        AgOkhttpUtilManager.postNoCacah(this, "users/member_money_change_list", params1, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                getFoucus();
                JSONObject object = null;
                try {
                    Gson gson = new Gson();
                    object = new JSONObject(data);
                    List<IncomeBean> datalist = gson.fromJson(object.getString("data"), new TypeToken<List<IncomeBean>>() {
                    }.getType());
                    if (datalist.size() == 0) {
                        lvDetail.setEmptyView(llMain);
                    } else {
                        incomeList.addAll(datalist);
                    }
                } catch (Exception e) {
                }
                commAdapter.notifyDataSetChanged();
                dismissProgressDialog();
                mPullRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
                dismissProgressDialog();
                lvDetail.setEmptyView(llMain);
                commAdapter.notifyDataSetChanged();
                mPullRefreshScrollView.onRefreshComplete();
            }
        });
    }
}
