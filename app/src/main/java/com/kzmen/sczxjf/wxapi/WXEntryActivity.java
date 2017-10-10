package com.kzmen.sczxjf.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.bean.TokenBean;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.util.EshareLoger;
import com.kzmen.sczxjf.util.TLog;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vondear.rxtools.RxLogUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;


    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///setContentView(R.layout.entry);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
       // api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        initWeixin();
    }
    private void initWeixin() {
        if(api == null) {
            api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        }
        api.registerApp(Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        RxLogUtils.e("weixin","errCode  :::"+baseResp.errCode+"  errStr  :::"+baseResp.errStr+"  transaction  :::"+baseResp.transaction+"  openId  :::"+baseResp.openId+"  ");
        if(baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            // 认证登录
            if(baseResp.errCode == BaseResp.ErrCode.ERR_OK) {
                // 认证成功
                TLog.log(" id " + ((SendAuth.Resp) baseResp).code
                        + ((SendAuth.Resp) baseResp).country + ((SendAuth.Resp) baseResp).lang);
                AppContext.getInstance().weixinCode = ((SendAuth.Resp)baseResp).code;
                getToken(AppContext.getInstance().weixinCode);
            } else {
                // 认证失败
                Toast.makeText(this, "认证失败", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        } else if(baseResp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX){
            Intent weixinShare=new Intent();
//            dismissProgressDialog();
            // 分享结果
            if(baseResp.errCode == BaseResp.ErrCode.ERR_OK) {
                // 分享成功
                weixinShare.setAction(Constants.WEIXIN_SHARE);
                weixinShare.putExtra(Constants.WEIXIN_SHARE_KEY,Constants.WEIXIN_SHARE_VALUE_SUCCEED);
                sendBroadcast(weixinShare);
                RxLogUtils.e("weixin",""+baseResp.errCode);
            } else {
                RxLogUtils.e("weixin","wrong:"+baseResp.errCode);
                weixinShare.setAction(Constants.WEIXIN_SHARE);
                weixinShare.putExtra(Constants.WEIXIN_SHARE_KEY,Constants.WEIXIN_SHARE_VALUE_FAILURE);
                sendBroadcast(weixinShare);
                // 认证失败
                if(AppContext.getInstance().mBaseWebAct != null) {
                    EshareLoger.logI("BaseWeb不为空fail");
                    AppContext.getInstance().mBaseWebAct.onShareCancel();
                }
            }
            this.finish();
        }
    }


    // 请求网络 获取微信token
    private void getToken(String code) {
        TLog.log("getToken code = " + code);
        RequestParams params = new RequestParams();
        params.put("appid", Constants.APP_ID);
        params.put("secret", Constants.SECRET);
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        NetworkDownload.byteGet(this, Constants.URL_GET_TOKEN, params, new NetworkDownload.NetworkDownloadCallBackbyte() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                try {
                    TokenBean token = TokenBean.parseJson(new JSONObject(new String(bytes)));
                    AppContext.getInstance().accessToken = token.access_token;
                    AppContext.getInstance().openid = token.openid;
                    getUserInfo();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(WXEntryActivity.this, "微信认证失败", Toast.LENGTH_SHORT).show();
                WXEntryActivity.this.finish();
            }
        });
    }
    // 获取微信信息
    private void getUserInfo() {
        TLog.error("idid" + AppContext.getInstance().openid);
        RequestParams params = new RequestParams();
        params.put("access_token", AppContext.getInstance().accessToken);
        params.put("openid", AppContext.getInstance().openid);
        NetworkDownload.byteGet(this, Constants.URL_GET_USERINFO, params, new NetworkDownload.NetworkDownloadCallBackbyte() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                Intent weixinacc=new Intent();
                weixinacc.setAction(Constants.WEIXIN_ACCREDIT);
                weixinacc.putExtra(Constants.WEIXIN_ACCREDIT_KEY,json);
                sendBroadcast(weixinacc);
                WXEntryActivity.this.finish();
            }
            @Override
            public void onFailure() {
                Toast.makeText(WXEntryActivity.this, "微信认证失败", Toast.LENGTH_SHORT).show();
                WXEntryActivity.this.finish();
            }
        });
    }

}