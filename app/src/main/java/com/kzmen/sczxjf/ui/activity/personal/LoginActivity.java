package com.kzmen.sczxjf.ui.activity.personal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.Config;
import com.kzmen.sczxjf.bean.WeixinInfo;
import com.kzmen.sczxjf.bean.user.User_For_pe;
import com.kzmen.sczxjf.net.EnWebUtil;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.ui.activity.AlterPassword;
import com.kzmen.sczxjf.ui.activity.WebviewActivity;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.TLog;
import com.kzmen.sczxjf.utils.JsonUtils;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;



/**
 * 说明：登陆界面
 * note：
 * Created by FuPei
 * on 2015/12/2 at 17:36
 */
public class LoginActivity extends SuperActivity {

    @InjectView(R.id.login_et_phone)
    public EditText et_phone;
    @InjectView(R.id.login_et_passwd)
    public EditText et_passwd;
    @InjectView(R.id.login_btn_login)
    public Button btn_login;
    @InjectView(R.id.login_btn_register)
    public Button btn_register;
    @InjectView(R.id.login_tv_forget)
    public TextView tv_forget;
    @InjectView(R.id.login_iv_login_wei)
    public ImageView iv_login_wei;
    @InjectView(R.id.login_tv_service)
    public TextView tv_service;
    @InjectView(R.id.login_tv_privacy)
    public TextView tv_privacy;
    @InjectView(R.id.title_name)
    public TextView tv_title;
    @InjectView(R.id.title_back)
    public ImageView iv_back;
    @InjectView(R.id.bangding)
    public RelativeLayout bangding;
    private String  regex = "[1]{1}[0-9]{1}[0-9]{9}";
    private BroadcastReceiver receiver;
    private boolean isStartShareReceiver=false;

