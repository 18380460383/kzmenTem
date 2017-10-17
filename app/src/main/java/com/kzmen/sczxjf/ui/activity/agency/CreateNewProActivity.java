package com.kzmen.sczxjf.ui.activity.agency;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.vondear.rxtools.view.RxToast;

import butterknife.InjectView;
import butterknife.OnClick;

public class CreateNewProActivity extends SuperActivity {

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
    @InjectView(R.id.et_pro_name)
    EditText etProName;
    @InjectView(R.id.et_pro_price)
    EditText etProPrice;
    @InjectView(R.id.et_pro_chengben)
    EditText etProChengben;
    @InjectView(R.id.et_first)
    EditText etFirst;
    @InjectView(R.id.et_secod)
    EditText etSecod;
    @InjectView(R.id.et_third)
    EditText etThird;
    @InjectView(R.id.et_expl)
    EditText etExpl;
    @InjectView(R.id.ll_add_new_pro)
    LinearLayout llAddNewPro;
    @InjectView(R.id.activity_create_new_pro)
    RelativeLayout activityCreateNewPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "新项目申请");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_create_new_pro);
    }

    @OnClick({R.id.iv_add, R.id.ll_add_new_pro})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_add:
                showInfoPopu(view);
                break;
            case R.id.ll_add_new_pro:
                RxToast.normal("申请");
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }
    }
}