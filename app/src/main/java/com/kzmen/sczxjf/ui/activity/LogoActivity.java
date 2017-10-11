package com.kzmen.sczxjf.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.AppManager;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.WeixinInfo;
import com.kzmen.sczxjf.bean.kzbean.UserBean;
import com.kzmen.sczxjf.easypermissions.AfterPermissionGranted;
import com.kzmen.sczxjf.easypermissions.EasyPermissions;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.IndexActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.MainTabActivity;
import com.vondear.rxtools.RxLogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wu
 */
public class LogoActivity extends SuperActivity implements EasyPermissions.PermissionCallbacks {
    /*private ELocationlistener listener = new ELocationlistener() {
        @Override
        public void onFinshLocation(EshareLocationInfo info) {
            // setLocation(info);
        }
    };*/

    /**
     * 上传地理位置
     */
   /* private void setLocation(ELocationlistener.EshareLocationInfo info) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", AppContext.getInstance().getPEUser().getUid());
        map.put("token", AppContext.getInstance().getPEUser().getToken());
        map.put("location", info.address);
        map.put("x", info.x);
        map.put("y", info.y);
        map.put("device_type", "Android");
        map.put("device_version", Build.MODEL);
        map.put("app_version", AppUtils.getAppVersionName(this));
        RequestParams requestParams = AppUtils.getParm(map);
        NetworkDownload.bytePost(AppContext.getInstance(), Constants.URL_SET_LOCATING, requestParams, new NetworkDownload.NetworkDownloadCallBackbyte() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                EshareLoger.logI("发送位置信息成功,result = ");
            }

            @Override
            public void onFailure() {
                EshareLoger.logI("发送位置信息失败了: ");
            }
        });
    }
*/
    @Override
    public void onCreateDataForView() {
        AppContext instance = AppContext.getInstance();
        if (TextUtils.isEmpty(instance.getUserLogin().getUid())) {
            instance.setPersonageOnLine(false);
        }
    }

    @Override
    public void setThisContentView() {
        // 防止第三方跳转时出现双实例
        Activity aty = AppManager.getActivity(MainTabActivity.class);
        if (aty != null && !aty.isFinishing()) {
            finish();
        }
        setContentView(R.layout.activity_logo);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                redirectTo();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 跳转到...
     */
    private void redirectTo() {
       /* if (AppContext.getInstance().isFirst()) {
            // 第一次登录
            UIManager.showFirstGuideActivity(this);
            AppContext.getInstance().setFirst();
            finish();
        } else {*/
            /*if (AppContext.getInstance().getPersonageOnLine()){
                //updataToken();
                OnLinelogin();
            } else {
                startActivity(new Intent(LogoActivity.this, IndexActivity.class));
                finish();
                //OnLinelogin();
            }*/
        // recordTask();
        // recordTask();
        recordTask();
        //}
    }

    private static final int RC_RECORD_PERM = 123;

    @AfterPermissionGranted(RC_RECORD_PERM)
    public void recordTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            AppContext.getInstance().setDeviceId();
            startActivity(new Intent(this, MainTabActivity.class));
            finish();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_record),
                    RC_RECORD_PERM, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        AppContext.getInstance().setDeviceId();
        startActivity(new Intent(this, MainTabActivity.class));
        finish();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        AppContext.getInstance().setDeviceId();
        startActivity(new Intent(this, MainTabActivity.class));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    private void updataToken() {
        OkhttpUtilManager.postNoCacah(this, "Public/autoLogin", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    Log.e("tst", data);
                    JSONObject object = new JSONObject(data);
                    JSONObject ob1 = new JSONObject(object.getString("data"));
                    String token = ob1.getString("token");
                    AppContext.getInstance().token = token;
                    AppContext.getInstance().getUserLogin().setToken(token);
                    AppContext.getInstance().setPersonageOnLine(true);
                    startActivity(new Intent(LogoActivity.this, MainTabActivity.class));
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    OnLinelogin();
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst", msg);
                OnLinelogin();
            }
        });
    }

    private void OnLinelogin() {
        AppContext instance = AppContext.getInstance();
        if (instance.getLoginType().equals("1")) {
            try {
                loginForWeixin();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            if (instance.getCpassword() == null) {
                startActivity(new Intent(LogoActivity.this, IndexActivity.class));
                finish();
                return;
            }
            Map<String, String> params = new HashMap<>();
            params.put("data[phone]", instance.getUserLogin().getPhone());
            params.put("data[pwd]", instance.getCpassword());
            OkhttpUtilManager.postNoCacah(this, "public/login", params, new OkhttpUtilResult() {
                @Override
                public void onSuccess(int type, String data) {
                    try {
                        AppContext.getInstance().setLoginType("0");
                        JSONObject object = new JSONObject(data);
                        Gson gson = new Gson();
                        UserBean bean = gson.fromJson(object.getString("data"), UserBean.class);
                        Log.e("tst", bean.toString());
                        AppContext.getInstance().setUserLogin(bean);
                        AppContext.getInstance().setPersonageOnLine(true);
                        startActivity(new Intent(LogoActivity.this, MainTabActivity.class));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        startActivity(new Intent(LogoActivity.this, IndexActivity.class));
                        finish();
                    }
                    dismissProgressDialog();
                }

                @Override
                public void onErrorWrong(int code, String msg) {
                    dismissProgressDialog();
                    startActivity(new Intent(LogoActivity.this, IndexActivity.class));
                    finish();
                }
            });
        }
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    private void loginForWeixin() throws JSONException {
        String json = AppContext.getInstance().getWeixinInfo();
        RxLogUtils.e("tst", "用户数据" + json);
        System.out.println("用户数据" + json);
        final WeixinInfo info = WeixinInfo.parseJson(new JSONObject(json));
        if (info != null) {
            AppContext.getInstance().setWeixinInfo(json);
            AppContext.getInstance().setLoginType("1");
            showProgressDialog("登陆中");
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
                        onLoginSuccess(bean);
                        startActivity(new Intent(LogoActivity.this, MainTabActivity.class));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        startActivity(new Intent(LogoActivity.this, IndexActivity.class));
                        finish();
                    }
                }

                @Override
                public void onErrorWrong(int code, String msg) {
                    dismissProgressDialog();
                    Toast.makeText(LogoActivity.this, "微信登录失败", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LogoActivity.this, IndexActivity.class));
                    finish();
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
        setResult(RESULT_OK, intent);
        finish();
    }

   /* private static final int RC_RECORD_PERM = 123;

    @AfterPermissionGranted(RC_RECORD_PERM)
    public void recordTask() {
        String[] perms = {Manifest.permission.RECORD_AUDIO};
        if (EasyPermissions.hasPermissions(this, perms)) {
            AppContext.getInstance().setDeviceId();
            startActivity(new Intent(this, MainTabActivity.class));
            finish();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_record),
                    RC_RECORD_PERM, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        startActivity(new Intent(this, MainTabActivity.class));
        finish();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }*/
}
