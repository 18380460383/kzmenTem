package com.kzmen.sczxjf.ui.activity.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.OrderBean;
import com.kzmen.sczxjf.bean.kzbean.ReturnOrderBean;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.TextUtil;
import com.pingplusplus.android.Pingpp;
import com.vondear.rxtools.view.RxToast;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

import static com.kzmen.sczxjf.R.id.tv_acount_price;
import static com.kzmen.sczxjf.R.id.tv_count;

public class PayTypeAcitivity extends SuperActivity {
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.iv_reduce)
    ImageView ivReduce;
    @InjectView(tv_count)
    TextView tvCount;
    @InjectView(R.id.iv_add)
    ImageView ivAdd;
    @InjectView(R.id.ll_vip_recharge)
    LinearLayout llVipRecharge;
    @InjectView(R.id.tv_acount)
    TextView tvAcount;
    @InjectView(tv_acount_price)
    TextView tvAcountPrice;
    @InjectView(R.id.tv_acount_message)
    TextView tvAcountMessage;
    @InjectView(R.id.rb_acount)
    RadioButton rbAcount;
    @InjectView(R.id.rl_acount)
    RelativeLayout rlAcount;
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
    @InjectView(R.id.vv_vip)
    View vv_vip;
    private String title = "";
    private String price = "";
    private double priceDouble;
    private int chargeYear = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onCreateDataForView() {
        if (TextUtil.isEmpty(title)) {
            setTitle(R.id.kz_tiltle, "支付方式");
            llVipRecharge.setVisibility(View.GONE);
            vv_vip.setVisibility(View.GONE);
        } else {
            priceDouble = Double.valueOf(price);
            setTitle(R.id.kz_tiltle, title);
        }
        tvPrice.setText(price);
        if (orderBean != null) {
            Double money = Double.valueOf(orderBean.getMoney());
            priceDouble = ((Double) (money * 1.0 / 100));
            price = "" + ((Double) (money * 1.0 / 100));
            tvPrice.setText(price);
            tvAcountPrice.setText("￥" + orderBean.getBalance());
            String price = orderBean.getMoney();
            String balance = orderBean.getBalance();
            if (TextUtil.isEmpty(balance)) {
                balance = "0";
            }
            if (TextUtil.isEmpty(price)) {
                price = "0";
            }
            boolean isHaveMoney = Double.valueOf(price) > Double.valueOf(balance);
            tvAcountMessage.setVisibility(isHaveMoney ? View.VISIBLE : View.GONE);
            if (!isHaveMoney) {
                rbAcount.setClickable(false);
            }
        }
    }

    private OrderBean orderBean;

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_pay_type_acitivity);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderBean = (OrderBean) bundle.getSerializable("orderBean");
            title = bundle.getString("title");
            price = bundle.getString("price");


            if (orderBean != null) {
                Log.e("order", orderBean.toString());
            }

        }
    }

    private String payment = "";

    @OnClick({R.id.iv_reduce, R.id.iv_add, R.id.rl_acount, R.id.ll_weix, R.id.ll_ali, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_reduce:
                if (chargeYear > 1) {
                    chargeYear--;
                    tvCount.setText(chargeYear + "年");
                    tvPrice.setText("" + (priceDouble * chargeYear));
                }
                break;
            case R.id.iv_add:
                chargeYear++;
                tvCount.setText(chargeYear + "年");
                tvPrice.setText("" + (priceDouble * chargeYear));
                break;
            case R.id.rl_acount:
                resetRB();
                rbAcount.setChecked(!rbAcount.isChecked());
                payment = "1";
                break;
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
                if (TextUtil.isEmpty(payment)) {
                    RxToast.normal("请选择支付方式");
                    return;
                }
                if (TextUtil.isEmpty(title)) {
                    doPay(payment);
                } else {
                    setOrder();
                }

                break;
        }
    }

    private void setOrder() {
        showProgressDialog("支付中。。。");
        Map<String, String> params = new HashMap<>();
        params.put("data[year]", "" + chargeYear);
        params.put("data[payment]", payment);
        OkhttpUtilManager.postNoCacah(this, "User/addUserRole", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
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
                RxToast.normal(msg);
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
                Pingpp.createPayment(PayTypeAcitivity.this, data);
            }

            @Override
            public void onErrorWrong(int code, String msg) {

            }
        });
    }

    private void resetRB() {
        rbAcount.setChecked(false);
        rbWeix.setChecked(false);
        rbAli.setChecked(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
            /* 处理返回值
             * "success" - 支付成功
             * "fail"    - 支付失败
             * "cancel"  - 取消支付
             * "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
             * "unknown" - app进程异常被杀死(一般是低内存状态下,app进程被杀死)
             */
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                ReturnOrderBean bean = new ReturnOrderBean(result.equals("success") ? 1 : 0, errorMsg);
                bean.setPrice("" + tvPrice.getText());
                bean.setErrType(result);
                EventBus.getDefault().post(bean);
                this.finish();
            }
        }
    }
}