    @Override
    public void onCreateDataForView() {
        AppContext.getInstance().setOneActivity(this);
        initView();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_login);
    }

    private void initView() {
        String phon=AppContext.getInstance().getPEUser().getOn_phone();
        if(!TextUtils.isEmpty(phon)||!"null".equals(phon)){
            System.out.println("电话号码"+phon);
            et_phone.setText(phon);
        }
        iv_back.setVisibility(ImageView.GONE);
        tv_title.setText("登录");
    }


    @OnClick({R.id.login_et_phone, R.id.login_et_passwd, R.id.login_btn_login, R.id.login_btn_register, R.id.login_tv_forget,
            R.id.login_iv_login_wei, R.id.login_tv_service, R.id.login_tv_privacy})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_login:
                login();
                break;
            case R.id.login_btn_register:
                startActivity(new Intent(this, PersonageRegister.class));
                break;
            case R.id.login_tv_forget:
                Intent intent = new Intent(LoginActivity.this, AlterPassword.class);
                intent.putExtra("type", 0);
                startActivity(intent);
                break;
            case R.id.login_iv_login_wei:
                 showProgressDialog("跳转微信登录中");
                    getToken();
            break;
            case R.id.login_tv_service:
                getUrl(1);
                break;
            case R.id.login_tv_privacy:
                getUrl(2);
                break;
        }
    }


    private void login() {
        String userName = et_phone.getText().toString();
        String password = et_passwd.getText().toString();

        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
        }else{
            showProgressDialog("登陆中");
            RequestParams params = new RequestParams();
            params.put("on_phone",userName);
            params.put("on_pwd", password);
            EnWebUtil.getInstance().post(this, new String[]{"OwnAccount", "loginApp"}, params, new EnWebUtil.AesListener2() {
                @Override
                public void onSuccess(String errorCode, String errorMsg, String data) {
                    dismissProgressDialog();
                    if ("0".equals(errorCode)) {
                        try {
                            User_For_pe bean = JsonUtils.getBean(new JSONObject(data), User_For_pe.class);
                            System.out.println("用户数据"+bean);
                            onLoginSuccess(bean);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFail(String result) {
                    dismissProgressDialog();
                    Toast.makeText(LoginActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void loginForWeixin(Intent data) throws JSONException {
        String json  = data.getExtras().getString(Constants.WEIXIN_ACCREDIT_KEY);
        System.out.println("用户数据"+json);
        final WeixinInfo info = WeixinInfo.parseJson(new JSONObject(json));
        if (info != null) {
            showProgressDialog("登陆中");
            RequestParams requestParams1 = new RequestParams();
            requestParams1.put("weixin[platform]", "android");
            requestParams1.put("weixin[unionid]", info.unionid);
            requestParams1.put("weixin[imageurl]", info.headimgurl);
            requestParams1.put("weixin[username]", info.nickname);
            requestParams1.put("weixin[city]", info.city + "");
            requestParams1.put("weixin[country]", info.country + "");
            requestParams1.put("weixin[sex]", info.sex + "");
            requestParams1.put("weixin[province]", info.province + "");
            requestParams1.put("weixin[source]", AppContext.getInstance().getChannel());

            //requestParams1.put("weixin", map.toString());
            //requestParams1.put("source", AppContext.getInstance().getChannel());
            EnWebUtil.getInstance().post(this, new String[]{"OwnAccount", "loginAppByWeixin"}, requestParams1, new EnWebUtil.AesListener2() {
                @Override
                public void onSuccess(String errorCode, String errorMsg, String data) {
                    if("0".equals(errorCode)){
                        try {
                            User_For_pe bean = JsonUtils.getBean(new JSONObject(data), User_For_pe.class);
                            TLog.error("用户数据"+data);
                            onLoginSuccess(bean);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this,errorMsg,Toast.LENGTH_SHORT).show();
                    }
                    dismissProgressDialog();
                }
                @Override
                public void onFail(String result) {
                    Toast.makeText(LoginActivity.this,"微信登陆失败",Toast.LENGTH_SHORT).show();
                    dismissProgressDialog();
                }
            });
        }
    }
    private void onLoginSuccess(User_For_pe data) {
        AppContext.getInstance().setPEUser(data);
        AppContext.getInstance().setPersonageOnLine(true);
        AppContext.getInstance().setFirst();
        dismissProgressDialog();
        Intent intent = new Intent();
        intent.putExtra("loginstate", 1);
        setResult(RESULT_OK, intent);

        finish();
    }
    public void getToken() {
        setAccBroadcastReceiver();
        IWXAPI api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "none";
        if(!api.sendReq(req)){
            Toast.makeText(this,"请确定是否安装微信",Toast.LENGTH_LONG).show();
            dismissProgressDialog();
        }
    }

    public void getUrl(final int i) {
        showProgressDialog(null);
        Config appConfig = AppContext.getInstance().getAppConfig();
        if(null!= appConfig){
            startWeb(i, appConfig);
        }else {

            NetworkDownload.byteGet(this, Constants.SERVER_API_CONFIG, null, new NetworkDownload.NetworkDownloadCallBackbyte() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                    try {
                        Config config = JsonUtils.getConfig(new JSONObject(new String(bytes)));
                        startWeb(i, config);
                        AppContext.getInstance().setAppConfig(config);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure() {

                }
            });
        }

    }

    private void startWeb(int i, Config appConfig) {
        System.out.println(appConfig);
        dismissProgressDialog();
        Intent intent = new Intent(this, WebviewActivity.class);
        switch (i){
            case 1:
                intent.putExtra("url", appConfig.getPromap().get("fuwu"));
                intent.putExtra("title","服务条款");
                startActivity(intent);
                break;
            case 2:
                intent.putExtra("url", appConfig.getPromap().get("yinsi"));
                intent.putExtra("title","隐私条款");
                startActivity(intent);
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        AppContext.getInstance().setOldinstance(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkDownload.stopRequest(this);
        if(isStartShareReceiver) {
            unregisterReceiver(receiver);
            isStartShareReceiver=false;
        }
    }
    private void setAccBroadcastReceiver(){
        isStartShareReceiver=true;
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    loginForWeixin(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.WEIXIN_ACCREDIT);
        registerReceiver(receiver, filter);
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @Override
    public boolean isCanExit() {
        return false;
    }
}
