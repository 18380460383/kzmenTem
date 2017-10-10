package com.kzmen.sczxjf.ui.activity.personal;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.returned.OrderForm;
import com.kzmen.sczxjf.control.ScreenControl;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.AppUtils;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.loopj.android.http.RequestParams;
import com.pingplusplus.android.PaymentActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/1/27.
 */
public class ShopOrderActivity extends SuperActivity {
    @InjectView(R.id.shop_all_money)
    TextView shopAllMoney;
    @InjectView(R.id.textview1)
    TextView textview1;
    @InjectView(R.id.textview_banlance)
    TextView textviewBanlance;
    @InjectView(R.id.image_balance)
    ImageView imageBalance;
    @InjectView(R.id.imageView2)
    ImageView imageView2;
    @InjectView(R.id.textview2)
    TextView textview2;
    @InjectView(R.id.image_weixin)
    ImageView imageWeixin;
    @InjectView(R.id.imageView3)
    ImageView imageView3;
    @InjectView(R.id.textview3)
    TextView textview3;
    @InjectView(R.id.image_alipay)
    ImageView imageAlipay;
    @InjectView(R.id.order_ok)
    Button orderOk;
    @InjectView(R.id.banlance_rl)
    RelativeLayout banlanceRl;
    private OrderForm orderForm;
    private String order;
    private String payment="0";
    private String id;
    private final int REQUEST_CODE_SHOP_PAYMENT=11;
    public static final String CLASS_SOURCE="cla";
    private String stringTAG;

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_shop_order);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.shop_order_title,"在线支付");
        getData();
    }
    @OnClick({R.id.image_balance,R.id.image_weixin,R.id.image_alipay,R.id.order_ok})
    public void onClic(View view){
        switch (view.getId()){
            case R.id.image_balance:
                payment="1";
                imageBalance.setImageResource(R.drawable.option_ok);
                imageWeixin.setImageResource(R.drawable.option);
                imageAlipay.setImageResource(R.drawable.option);
                break;
            case R.id.image_weixin:
                payment="3";
                imageBalance.setImageResource(R.drawable.option);
                imageWeixin.setImageResource(R.drawable.option_ok);
                imageAlipay.setImageResource(R.drawable.option);
                break;
            case R.id.image_alipay:
                payment="2";
                imageBalance.setImageResource(R.drawable.option);
                imageWeixin.setImageResource(R.drawable.option);
                imageAlipay.setImageResource(R.drawable.option_ok);
                break;
            case R.id.order_ok:
                if(payment.equals("0")){
                    Toast.makeText(ShopOrderActivity.this, "请选择支付方式", Toast.LENGTH_SHORT).show();
                } else if(payment.equals("1")){
                    showPopupWindow(view);
                }else{
                    payment();
                }
                break;
        }
    }
    public void getData() {
        Intent intent = getIntent();
        if(intent!=null){
            Bundle extras = intent.getExtras();
            if(extras!=null){
                /*orderForm = (OrderForm) extras.getSerializable(BuyShopSetActivity.ORDER);
                stringTAG = extras.getString(CLASS_SOURCE);
                setData(orderForm);*/
            }
        }
    }

    private void setData(OrderForm orderForm) {
        if(orderForm==null){
            return;
        }
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数
            df.setRoundingMode(RoundingMode.UP);
            double money = orderForm.getMoney();
            shopAllMoney.setText(df.format(money / 100));
            double balance = orderForm.getBalance();
            textviewBanlance.setText("￥(" + df.format(balance / 100)+")");
            order = orderForm.getOrder();
            if(balance>money){
                banlanceRl.setVisibility(View.VISIBLE);
            }
    }
    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.pop_window_play, null);
        ScreenControl s=new ScreenControl();
        contentView.setLayoutParams(new LinearLayout.LayoutParams(s.getscreenWide(), s.getscreenHigh()));
        // 设置按钮的点击事件
        Button button = (Button) contentView.findViewById(R.id.play_shop_submit);
        ImageView imageView = (ImageView) contentView.findViewById(R.id.cancel_play);
        TextView textViewName= (TextView) contentView.findViewById(R.id.shop_name);
        TextView textViewMoney= (TextView) contentView.findViewById(R.id.shop_play_money);
        TextView textViewBalan= (TextView) contentView.findViewById(R.id.banlance_money);
        textViewName.setText(orderForm.getTitle());
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        df.setRoundingMode(RoundingMode.UP);
        double money = orderForm.getMoney();
        textViewMoney.setText("￥"+df.format(money / 100));
        textViewBalan.setText("￥" + df.format(orderForm.getBalance()/100));
        final Dialog dialog=new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(contentView);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                payment();
                dialog.dismiss();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();

        window.setLayout(s.getscreenWide(), s.getscreenHigh() / 5 * 3);
        window.setGravity(Gravity.BOTTOM);
        dialog.show();

    }

    private void payment(){
        Map<String,String> map=new HashMap<>();
        map.put("uid", AppContext.getInstance().getPEUser().getUid());
        map.put("payment", payment);
        map.put("order", order);
        RequestParams params = AppUtils.getParm(map);
        showProgressDialog(null);
        NetworkDownload.jsonPostForCode1(this, Constants.URL_POST_PLAYMENT, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                dismissProgressDialog();
                if ("1".equals(payment)) {
                    JSONObject data = jsonObject.optJSONObject("data");
                    if (data != null) {
                        String s = data.optString("id");
                        id = s;
                        refreshBalance();
                        if("DetailEXActivity".equals(stringTAG)){
                            finish();
                        }else if("ExchangeActivity".equals(stringTAG)){
                            Intent data1 = new Intent(ShopOrderActivity.this,ExchangeActivity.class);
                            setResult(RESULT_OK, data1);
                            finish();
                        }else{
                            showHintDialog();
                        }

                    }
                } else if ("2".equals(payment) || "3".equals(payment)) {
                    Intent intent = new Intent();
                    String packageName = getPackageName();
                    ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
                    intent.setComponent(componentName);
                    JSONObject data = jsonObject.optJSONObject("data");
                    if(data!=null){

                        String s = data.optString("id");
                        id = s;
                        String charge = data.optString("charge");
                        if(charge!=null){
                            intent.putExtra(PaymentActivity.EXTRA_CHARGE, charge);
                            startActivityForResult(intent, REQUEST_CODE_SHOP_PAYMENT);
                        }else{
                            Toast.makeText(ShopOrderActivity.this, "付款失败", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(ShopOrderActivity.this, "付款失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure() {
                dismissProgressDialog();
            }
        });
    }

    private void showHintDialog() {
        final Dialog dialog=new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_shop_play_hint, null);
       Button encounterProblem= (Button) inflate.findViewById(R.id.back_shop_home);
        encounterProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPlayOkBroadcast();
                dialog.dismiss();
                finish();
            }
        });
        Button playOk= (Button) inflate.findViewById(R.id.look_detial);
        playOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopOrderActivity.this, DetailEXActivity.class);
                intent.putExtra(DetailEXActivity.EXTRA_ID, id);
                ShopOrderActivity.this.startActivity(intent);
                sendPlayOkBroadcast();
                dialog.dismiss();
                finish();
            }
        });
        dialog.setContentView(inflate);
        Window window = dialog.getWindow();
        ScreenControl s=new ScreenControl();
        window.setLayout(s.getscreenWide() / 6 * 5, s.getscreenHigh() / 3);
        dialog.show();

    }

    private void sendPlayOkBroadcast() {
        Intent intent1=new Intent();
        intent1.setAction(Constants.SHOP_PLAY_OK);
        sendBroadcast(intent1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == REQUEST_CODE_SHOP_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                if("success".equals(result)){
                    refreshBalance();
                    showHintDialog();
                }else if("fail".equals(result)){
                    Toast.makeText(ShopOrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                }else if("cancel".equals(result)){
                    Toast.makeText(ShopOrderActivity.this, "取消了支付", Toast.LENGTH_SHORT).show();
                }else if("invalid".equals(result)){
                    Toast.makeText(ShopOrderActivity.this, "支付插件没有安装", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
    private void refreshBalance(){
        Intent intent = new Intent();
        intent.setAction(Constants.FRAGMENT_MONEY);
        sendBroadcast(intent);
    }

}
