package com.kzmen.sczxjf.ui.activity.agency;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.view.MyListView;

import butterknife.InjectView;
import butterknife.OnClick;

public class MyMoneyDetailActivity extends SuperActivity {

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
    @InjectView(R.id.tv_more)
    TextView tvMore;
    @InjectView(R.id.activity_my_money_detail)
    RelativeLayout activityMyMoneyDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "我的收支明细");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_my_money_detail);
    }

    @OnClick(R.id.tv_more)
    public void onViewClicked() {
    }
}
