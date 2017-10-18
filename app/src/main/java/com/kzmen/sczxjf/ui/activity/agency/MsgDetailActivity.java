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

import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.agent.MsgDetailBean;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.AgOkhttpUtilManager;
import com.kzmen.sczxjf.popuwidow.EditPopuwindow;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.TextUtil;
import com.vondear.rxtools.view.RxToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

public class MsgDetailActivity extends SuperActivity {

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
    @InjectView(R.id.tv_pro_name)
    TextView tvProName;
    @InjectView(R.id.tv_pro_price)
    TextView tvProPrice;
    @InjectView(R.id.tv_pro_count)
    TextView tvProCount;
    @InjectView(R.id.tv_content)
    TextView tvContent;
    @InjectView(R.id.tv_join)
    TextView tvJoin;
    @InjectView(R.id.tv_show_par)
    TextView tvShowPar;
    @InjectView(R.id.tv_invi_par)
    TextView tvInviPar;
    @InjectView(R.id.ll_isjoin)
    LinearLayout llIsjoin;
    @InjectView(R.id.activity_partner_index_acitivity)
    RelativeLayout activityPartnerIndexAcitivity;
    private String member_message_no = "";
    private MsgDetailBean msgDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "我的邮件");
        showProgressDialog("加载中");
        Map<String, String> params = new HashMap<>();
        params.put("member_message_no", member_message_no);
        params.put("member_id", AppContext.getInstance().getUserMessageBean().getUid());
        AgOkhttpUtilManager.postNoCacah(this, "users/member_message_detail", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    Gson gson = new Gson();
                    msgDetailBean = gson.fromJson(data, MsgDetailBean.class);
                    if (null != msgDetailBean) {
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
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_msg_detail2);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            member_message_no = bundle.getString("id");
        }
    }

    private void initView() {
        if (null != msgDetailBean) {
            tvProName.setText(msgDetailBean.getPartner_project_name());
            tvProPrice.setText(msgDetailBean.getTotal_fee());
            tvProCount.setText(msgDetailBean.getJoin_count());
            tvContent.setText(msgDetailBean.getContents());
            if (!TextUtil.isEmpty(msgDetailBean.getIs_join()) && msgDetailBean.getIs_join().equals("1")) {
                llIsjoin.setVisibility(View.GONE);
            } else {
                tvJoin.setVisibility(View.GONE);
                llIsjoin.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick({R.id.iv_add, R.id.tv_join, R.id.tv_show_par, R.id.tv_invi_par})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_add:
                showInfoPopu(view);
                break;
            case R.id.tv_join:
                intent = new Intent(MsgDetailActivity.this, MsgJoinActivity.class);
                break;
            case R.id.tv_show_par:
                break;
            case R.id.tv_invi_par:
                showEditPopu(view, 1, "", "", "");
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }
    }

    private EditPopuwindow editPopuwindow;
    private WindowManager.LayoutParams params;

    public void showEditPopu(View view, int opType, String id, String name, String percent) {
        editPopuwindow = new EditPopuwindow(this, opType, id, name, percent);
        editPopuwindow.showAtLocation(view, Gravity.CENTER | Gravity.CENTER_VERTICAL, 0, 0);
        params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        editPopuwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }
}
