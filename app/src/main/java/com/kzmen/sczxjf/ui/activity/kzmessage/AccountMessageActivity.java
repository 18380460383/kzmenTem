package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.UserMessageBean;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;

import butterknife.InjectView;


public class AccountMessageActivity extends SuperActivity {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.tv_acountid)
    TextView tvAcountid;
    @InjectView(R.id.tv_realname)
    TextView tvRealname;
    @InjectView(R.id.tv_phone)
    TextView tvPhone;
    @InjectView(R.id.tv_get_money)
    TextView tvGetMoney;
    @InjectView(R.id.tv_souy)
    TextView tvSouy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "账号信息");
        initView();
    }

    private void initView() {
        UserMessageBean userMessageBean = AppContext.getInstance().getUserMessageBean();
        setUserInfo(userMessageBean);
    }

    private void setUserInfo(UserMessageBean userMessageBean) {
        if (userMessageBean != null) {
            tvAcountid.setText(userMessageBean.getUid());
            tvRealname.setText(userMessageBean.getNickname());
            tvPhone.setText(userMessageBean.getPhone());
            tvGetMoney.setText("￥" + userMessageBean.getWithdraw_ok());
            tvSouy.setText("￥" + userMessageBean.getEarn_money());
        }
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_account_message);
    }
}
