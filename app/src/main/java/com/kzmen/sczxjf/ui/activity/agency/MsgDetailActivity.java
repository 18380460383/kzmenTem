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
import com.kzmen.sczxjf.bean.agent.ParProDEtailbean;
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
    private String type = "";
    private MsgDetailBean msgDetailBean;
    private ParProDEtailbean parProDEtailbean;
    private String proId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "我的邮件");
        if (!TextUtil.isEmpty(type) && type.equals("msg")) {
            getMsgDetail();
        } else if (!TextUtil.isEmpty(type) && type.equals("pro")) {
            getProDetail();
        }
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_msg_detail2);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            member_message_no = bundle.getString("id");
            proId = bundle.getString("proId");
            type = bundle.getString("type");
        }
    }

    private void getProDetail() {
        showProgressDialog("加载中");
        Map<String, String> params = new HashMap<>();
        params.put("partner_project_id", proId);
        params.put("member_id", AppContext.getInstance().getUserMessageBean().getUid());
        AgOkhttpUtilManager.postNoCacah(this, "users/partner_project_detail", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    Gson gson = new Gson();
                    parProDEtailbean = gson.fromJson(data, ParProDEtailbean.class);
                    if (null != parProDEtailbean) {
                        initViewPro();
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

    private void initViewPro() {
        if (null != parProDEtailbean) {
            tvProName.setText(parProDEtailbean.getPartner_project_name());
            tvProPrice.setText(parProDEtailbean.getTotal_fee());
            tvProCount.setText(parProDEtailbean.getJoin_count());
            tvContent.setText(parProDEtailbean.getContents());
            tvJoin.setVisibility(View.GONE);
            llIsjoin.setVisibility(View.VISIBLE);
        }
    }

    private void getMsgDetail() {
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
                        proId = msgDetailBean.getPartner_project_id();
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
                joinPro();
                break;
            case R.id.tv_show_par:
                intent = new Intent(MsgDetailActivity.this, PartnerListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("partner_project_id", proId);
                intent.putExtras(bundle);
                break;
            case R.id.tv_invi_par:
                if (null != parProDEtailbean && parProDEtailbean.getStatus_e().equals("30")) {
                    showEditPopu(view, 2, proId, "", "");
                } else if (null != parProDEtailbean) {
                    RxToast.normal(parProDEtailbean.getStatus_name());
                } else if (null != msgDetailBean) {
                    editPopuwindow.setProid(msgDetailBean.getPartner_project_id());
                    showEditPopu(view, 2, msgDetailBean.getPartner_project_id(), "", "");
                }
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }
    }

    private void joinPro() {
        showProgressDialog("参加中");
        Map<String, String> params = new HashMap<>();
        params.put("partner_project_id", proId);
        params.put("member_id", AppContext.getInstance().getUserMessageBean().getUid());
        AgOkhttpUtilManager.postNoCacah(this, "users/join_partner_project", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                /*try {
                    Gson gson = new Gson();
                    msgDetailBean = gson.fromJson(data, MsgDetailBean.class);
                    if (null != msgDetailBean) {
                        initView();
                    }
                } catch (Exception e) {
                }*/
                dismissProgressDialog();
                Intent intent = new Intent(MsgDetailActivity.this, MsgJoinActivity.class);
                startActivity(intent);
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
                dismissProgressDialog();
            }
        });
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
