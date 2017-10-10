package com.kzmen.sczxjf.ui.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.MoneyLogBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.GetBalanceActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.MoneyPayActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.WebAcitivity;
import com.kzmen.sczxjf.view.MyListView;
import com.vondear.rxtools.RxLogUtils;
import com.vondear.rxtools.view.RxToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 我的钱包
 */
public class MyPackageAcitivity extends SuperActivity {

    @InjectView(R.id.ll_main)
    LinearLayout ll_main;
    @InjectView(R.id.tv_money)
    TextView tvMoney;
    @InjectView(R.id.tv_get_money)
    TextView tvGetMoney;
    @InjectView(R.id.tv_add_money)
    TextView tvAddMoney;
    @InjectView(R.id.tv_money_edu)
    TextView tvMoneyEdu;
    @InjectView(R.id.iv_know)
    ImageView ivKnow;
    @InjectView(R.id.tv_rule)
    TextView tvRule;
    @InjectView(R.id.lv_integral)
    MyListView lvIntegral;
    @InjectView(R.id.tv_more_list)
    TextView tvMoreList;
    private MoneyLogBean moneyLogBean;
    private CommonAdapter<MoneyLogBean.data> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView() {
        if (moneyLogBean != null) {
            tvMoney.setText("￥ " + moneyLogBean.getBalance());
            tvMoneyEdu.setText("￥ " + moneyLogBean.getWithdraw());

            if (null != moneyLogBean.getData() && moneyLogBean.getData().size() > 0) {
                listAdapter = new CommonAdapter<MoneyLogBean.data>(this, R.layout.kz_money_list_item, moneyLogBean.getData()) {
                    @Override
                    protected void convert(ViewHolder viewHolder, MoneyLogBean.data item, int position) {
                        viewHolder.setText(R.id.tv_name, item.getType_str())
                                .setText(R.id.tv_price, item.getMoney())
                                .setText(R.id.tv_date, item.getDatetime())
                                .setText(R.id.tv_state, item.getStatus_str());
                        if (item.getState().equals("1")) {
                            viewHolder.setBackgroundRes(R.id.iv_type, R.drawable.icon_reduce64);
                        } else {
                            viewHolder.setBackgroundRes(R.id.iv_type, R.drawable.icon_add);
                        }
                    }
                };
                lvIntegral.setEmptyView(ll_main);
                lvIntegral.setAdapter(listAdapter);
            } else {
                tvMoreList.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "我的钱包");
        initView();
        initData();
    }

    private void initData() {
        showProgressDialog("数据获取中");
        Map<String, String> params = new HashMap<>();
        params.put("data[page]", "1");
        params.put("data[limit]", "20");
        OkhttpUtilManager.postNoCacah(this, "User/getMoneyLogList", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                RxLogUtils.e("tst", data);
                Gson gson = new Gson();
                moneyLogBean = gson.fromJson(data, MoneyLogBean.class);
                initView();
                dismissProgressDialog();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
                dismissProgressDialog();
            }
        });
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_my_package_acitivity);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.tv_get_money, R.id.tv_add_money,R.id.iv_know})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_get_money:
                intent = new Intent(MyPackageAcitivity.this, GetBalanceActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("money", moneyLogBean.getWithdraw());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_add_money:
                intent = new Intent(MyPackageAcitivity.this, MoneyPayActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_know:
                Intent intent1 = new Intent(MyPackageAcitivity.this, WebAcitivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("title", "提现规则");
                bundle1.putString("url", OkhttpUtilManager.URL_WITHDRAWAL);
                intent1.putExtras(bundle1);
                startActivity(intent1);
                break;
        }
    }
}
