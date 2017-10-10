package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.percent.PercentRelativeLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
import com.kzmen.sczxjf.view.DJEditText;
import com.kzmen.sczxjf.view.PasswordToggleEditText;
import com.vondear.rxtools.RxRegUtils;
import com.vondear.rxtools.view.RxToast;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;


public class RegisterActivity extends SuperActivity {
    
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.et_phone)
    DJEditText etPhone;
    @InjectView(R.id.ev_yz)
    DJEditText evYz;
    @InjectView(R.id.tv_yz)
    TextView tvYz;
    @InjectView(R.id.et_pass)
    PasswordToggleEditText etPass;
    @InjectView(R.id.et_yq)
    DJEditText etYq;
    @InjectView(R.id.tv_register)
    TextView tvRegister;
    @InjectView(R.id.ll_xieyi)
    LinearLayout llXieyi;
    private String yzGet = "";
    private String phone;
    private String yz;
    private String password;
    private String yq;
    private int timeCount = 60 * 1000;
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
        EventBus.getDefault().register(this);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "注册");
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @OnClick({R.id.tv_yz, R.id.tv_register, R.id.ll_xieyi})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_yz:
                if (isPhoneRigth()) {
                    timer.start();
                    getYz();
                }
                break;
            case R.id.tv_register:
                if (isAllRight()) {
                    showProgressDialog("注册中。。。");
                    Map<String, String> params = new HashMap<>();
                    params.put("data[phone]", phone);
                    params.put("data[code]", yz);
                    params.put("data[pwd]", password);
                    params.put("data[invite_code]", yq);

                    OkhttpUtilManager.postNoCacah(RegisterActivity.this, "public/register", params, new OkhttpUtilResult() {
                        @Override
                        public void onSuccess(int type, String data) {
                            //注册成功
                            Log.e("tst", data);
                            try {
                                JSONObject object = new JSONObject(data);
                                Gson gson = new Gson();
                                UserBean bean = gson.fromJson(object.getString("data"), UserBean.class);
                                AppContext.getInstance().setUserLogin(bean);
                                AppContext.getInstance().setPersonageOnLine(true);
                                startActivity(new Intent(RegisterActivity.this, MainTabActivity.class));
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
                break;
            case R.id.ll_xieyi:
                /*RxDialogSure dialogSure = new RxDialogSure(RegisterActivity.this);
                dialogSure.setTitle("");
                dialogSure.setContent("用户协议");
                dialogSure.show();*/
                Intent intent1 = new Intent(RegisterActivity.this, WebAcitivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", "用户协议");
                bundle.putString("url", OkhttpUtilManager.URL_ASK);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    private void getYz() {
        if (TextUtil.isEmpty(phone)) {
            RxToast.normal("电话号码不能为空");
            return;
        }
        timer.start();
        Map<String, String> params = new HashMap<>();
        params.put("data[phone]", phone);
        params.put("data[type]", "1");
        OkhttpUtilManager.postNoCacah(this, "public/get_phone_code", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
               /* if (timer != null) {
                    timer.cancel();
                }*/
                Log.e("tst", data);
                try {
                    JSONObject object = new JSONObject(data);
                    JSONObject object1 = new JSONObject(object.getString("data"));
                    String code = object1.getString("code");
                    //tvYz.setText(code);
                    yzGet = code;
                } catch (JSONException e) {
                    e.printStackTrace();
                    yzGet = "-9999";
                }
                //tvYz.setEnabled(true);
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                if (timer != null) {
                    timer.cancel();
                }
                Log.e("tst", msg);
                yzGet = "-9999";
                //tvYz.setEnabled(true);
            }
        });
    }

    private boolean isPhoneRigth() {
        phone = etPhone.getText().toString();
        if (TextUtil.isEmpty(phone)) {
            RxToast.normal("电话号码不能为空");
            return false;
        }
        if (!RxRegUtils.isMobile(phone)) {
            RxToast.normal("电话号码不合法");
        }
        return true;
    }

    private boolean isAllRight() {
        phone = etPhone.getText().toString();
        yz = evYz.getText().toString();
        password = etPass.getText().toString();
        yq = etYq.getText().toString();
        if (!RxRegUtils.isMobile(phone)) {
            RxToast.normal("手机号码格式不正确");
            return false;
        }
        if (!yz.equals(yzGet)) {
            RxToast.normal("验证码不正确");
            return false;
        }
        if (password.length() < 6 || password.length() > 16) {
            RxToast.normal("密码长度不正确");
            return false;
        }
        if (yq == null || yq.length() == 0) {
            RxToast.normal("邀请码不能为空");
            return false;
        }

        return true;
    }
}
