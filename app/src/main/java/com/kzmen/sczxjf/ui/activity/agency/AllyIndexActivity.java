package com.kzmen.sczxjf.ui.activity.agency;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.agent.AllyIndexBean;
import com.kzmen.sczxjf.bean.kzbean.FriendInvitedBean;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.AgOkhttpUtilManager;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.StringUtils;
import com.kzmen.sczxjf.view.CircleImageViewBorder;
import com.vondear.rxtools.RxLogUtils;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 盟友首页
 */
public class AllyIndexActivity extends SuperActivity {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.iv_add)
    ImageView ivAdd;
    @InjectView(R.id.iv_user_head)
    CircleImageViewBorder ivUserHead;
    @InjectView(R.id.tv_user_name)
    TextView tvUserName;
    @InjectView(R.id.tv_msg_count)
    TextView tvMsgCount;
    @InjectView(R.id.ll_msg)
    LinearLayout llMsg;
    @InjectView(R.id.tv_all_earing)
    TextView tvAllEaring;
    @InjectView(R.id.ll_all_count)
    LinearLayout llAllCount;
    @InjectView(R.id.tv_today_earing)
    TextView tvTodayEaring;
    @InjectView(R.id.ll_today_count)
    LinearLayout llTodayCount;
    @InjectView(R.id.tv_today_add)
    TextView tvTodayAdd;
    @InjectView(R.id.ll_green)
    LinearLayout llGreen;
    @InjectView(R.id.tv_pay)
    TextView tvPay;
    @InjectView(R.id.tv_qr)
    TextView tv_qr;
    @InjectView(R.id.tv_member_count)
    TextView tv_member_count;
    @InjectView(R.id.ll_blue)
    LinearLayout llBlue;
    @InjectView(R.id.tv_no_pay)
    TextView tvNoPay;
    @InjectView(R.id.ll_yellow)
    LinearLayout llYellow;
    @InjectView(R.id.ll_friend_count)
    LinearLayout llFriendCount;
    @InjectView(R.id.iv_qr)
    ImageView ivQr;
    @InjectView(R.id.activity_partner_index_acitivity)
    LinearLayout activityPartnerIndexAcitivity;
    private AllyIndexBean allyIndexBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "盟友");
        showProgressDialog("加载中");
        Map<String, String> params = new HashMap<>();
        params.put("member_id", "" + AppContext.getInstance().getUserMessageBean().getUid());
        AgOkhttpUtilManager.postNoCacah(this, "users/member_statistics", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    Gson gson = new Gson();
                    allyIndexBean = gson.fromJson(data, AllyIndexBean.class);
                    if (null != allyIndexBean) {
                        initView();
                    }
                } catch (Exception e) {

                }
                dismissProgressDialog();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
                dismissProgressDialog();
            }
        });
        initData();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_ally_index);
    }

    private void initView() {
        if (null != allyIndexBean) {
            tvAllEaring.setText(StringUtils.addComma("" + allyIndexBean.getTotal_income()));
            tvTodayEaring.setText(StringUtils.addComma("" + allyIndexBean.getToday_income()));
            tvNoPay.setText(allyIndexBean.getNot_pay_member_count());
            tvPay.setText(allyIndexBean.getPay_member_count());
            tvTodayAdd.setText(allyIndexBean.getToday_new_member());
            tv_member_count.setText(allyIndexBean.getMember_count());
            tvUserName.setText(AppContext.getInstance().getUserMessageBean().getUsername());
            tv_qr.setText("邀请码:" + AppContext.getInstance().getUserMessageBean().getInvite_code());
            Glide.with(this).load(AppContext.getInstance().getUserMessageBean().getAvatar()).into(ivUserHead);
            //Glide.with(this).load(AppContext.getInstance().getUserMessageBean().getAvatar()).into(ivQr);
        }
    }

    private FriendInvitedBean friendInvitedBean;

    private void initData() {
        Map<String, String> params = new HashMap<>();
        try {
            File file1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile() + File.separator + "kzmen" + File.separator + "qr.png");
            if (!file1.exists()) {
                //params.put("data[make]", "1");
                OkhttpUtilManager.postNoCacah(this, "User/getUserInviteCode", params, new OkhttpUtilResult() {
                    @Override
                    public void onSuccess(int type, String data) {
                        RxLogUtils.e("tst", data);
                        JSONObject object = null;
                        try {
                            object = new JSONObject(data);
                            Gson gson = new Gson();
                            friendInvitedBean = gson.fromJson(object.getString("data"), FriendInvitedBean.class);
                            if (null != friendInvitedBean) {
                                Glide.with(AllyIndexActivity.this).load(friendInvitedBean.getImage()).into(ivQr);
                            }
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onErrorWrong(int code, String msg) {
                        RxLogUtils.e("tst", msg);
                    }
                });
            } else {
                Glide.with(this).load(file1).into(ivQr);
            }
        } catch (Exception e) {
        }

    }

    @OnClick({R.id.iv_add, R.id.ll_msg, R.id.ll_all_count, R.id.ll_today_count, R.id.ll_green, R.id.ll_blue, R.id.ll_yellow, R.id.ll_friend_count})
    public void onViewClicked(View view) {
        Intent intent = null;
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_add:
                showInfoPopu(view);
                break;
            case R.id.ll_msg:
                intent = new Intent(AllyIndexActivity.this, MsgListActivity.class);
                break;
            case R.id.ll_all_count:
                intent = new Intent(AllyIndexActivity.this, MyMoneyDetailActivity.class);
                bundle.putString("distrubution_type", "10");
                intent.putExtras(bundle);
                break;
            case R.id.ll_today_count:
                intent = new Intent(AllyIndexActivity.this, MyMoneyDetailActivity.class);
                bundle.putString("distrubution_type", "10");
                intent.putExtras(bundle);
                break;
            case R.id.ll_green://add
                intent = new Intent(AllyIndexActivity.this, MyAllyListActivity.class);
                bundle.putInt("pos", 0);
                intent.putExtras(bundle);
                break;
            case R.id.ll_blue://付款
                intent = new Intent(AllyIndexActivity.this, MyAllyListActivity.class);
                bundle.putInt("pos", 1);
                intent.putExtras(bundle);
                break;
            case R.id.ll_yellow://未付款
                intent = new Intent(AllyIndexActivity.this, MyAllyListActivity.class);
                bundle.putInt("pos", 2);
                intent.putExtras(bundle);
                break;
            case R.id.ll_friend_count:
                intent = new Intent(AllyIndexActivity.this, MyAllyListActivity.class);
                bundle.putInt("pos", 0);
                intent.putExtras(bundle);
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }
    }


}
