package com.kzmen.sczxjf.ui.activity.agency;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.WebAcitivity;
import com.vondear.rxtools.view.RxToast;

import butterknife.InjectView;
import butterknife.OnClick;

public class MsgJoinActivity extends SuperActivity {

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
    @InjectView(R.id.tv_msg)
    TextView tvMsg;
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.tv_rule)
    TextView tvRule;
    @InjectView(R.id.tv_pay)
    TextView tvPay;
    @InjectView(R.id.activity_msg_join)
    LinearLayout activityMsgJoin;
    @InjectView(R.id.iv_state)
    ImageView ivState;
    private boolean isCheck = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "我的邮件");
        setText();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_msg_join);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            pro_usr = bundle.getString("pro_usr");
            pro_name = bundle.getString("pro_name");
            pro_price = bundle.getString("pro_price");
        }
    }

    private String pro_usr = "";
    private String pro_name = "";
    private String pro_price = "";

    private void setText() {
        String originText = "您正在参与  " + pro_usr + "  邀请的  " + pro_name + "  项目合伙人计划，您需要支付人民币<font color='#ffca3f'>" + pro_price + "</font>成为该项目合伙人。";
        StringBuilder sb = new StringBuilder(originText);
        tvMsg.setText(Html.fromHtml(sb.toString()));
        tvMsg.setMovementMethod(LinkMovementMethod.getInstance());
        tvPrice.setText(pro_price);
    }

    @OnClick({R.id.iv_add, R.id.tv_rule, R.id.tv_pay, R.id.iv_state})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_add:
                showInfoPopu(view);
                break;
            case R.id.tv_rule:
                intent = new Intent(MsgJoinActivity.this, WebAcitivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", "用户协议");
                bundle.putString("url", OkhttpUtilManager.URL_USER_RULE);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_pay:
                if (isCheck) {
                    RxToast.normal("支付");
                }
                break;
            case R.id.iv_state:
                isCheck = !isCheck;
                if (isCheck) {
                    ivState.setBackgroundResource(R.drawable.icon_checkbox2);
                    tvPay.setBackgroundColor(getResources().getColor(R.color.yellow));
                } else {
                    tvPay.setBackgroundColor(getResources().getColor(R.color.gloomy));
                    ivState.setBackgroundResource(R.drawable.icon_checkbox1);
                }
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }
    }
}
