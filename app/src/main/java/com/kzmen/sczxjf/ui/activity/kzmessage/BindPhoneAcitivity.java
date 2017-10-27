package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.EventBusBean;
import com.kzmen.sczxjf.bean.kzbean.UserBean;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.TextUtil;
import com.kzmen.sczxjf.view.PasswordToggleEditText;
import com.vondear.rxtools.RxLogUtils;
import com.vondear.rxtools.RxRegUtils;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogSure;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;


public class BindPhoneAcitivity extends SuperActivity {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.et_phone)
    EditText etPhone;
    @InjectView(R.id.ev_yz)
    EditText evYz;
    @InjectView(R.id.tv_yz)
    TextView tvYz;
    @InjectView(R.id.tv_next)
    TextView tvNext;
    @InjectView(R.id.ll_xieyi)
    LinearLayout llXieyi;
    @InjectView(R.id.et_pass)
    PasswordToggleEditText etPass;
    private String phone;
    private String yzen;
    private String yzenGet;
    private int timeCount = 60 * 1000;
    private UserBean bean;
    private CountDownTimer timer = new CountDownTimer(timeCount, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            tvYz.setText("(" + (millisUntilFinished / 1000) + ")");
            tvYz.setEnabled(false);
        }

        @Override
        public void onFinish() {
            tvYz.setEnabled(true);
            tvYz.setText("获取验证码");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bean = (UserBean) bundle.getSerializable("userbean");
        }
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "绑定手机");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_bind_wxacitivity);
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @OnClick({R.id.tv_yz, R.id.tv_next, R.id.ll_xieyi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_yz:

                getYz();
                break;
            case R.id.tv_next:
                if (isAllRight()) {
                    bindPhone();

                }
                break;
            case R.id.ll_xieyi:
                RxDialogSure dialogSure = new RxDialogSure(BindPhoneAcitivity.this);
                dialogSure.setTitle("用户协议");
                dialogSure.show();
                break;
        }
    }

    public boolean isAllRight() {
        if (TextUtil.isEmpty(etPhone.getText().toString())) {
            RxToast.normal("电话号码不能为空");
            return false;
        }
        if (!RxRegUtils.isMobile(etPhone.getText().toString())) {
            RxToast.normal("电话号码不合法");
            return false;
        }
        if (TextUtil.isEmpty(etPass.getText().toString())) {
            RxToast.normal("密码不能为空");
            return false;
        }
        if (TextUtil.isEmpty(evYz.getText().toString())) {
            RxToast.normal("验证码不能为空");
            return false;
        } else {
            if (!evYz.getText().toString().equals(yzenGet)) {
                RxToast.normal("验证码不正确");
                return false;
            }
        }
        return true;
    }

    private void bindPhone() {
        showProgressDialog("绑定中。。。");
        Map<String, String> params = new HashMap<>();
        params.put("data[phone]", etPhone.getText().toString());
        params.put("data[code]", yzenGet);
        params.put("data[pwd]", etPass.getText().toString());
        params.put("data[uid]", bean.getUid());
        OkhttpUtilManager.postNoCacah(this, "public/blind_phone", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst", data);
                try {
                    if (null != AppContext.indexActivity) {
                        AppContext.indexActivity.finish();
                    }
                    AppContext.resetIndex();
                } catch (Exception e) {
                }
                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    UserBean bean = gson.fromJson(object.getString("data"), UserBean.class);
                    AppContext.getInstance().setUserLogin(bean);
                    AppContext.getInstance().setPersonageOnLine(true);
                    startActivity(new Intent(BindPhoneAcitivity.this, MainTabActivity.class));
                    EventBus.getDefault().post(new EventBusBean());
                    finish();
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

    private void getYz() {
        if (TextUtil.isEmpty(etPhone.getText().toString())) {
            RxToast.normal("电话号码不能为空");
            return;
        }
        timer.start();
        Map<String, String> params = new HashMap<>();
        params.put("data[phone]", etPhone.getText().toString());
        params.put("data[type]", "3");
        OkhttpUtilManager.postNoCacah(this, "public/get_phone_code", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst", data);
                try {
                    JSONObject object = new JSONObject(data);
                    JSONObject object1 = new JSONObject(object.getString("data"));
                    String code = object1.getString("code");
                    //tvYz.setText(code);
                    yzenGet = code;
                } catch (JSONException e) {
                    e.printStackTrace();
                    yzenGet = "-9999";
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxLogUtils.e("tst", msg);
                if (timer != null) {
                    timer.onFinish();
                }
                yzenGet = "-111111";
                tvYz.setText("获取验证码");
            }

        });
    }


}
