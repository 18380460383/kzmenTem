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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "我的邮件");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_msg_join);
        setText();
    }

    private String pro_usr = "发起人";
    private String pro_name = "项目名称";
    private String pro_price = "2000";

    private void setText() {
        String originText = "您正在参与  " + pro_usr + "  邀请的  " + pro_name + "  项目合伙人计划，您需要支付人民币<font color='#ffca3f'>" + pro_price + "</font>成为该项目合伙人。";
        StringBuilder sb = new StringBuilder(originText);
        tvMsg.setText(Html.fromHtml(sb.toString()));
        tvMsg.setMovementMethod(LinkMovementMethod.getInstance());
        tvPrice.setText(pro_price);
    }

    @OnClick({R.id.iv_add, R.id.tv_rule, R.id.tv_pay})
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
                RxToast.normal("支付");
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }
    }
}
