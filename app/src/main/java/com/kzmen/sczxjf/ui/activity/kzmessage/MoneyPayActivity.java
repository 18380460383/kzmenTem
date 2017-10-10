package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.OrderBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.TextUtil;
import com.kzmen.sczxjf.view.DJEditText;
import com.kzmen.sczxjf.view.ExPandGridView;
import com.pingplusplus.android.Pingpp;
import com.vondear.rxtools.RxLogUtils;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 充值
 */
public class MoneyPayActivity extends SuperActivity {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.tv_price)
    DJEditText tvPrice;
    @InjectView(R.id.vv_vip)
    View vvVip;
    @InjectView(R.id.rb_weix)
    RadioButton rbWeix;
    @InjectView(R.id.ll_weix)
    LinearLayout llWeix;
    @InjectView(R.id.rb_ali)
    RadioButton rbAli;
    @InjectView(R.id.ll_ali)
    LinearLayout llAli;
    @InjectView(R.id.tv_sure)
    TextView tvSure;
    @InjectView(R.id.gv_money)
    ExPandGridView gv_money;
    @InjectView(R.id.iv_tips)
    ImageView iv_tips;
    private String price = "";
    private String priceSelect = "";
    private List<String> moneyList;
    private CommonAdapter<String> moneyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "余额充值");
        moneyList = new ArrayList<>();
        moneyList.add("10");
        moneyList.add("20");
        moneyList.add("50");
        moneyList.add("100");
        moneyList.add("200");
        moneyAdapter = new CommonAdapter<String>(this, R.layout.kz_money_item, moneyList) {
            @Override
            protected void convert(ViewHolder viewHolder, final String item, int position) {
                viewHolder.setText(R.id.tv_money, item);
                viewHolder.getConvertView().setBackgroundResource(R.color.white);
                if (item.equals(priceSelect)) {
                    viewHolder.getConvertView().setBackgroundResource(R.color.yellow);
                }
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        priceSelect = item;
                        tvPrice.setText("");
                        notifyDataSetChanged();
                    }
                });
            }
        };
        gv_money.setAdapter(moneyAdapter);
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_money_pay);
    }

    private void resetRB() {
        rbWeix.setChecked(false);
        rbAli.setChecked(false);
    }

    private String payment = "";

    @OnClick({R.id.ll_weix, R.id.ll_ali, R.id.tv_sure, R.id.iv_tips})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_weix:
                resetRB();
                rbWeix.setChecked(!rbWeix.isChecked());
                payment = "3";
                break;
            case R.id.ll_ali:
                resetRB();
                rbAli.setChecked(!rbAli.isChecked());
                payment = "2";
                break;
            case R.id.tv_sure:
                getMoney();
                if (TextUtil.isEmpty(price)) {
                    RxToast.normal("请选择或者输入充值金额");
                    return;
                }
                if (TextUtil.isEmpty(payment)) {
                    RxToast.normal("请选择支付方式");
                    return;
                }
                setOrder();
                //User/addUserRecharge/
                break;
            case R.id.iv_tips:
                Intent intent1 = new Intent(MoneyPayActivity.this, WebAcitivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("title", "充值说明");
                bundle1.putString("url", OkhttpUtilManager.URL_RECHARGE);
                intent1.putExtras(bundle1);
                startActivity(intent1);
                break;
        }
    }

    private void getMoney() {
        if (TextUtil.isEmpty(priceSelect)) {
            if (TextUtil.isEmpty(tvPrice.getText().toString())) {
                return;
            } else {
                price = tvPrice.getText().toString();
            }
        } else {
            price = priceSelect;
        }
    }

    private OrderBean orderBean;

    private void setOrder() {
        showProgressDialog("支付中");
        Map<String, String> params = new HashMap<>();
        params.put("data[payment]", "" + payment);
        params.put("data[money]", price);
        OkhttpUtilManager.postNoCacah(this, "User/addUserRecharge/", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                RxLogUtils.e("tst", data);
                Gson gson = new Gson();
                JSONObject object1 = null;
                try {
                    object1 = new JSONObject(data);
                    orderBean = gson.fromJson(object1.getString("data"), OrderBean.class);
                    doPay(payment);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissProgressDialog();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxLogUtils.e("tst", msg);
                dismissProgressDialog();
            }
        });
    }

    private void doPay(String payment) {
        if (orderBean == null) {
            Log.e("order", "orderbean is null");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("data[payment]", payment);
        params.put("data[order]", orderBean.getOrder());
        params.put("data[source]", orderBean.getSource());
        OkhttpUtilManager.setUserOrder(this, params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                RxLogUtils.e("tst", data);
                Pingpp.createPayment(MoneyPayActivity.this, data);
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxLogUtils.e("tst", msg);
            }
        });
    }
}
