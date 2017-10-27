package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.WeixinInfo;
import com.kzmen.sczxjf.bean.kzbean.EventBusBean;
import com.kzmen.sczxjf.bean.kzbean.UserBean;
import com.kzmen.sczxjf.bean.kzbean.UserMessageBean;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.TextUtil;
import com.kzmen.sczxjf.view.DJEditText;
import com.kzmen.sczxjf.view.PasswordToggleEditText;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vondear.rxtools.RxLogUtils;
import com.vondear.rxtools.view.RxToast;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;


public class LoginActivity extends SuperActivity {
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.et_phone)
    DJEditText etPhone;
    @InjectView(R.id.et_pass)
    PasswordToggleEditText etPass;
    @InjectView(R.id.tv_login)
    TextView tvLogin;
    @InjectView(R.id.tv_forgetpass)
    TextView tv_forgetpass;
    @InjectView(R.id.activity_index)
    LinearLayout activityIndex;
    @InjectView(R.id.ll_login_weix)
    LinearLayout llLoginWeix;
    private BroadcastReceiver receiver;
    private boolean isStartShareReceiver = false;
    private String phone;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "登录");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_login2);
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

    @OnClick({R.id.tv_login, R.id.ll_login_weix, R.id.tv_forgetpass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
               /* startActivity(new Intent(LoginActivity.this, MainTabActivity.class));
                finish();*/
                onLogin();
                break;
            case R.id.ll_login_weix:
                showProgressDialog("跳转微信登录中");
                getToken();
                break;
            case R.id.tv_forgetpass:
                startActivity(new Intent(LoginActivity.this, ForgetPassActivity.class));
                finish();
                break;
        }
    }

    private void onLogin() {
        phone = etPhone.getText().toString();
        pass = etPass.getText().toString();
        if (isAllRight()) {
            showProgressDialog("登录中");
            Map<String, String> params = new HashMap<>();
            params.put("data[phone]", phone);
            params.put("data[pwd]", pass);
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
    }

    private boolean isAllRight() {
        if (TextUtil.isEmpty(phone)) {
            RxToast.normal("电话号码不能为空");
            return false;
        }
        if (TextUtil.isEmpty(pass)) {
            RxToast.normal("密码不能为空");
            return false;
        }
        return true;
    }

    public void getToken() {
        setAccBroadcastReceiver();
        IWXAPI api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "none";
        if (!api.sendReq(req)) {
            Toast.makeText(this, "请确定是否安装微信", Toast.LENGTH_LONG).show();
            dismissProgressDialog();
        }
    }

    private void setAccBroadcastReceiver() {
        isStartShareReceiver = true;
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    dismissProgressDialog();
                    if (TextUtil.isEmpty(intent.getExtras().getString("wrong"))) {
                        loginForWeixin(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.WEIXIN_ACCREDIT);
        registerReceiver(receiver, filter);
    }

    private void loginForWeixin(Intent data) throws JSONException {
        String json = data.getExtras().getString(Constants.WEIXIN_ACCREDIT_KEY);
        final WeixinInfo info = WeixinInfo.parseJson(new JSONObject(json));
        if (info != null) {
            AppContext.getInstance().setWeixinInfo(json);
            AppContext.getInstance().setLoginType("1");
            Map<String, String> params = new HashMap<>();
            params.put("data[weixin]", info.unionid + "");
            params.put("data[openid]", info.openid + "");
            params.put("data[username]", info.nickname + "");
            params.put("data[avatar]", info.headimgurl + "");
            params.put("data[third_country]", info.country + "");
            params.put("data[third_province]", info.province + "");
            params.put("data[third_city]", info.city + "");
            params.put("data[third_sex]", info.sex + "");
            OkhttpUtilManager.postNoCacah(this, "public/weixinLogin", params, new OkhttpUtilResult() {
                @Override
                public void onSuccess(int type, String data) {
                    RxLogUtils.e("tst", "用户数据:::::::" + data);
                    JSONObject object = null;
                    try {
                        object = new JSONObject(data);
                        Gson gson = new Gson();
                        UserBean bean = gson.fromJson(object.getString("data"), UserBean.class);
                        if (TextUtil.isEmpty(bean.getPhone())) {
                            Intent intent = new Intent(LoginActivity.this, BindPhoneAcitivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("userbean", bean);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        } else {
                            onLoginSuccess(bean);
                            getUserInfo();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dismissProgressDialog();
                }

                @Override
                public void onErrorWrong(int code, String msg) {
                    dismissProgressDialog();
                    Toast.makeText(LoginActivity.this, "微信登录失败", Toast.LENGTH_SHORT).show();
                    if (code == 1024) {
                        finish();
                    }
                }
            });
        }
    }

    private void onLoginSuccess(UserBean data) {
        AppContext.getInstance().setUserLogin(data);
        AppContext.getInstance().setPersonageOnLine(true);
        AppContext.getInstance().setFirst();
        dismissProgressDialog();
        if (null != AppContext.maintabeactivity) {
            AppContext.maintabeactivity.setHeadImageNew(data);
        }
        finish();
    }

    private void getUserInfo() {
        OkhttpUtilManager.postNoCacah(this, "User/get_user_info", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
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
