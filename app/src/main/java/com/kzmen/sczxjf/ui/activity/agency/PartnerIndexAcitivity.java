package com.kzmen.sczxjf.ui.activity.agency;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.AgOkhttpUtilManager;
import com.kzmen.sczxjf.popuwidow.MsgShowPopuwindow;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.view.CircleImageViewBorder;
import com.kzmen.sczxjf.view.MyListView;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 合伙人首页
 */
public class PartnerIndexAcitivity extends SuperActivity {

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
    @InjectView(R.id.tv_par_all)
    TextView tvParAll;
    @InjectView(R.id.ll_par_all)
    LinearLayout llParAll;
    @InjectView(R.id.tv_today_par_add)
    TextView tvTodayParAdd;
    @InjectView(R.id.ll_par_today)
    LinearLayout llParToday;
    @InjectView(R.id.iv_percent)
    ImageView ivPercent;
    @InjectView(R.id.lv_leader_list)
    MyListView lvLeaderList;
    @InjectView(R.id.ll_add_new_pro)
    LinearLayout llAddNewPro;
    @InjectView(R.id.activity_partner_index_acitivity)
    RelativeLayout activityPartnerIndexAcitivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "合伙人");
        Map<String, String> params = new HashMap<>();
        params.put("member_id", "" + AppContext.getInstance().getUserMessageBean().getUid());
        AgOkhttpUtilManager.postNoCacah(this, "users/partner_statistics", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {

            }

            @Override
            public void onErrorWrong(int code, String msg) {

            }
        });
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_partner_index_acitivity);
    }

    @OnClick({R.id.iv_add, R.id.ll_msg, R.id.ll_all_count, R.id.ll_today_count, R.id.ll_par_all, R.id.ll_par_today, R.id.iv_percent, R.id.ll_add_new_pro})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_add:
                showInfoPopu(view);
                break;
            case R.id.ll_msg:
                intent = new Intent(PartnerIndexAcitivity.this, MsgListActivity.class);
                break;
            case R.id.ll_all_count:
                intent = new Intent(PartnerIndexAcitivity.this, MyMoneyDetailActivity.class);
                break;
            case R.id.ll_today_count:
                intent = new Intent(PartnerIndexAcitivity.this, MyMoneyDetailActivity.class);
                break;
            case R.id.ll_par_all:
                break;
            case R.id.ll_par_today:
                break;
            case R.id.iv_percent:
                showMsgPopu(view, "内容");
                break;
            case R.id.ll_add_new_pro:
                intent = new Intent(PartnerIndexAcitivity.this, CreateNewProActivity.class);
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }
    }

    private MsgShowPopuwindow msgShowPopuwindow;
    private WindowManager.LayoutParams params;

    public void showMsgPopu(View view, String msg) {
        msgShowPopuwindow = new MsgShowPopuwindow(this, msg);
        msgShowPopuwindow.showAtLocation(view, Gravity.CENTER | Gravity.CENTER_VERTICAL, 0, 0);
        params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        msgShowPopuwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }
}
