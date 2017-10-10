package com.kzmen.sczxjf.ui.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.ReturnOrderBean;
import com.kzmen.sczxjf.bean.kzbean.SpecialPowerBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.view.MyListView;
import com.vondear.rxtools.RxLogUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;

public class SpecialPowerActivity extends SuperActivity {

    @InjectView(R.id.iv_head)
    ImageView ivHead;
    @InjectView(R.id.iv_level_sign)
    ImageView ivLevelSign;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_level)
    TextView tvLevel;
    @InjectView(R.id.tv_date)
    TextView tvDate;
    @InjectView(R.id.tv_level_)
    TextView tvLevel_;
    @InjectView(R.id.ll_have_level)
    LinearLayout llHaveLevel;
    @InjectView(R.id.lv_power)
    MyListView lvPower;
    @InjectView(R.id.tv_charge_vip)
    TextView tvChargeVip;
    @InjectView(R.id.tv_money)
    TextView tv_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void funOrder(ReturnOrderBean bean) {
        super.funOrder(bean);
        if (bean.getType() == 1) {
            initData();
        }
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "我的特权");
        initData();
    }

    private SpecialPowerBean specialPowerBean;
    private CommonAdapter<String> listAdapter;

    private void initData() {
        showProgressDialog("获取信息中");
        OkhttpUtilManager.postNoCacah(this, "User/getUserRole", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                RxLogUtils.e("tst",data);
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    specialPowerBean = gson.fromJson(object.getString("data"), SpecialPowerBean.class);
                    initView();
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

    private void initView() {
        if (specialPowerBean != null) {
            RxLogUtils.e("tst",specialPowerBean.getAvatar());
            Glide.with(this).load(specialPowerBean.getAvatar()).placeholder(R.drawable.icon_user_normal).into(ivHead);
            if (specialPowerBean.getRole().equals("1")) {
                ivLevelSign.setBackgroundResource(R.drawable.icon_vip);
                tvDate.setText(specialPowerBean.getRole_date());
                llHaveLevel.setVisibility(View.GONE);
            } else {
                tvLevel.setText("普通会员");
                tvDate.setVisibility(View.GONE);
            }
            tvName.setText(specialPowerBean.getUsername());
            listAdapter = new CommonAdapter<String>(this, R.layout.kz_special_list_item, specialPowerBean.getContent()) {
                @Override
                protected void convert(ViewHolder viewHolder, String item, int position) {
                    viewHolder.setText(R.id.tv_item_name, item);
                }
            };
            lvPower.setAdapter(listAdapter);
            tv_money.setText("￥" + specialPowerBean.getRole_money() + "/年");
        }
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_special_power);
    }

    @OnClick(R.id.tv_charge_vip)
    public void onViewClicked() {
        Intent intent = new Intent(SpecialPowerActivity.this, PayTypeAcitivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("price", specialPowerBean.getRole_money());
        bundle.putString("title", "会员充值");
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
