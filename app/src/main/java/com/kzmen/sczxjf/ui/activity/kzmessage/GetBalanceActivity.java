package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.TextUtil;
import com.vondear.rxtools.RxLogUtils;
import com.vondear.rxtools.view.RxToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 余额提现
 */
public class GetBalanceActivity extends SuperActivity {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.ll_rule)
    LinearLayout ll_rule;
    @InjectView(R.id.tv_money)
    TextView tvMoney;
    @InjectView(R.id.tv_rule)
    TextView tvRule;
    @InjectView(R.id.rb_weix)
    RadioButton rbWeix;
    @InjectView(R.id.ll_weix)
    LinearLayout llWeix;
    @InjectView(R.id.rb_ali)
    RadioButton rbAli;
    @InjectView(R.id.ll_ali)
    LinearLayout llAli;
    @InjectView(R.id.tv_get_money)
    EditText tvGetMoney;
    @InjectView(R.id.tv_allmoney)
    TextView tvAllmoney;
    @InjectView(R.id.tv_sure)
    TextView tvSure;
    private String payment = "";
    private String money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "余额提现");
        if (!TextUtil.isEmpty(money)) {
            tvMoney.setText(money);
        }
    }


    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_get_balance);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            money = bundle.getString("money");
            if (!TextUtil.isEmpty(money)) {
                money = "" + Integer.valueOf(money) / 100;
            }
        }
    }

    @OnClick({R.id.tv_rule, R.id.ll_weix, R.id.ll_ali, R.id.tv_allmoney, R.id.tv_sure, R.id.ll_rule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_rule:
                break;
            case R.id.ll_weix:
                resetRB();
                rbWeix.setChecked(!rbWeix.isChecked());
                payment = "2";
                break;
            case R.id.ll_ali:
                resetRB();
                rbAli.setChecked(!rbAli.isChecked());
                payment = "1";
                break;
            case R.id.tv_allmoney:
                tvGetMoney.setText(money);
                break;
            case R.id.tv_sure:
                getMoney();
                break;
            case R.id.ll_rule:
                Intent intent1 = new Intent(GetBalanceActivity.this, WebAcitivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("title", "提现规则");
                bundle1.putString("url", OkhttpUtilManager.URL_WITHDRAWAL);
                intent1.putExtras(bundle1);
                startActivity(intent1);
                break;
        }
    }

    private void resetRB() {
        rbWeix.setChecked(false);
        rbAli.setChecked(false);
    }

    private void getMoney() {
        if (TextUtil.isEmpty(tvGetMoney.getText().toString())) {
            RxToast.normal("请输入提现金额");
            return;
        }
        if (TextUtil.isEmpty(payment)) {
            RxToast.normal("请选择提现方式");
            return;
        }
        if (Integer.valueOf(tvGetMoney.getText().toString()) > Integer.valueOf(money)) {
            RxToast.normal("余额不足");
            return;
        }
        showProgressDialog("提现中。。。");
        Map<String, String> params = new HashMap<>();
        params.put("money", tvGetMoney.getText().toString());
        params.put("type", payment);
        OkhttpUtilManager.postNoCacah(this, "User/setMoneyWithdraw", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                RxLogUtils.e("tst", data);
                dismissProgressDialog();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxLogUtils.e("tst", msg);
                dismissProgressDialog();
            }
        });
    }
}
