package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.EventBusBean;
import com.kzmen.sczxjf.bean.kzbean.UserBean;
import com.kzmen.sczxjf.bean.kzbean.UserMessageBean;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.TextUtil;
import com.kzmen.sczxjf.view.PasswordToggleEditText;
import com.vondear.rxtools.view.RxToast;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

public class ResetPassActivity extends SuperActivity {

    @InjectView(R.id.et_phone)
    PasswordToggleEditText etPhone;
    @InjectView(R.id.et_pass_confim)
    PasswordToggleEditText etPassConfim;
    @InjectView(R.id.tv_complete)
    TextView tvComplete;

    private String pass;
    private String passConfir;
    private String phone = "";
    private String code = "";
    private String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "重置密码");
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_reset_pass);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            phone = bundle.getString("phone");
            code = bundle.getString("code");
            key = bundle.getString("key");
        }
    }

    @OnClick(R.id.tv_complete)
    public void onViewClicked() {
        pass = etPhone.getText().toString();
        passConfir = etPassConfim.getText().toString();
        if (isAllRight()) {
            reseat();
        }
    }

    public boolean isAllRight() {
        if (TextUtil.isEmpty(pass)) {
            RxToast.normal("密码不能为空");
            return false;
        }
        if (TextUtil.isEmpty(passConfir)) {
            RxToast.normal("请确认密码");
            return false;
        }
        if (!pass.equals(passConfir)) {
            RxToast.normal("两次输入的密码不一致");
            return false;
        }
        return true;
    }

    private void reseat() {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("code", code);
        params.put("key", key);
        params.put("pwd", pass);
        OkhttpUtilManager.postNoCacah(this, "public/resetPwd", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                showProgressDialog("登录中");
                doLogin();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
            }
        });
    }

    private void doLogin() {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("pwd", pass);
        OkhttpUtilManager.postNoCacah(this, "public/login", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    AppContext.getInstance().setLoginType("0");
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    UserBean bean = gson.fromJson(object.getString("data"), UserBean.class);
                    AppContext.getInstance().setUserLogin(bean);
                    AppContext.getInstance().setPersonageOnLine(true);
                    AppContext.getInstance().setCpassword(pass);
                    if (null != AppContext.maintabeactivity) {
                        AppContext.maintabeactivity.setHeadImageNew(bean);
                    }
                    getUserInfo();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissProgressDialog();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                if (code == 1024) {
                    finish();
                }
                RxToast.normal(msg);
                dismissProgressDialog();
            }
        });
    }

    private void getUserInfo() {
        OkhttpUtilManager.postNoCacah(this, "User/get_user_info", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    UserMessageBean bean = gson.fromJson(object.getString("data"), UserMessageBean.class);
                    AppContext.userMessageBean = bean;
                    AppContext.getInstance().setUserMessageBean(bean);
                    EventBus.getDefault().post(new EventBusBean());
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst", "获取用户信息：" + msg);
            }
        });
    }
}
