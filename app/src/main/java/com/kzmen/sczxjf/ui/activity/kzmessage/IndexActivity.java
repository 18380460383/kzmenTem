package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class IndexActivity extends SuperActivity {

    @InjectView(R.id.tv_login)
    TextView tvLogin;
    @InjectView(R.id.tv_register)
    TextView tvRegister;
    @InjectView(R.id.ll_login_weix)
    LinearLayout llLoginWeix;

    private BroadcastReceiver receiver;
    private boolean isStartShareReceiver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onCreateDataForView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        AppContext.setIndexActivity(this);
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_index);
        ButterKnife.inject(this);

    }

    @Override
    public void funFinish(EventBusBean bean) {
        super.funFinish(bean);
        // this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.tv_login, R.id.tv_register, R.id.ll_login_weix})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_login:
                intent = new Intent(IndexActivity.this, LoginActivity.class);
                break;
            case R.id.tv_register:
                intent = new Intent(IndexActivity.this, RegisterActivity.class);
                break;
            case R.id.ll_login_weix:
                showProgressDialog("跳转微信登录中");
                getToken();
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
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
            params.put("weixin", info.unionid + "");
            params.put("openid", info.openid + "");
            params.put("username", info.nickname + "");
            params.put("avatar", info.headimgurl + "");
            params.put("third_country", info.country + "");
            params.put("third_province", info.province + "");
            params.put("third_city", info.city + "");
            params.put("third_sex", info.sex + "");
            OkhttpUtilManager.postNoCacah(this, "public/weixinLogin", params, new OkhttpUtilResult() {
                @Override
                public void onSuccess(int type, String data) {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(data);
                        Gson gson = new Gson();
                        UserBean bean = gson.fromJson(object.getString("data"), UserBean.class);
                        if (TextUtil.isEmpty(bean.getPhone())) {
                            Intent intent = new Intent(IndexActivity.this, BindPhoneAcitivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("userbean", bean);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            onLoginSuccess(bean);
                            startActivity(new Intent(IndexActivity.this, MainTabActivity.class));
                        }
                        getUserInfo();
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorWrong(int code, String msg) {
                    Toast.makeText(IndexActivity.this, "微信登录失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void onLoginSuccess(UserBean bean) {
        AppContext.getInstance().setUserLogin(bean);
        AppContext.getInstance().setPersonageOnLine(true);
        AppContext.getInstance().setFirst();
        dismissProgressDialog();
        Intent intent = new Intent();
        intent.putExtra("loginstate", 1);
        if (null != AppContext.maintabeactivity) {
            AppContext.maintabeactivity.setHeadImageNew(bean);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    private void getUserInfo() {
        OkhttpUtilManager.postNoCacah(this, "User/get_user_info", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst", "获取用户信息：" + data);
                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    UserMessageBean bean = gson.fromJson(object.getString("data"), UserMessageBean.class);
                    Log.e("tst", bean.toString());
                    AppContext.userMessageBean = bean;
                    AppContext.getInstance().setUserMessageBean(bean);
                    //startActivity(new Intent(LoginActivity.this, MainTabActivity.class));
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
