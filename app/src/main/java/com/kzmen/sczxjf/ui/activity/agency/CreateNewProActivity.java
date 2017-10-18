package com.kzmen.sczxjf.ui.activity.agency;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.AgOkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.TextUtil;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

public class CreateNewProActivity extends SuperActivity {

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
    @InjectView(R.id.et_pro_name)
    EditText etProName;
    @InjectView(R.id.et_pro_price)
    EditText etProPrice;
    @InjectView(R.id.et_pro_chengben)
    EditText etProChengben;
    @InjectView(R.id.et_first)
    EditText etFirst;
    @InjectView(R.id.et_secod)
    EditText etSecod;
    @InjectView(R.id.et_third)
    EditText etThird;
    @InjectView(R.id.et_expl)
    EditText etExpl;
    @InjectView(R.id.ll_add_new_pro)
    LinearLayout llAddNewPro;
    @InjectView(R.id.activity_create_new_pro)
    RelativeLayout activityCreateNewPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "新项目申请");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_create_new_pro);
    }

    @OnClick({R.id.iv_add, R.id.ll_add_new_pro})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_add:
                showInfoPopu(view);
                break;
            case R.id.ll_add_new_pro:
                if (!isAllRigth()) {
                    return;
                }
                proAdd();
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }
    }

    private boolean isAllRigth() {
        if (TextUtil.isEmpty(etProName.getText().toString())) {
            RxToast.normal("项目名称不能为空");
            return false;
        }
        if (TextUtil.isEmpty(etProPrice.getText().toString())) {
            RxToast.normal("项目费用不能为空");
            return false;
        }
        if (TextUtil.isEmpty(etProChengben.getText().toString())) {
            RxToast.normal("项目成本不能为空");
            return false;
        }
        if (TextUtil.isEmpty(etFirst.getText().toString())) {
            RxToast.normal("一级分润比例");
            return false;
        }
        if (TextUtil.isEmpty(etSecod.getText().toString())) {
            RxToast.normal("二级分润比例");
            return false;
        }
        if (TextUtil.isEmpty(etThird.getText().toString())) {
            RxToast.normal("三级分润比例");
            return false;
        }
        if (TextUtil.isEmpty(etExpl.getText().toString())) {
            RxToast.normal("项目介绍");
            return false;
        }
        return true;
    }

    private void proAdd() {
        showProgressDialog("新增中");
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppContext.getInstance().getUserMessageBean().getUid());
        params.put("contents", etExpl.getText().toString());
        params.put("share_three", etThird.getText().toString());
        params.put("share_two", etSecod.getText().toString());
        params.put("share_one", etFirst.getText().toString());
        params.put("cost", etProChengben.getText().toString());
        params.put("total_fee", etProPrice.getText().toString());
        params.put("name", etProName.getText().toString());
        AgOkhttpUtilManager.postNoCacah(this, "users/partner_project_add", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                } catch (Exception e) {
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
            }
        });
    }
}
