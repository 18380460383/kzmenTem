package com.kzmen.sczxjf.net;

import android.content.Context;
import android.content.Intent;


import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.EnConstants;
import com.kzmen.sczxjf.bean.entitys.EnterPriseAppEntity;
import com.kzmen.sczxjf.bean.request.PublicParameter;
import com.kzmen.sczxjf.bean.user.User_For_pe;
import com.kzmen.sczxjf.interfaces.EnLoginInterface;
import com.kzmen.sczxjf.sql.enterpriseDB.EnDBHelper;
import com.kzmen.sczxjf.sql.enterpriseDB.EnUserDB;
import com.kzmen.sczxjf.util.EToastUtil;
import com.kzmen.sczxjf.util.EshareLoger;
import com.kzmen.sczxjf.utils.AESUtils;
import com.kzmen.sczxjf.utils.EnDataUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * describe:
 * notice:
 * Created by FuPei on 2016/5/20.
 */
public class EnWebUtil {

    private static final String STATUS_LOGIN_SUCCESS = "SUCCESS";
    private static final String APPKEY = "f819a5d294fc451989eccc7f909b8107";

    private static AsyncHttpClient mHttpClient;

    private static EnWebUtil mUtil;

    private static String mToken;

    private static String mUserKey;

    private EnWebUtil() {

    }

    public static EnWebUtil getInstance() {
        if(mHttpClient == null) {
            mHttpClient = new AsyncHttpClient();
            mHttpClient.setTimeout(5 * 1000);
        }
        if(mUtil == null) {
            mUtil = new EnWebUtil();
        }
        return mUtil;
    }

    private static AsyncHttpClient getClient(Context context) {
         AppContext instance = AppContext.getInstance();
        if(instance.getPersonageOnLine() && mToken == null) {
             User_For_pe peUser = instance.getPEUser();
            mToken = peUser.getToken();
            mUserKey = peUser.getSecurity_key();
        }
        mHttpClient.addHeader("token", mToken);
        mHttpClient.addHeader("userkey", mUserKey);
        mHttpClient.addHeader("appkey", APPKEY);
        //sign随便弄
        mHttpClient.addHeader("sign", "doubi");
        return mHttpClient;
    }

    /**
     * 登录验证信息，获取使用Token认证必须要的sign
     * @param context
     * @param account
     * @param pwd
     * @param loginInterface
     */
    public void login(final Context context, final String account, final String pwd, final EnLoginInterface loginInterface) {
        loginApp(context, account, pwd, loginInterface);
//        RequestParams params = new RequestParams();
//        params.add("account", account);
//        params.add("pwd", pwd);
//        params.add("sign", "abc");
//        mHttpClient.post(context, EnConstants.URL_LOGIN, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                if (bytes != null) {
//                    try {
//                        JSONObject json = new JSONObject(new String(bytes));
//                        String status = json.optString("status");
//                        if (STATUS_LOGIN_SUCCESS.equals(status)) {
//                            EshareLoger.logI("login success");
//                            mUserKey = json.optString("data");
//                            initToken(context, account, pwd, mUserKey, loginInterface);
//                        } else {
//                            loginInterface.onLoginFail("就是失败了");
//                        }
//                    } catch (JSONException e) {
//                        loginInterface.onLoginFail("服务器返回的信息出现异常");
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                loginInterface.onNetError();
//            }
//        });
    }

    private void loginApp(final Context context, final String account, final String pwd, final EnLoginInterface loginInterface) {
        RequestParams params = new RequestParams();
        params.add("on_phone", account);
        params.add("on_pwd", pwd);
        initParams(params, EnConstants.URL_LOGIN_APP);
        EshareLoger.logI("login app");
        getClient(context).post(context, EnConstants.URL_API, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    JSONObject jsonObject = new JSONObject(new String(bytes));
                    if (jsonObject.optString("errcode").equals("0")) {
                        String text = AESUtils.decrypt(jsonObject.optString("data"));
                        EshareLoger.logI("text:\n" + text);
                        EnterPriseAppEntity entity = new Gson().fromJson(text, EnterPriseAppEntity.class);
                        mToken = entity.getToken();
                        mUserKey = entity.getUserkey();
                        EnUserDB userDB = new EnUserDB();
                        userDB.setToken(mToken);
                        userDB.setUserkey(entity.getUserkey());
                        userDB.setDate(System.currentTimeMillis() + "");
                        userDB.setAccount(account);
                        EnDataUtils.saveValueByKey(EnDataUtils.KEY_PRISE_INFO, entity.toString());
                        EnDataUtils.saveAccount(account);
                        EnDataUtils.savePassword(account, pwd);
                        new EnDBHelper(context).addEnUser(userDB);

                        if(context!=null){
                            Intent intent = new Intent();
                            intent.setAction(EnConstants.BROCAST_LOGIN_SUCCESS);
                            context.sendBroadcast(intent);
                        }

                        loginInterface.onLoginSuccess(entity.getToken());
                    } else {
                        EshareLoger.logI("errcode:" + jsonObject.optString(", errcode") + "errmsg:" +
                                jsonObject.optString("errmsg"));
                        loginInterface.onLoginFail(jsonObject.optString("errmsg"));
                    }
                } catch (JSONException e) {
                    loginInterface.onLoginFail("数据解析失败");
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
               // EshareLoger.logI("onFailure" + new String(bytes));
                loginInterface.onLoginFail("连接网络失败");
            }
        });
    }

    /**
     * 初始化请求必须的Token
     * @param context
     * @param sign
     * @param loginInterface
     */
    private void initToken(final Context context, final String account, final String password,
                           String sign, final EnLoginInterface loginInterface) {
        RequestParams params = new RequestParams();
        params.add("sign", "aaa");
        params.add("code", sign);
        mHttpClient.post(context, EnConstants.URL_GET_TOKEN, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                if (bytes != null) {
                    try {
                        JSONObject json = new JSONObject(new String(bytes));
                        String status = json.optString("status");
                        if (STATUS_LOGIN_SUCCESS.equals(status)) {
                            EshareLoger.logI("initToken success");
                            mToken = json.optString("data");
                            EnUserDB userDB = new EnUserDB();
                            userDB.setToken(mToken);
                            userDB.setUserkey(mUserKey);
                            userDB.setDate(System.currentTimeMillis() + "");
                            userDB.setAccount(account);
                            EnDataUtils.saveAccount(account);
                            EnDataUtils.savePassword(account, password);
                            new EnDBHelper(context).addEnUser(userDB);
                            loginInterface.onLoginSuccess("登录成功");
                        } else {
                            loginInterface.onLoginFail("就是失败了");
                        }
                    } catch (JSONException e) {
                        loginInterface.onLoginFail("服务器返回的信息出现异常");
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                loginInterface.onNetError();
            }
        });
    }


    public void post(final Context context, String[] url, RequestParams params, AsyncHttpResponseHandler respon) {
        initParams(params, url);
        getClient(context).post(context, EnConstants.URL_API, params, respon);
    }

    public void get(final Context context, String[] url, RequestParams params, AsyncHttpResponseHandler respon) {
        initParams(params, url);
        getClient(context).get(context, EnConstants.URL_API, params, respon);
    }

    public void post(final Context context, String[] url, RequestParams params, final AesListener listener) {
        initParams(params, url);
        getClient(context).post(context, EnConstants.URL_API, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                if (bytes != null && i == 200) {
                    listener.onSuccess(new String(bytes));
                } else {
                    listener.onFail(i, new String(bytes));
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                listener.onWebError();
            }
        });
    }

    public void get(final Context context, String[] url, RequestParams params, final AesListener listener) {
        initParams(params, url);
        getClient(context).get(context, EnConstants.URL_API, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                if (bytes != null && i == 200) {
                    listener.onSuccess(new String(bytes));
                } else {
                    listener.onFail(i, new String(bytes));
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                listener.onWebError();
            }
        });
    }

    public void post(final Context context, String[] url, RequestParams params, final AesListener2 listener) {
        initParams(params, url);
        getClient(context).post(context, EnConstants.URL_API, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                if (bytes != null && i == 200) {
                    String text = new String(bytes);
                    try {
                        JSONObject json = new JSONObject(text);
                        String errorCode = json.getString("errcode");
                        String errorMsg = json.getString("errmsg");
                        String data = AESUtils.decrypt(json.getString("data"));
                        listener.onSuccess(errorCode, errorMsg, data);
                    } catch (JSONException e) {
                        EshareLoger.logI("jsonException:" + text);
                        listener.onFail("服务器发生未知错误");
                    }
                } else {
                    listener.onFail(new String(bytes));
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                EToastUtil.show(context, "无法连接网络");
                listener.onFail("无法连接网络");
            }
        });
    }

    /**
     * 上传文件
     * @param context
     * @param path
     */
    public void uploadFile(Context context, String path,  NetworkDownload.NetworkDownloadCallBackJson listener) {
        RequestParams requestParams = new RequestParams();
        try {
            requestParams.put("imageurl", new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        NetworkDownload.jsonPostForCode1(context, Constants.URL_SERVER_UPLOAD_PIC, requestParams, listener);
    }

    private void initParams(RequestParams params, String[] url) {
//        if(!url.equals(EnConstants.URL_GET_WRITER)) {
            PublicParameter.getPublicParameter(AppContext.getInstance()).getRequestParams(params);
//        }
        params.add("controller", url[0]);
        params.add("func", url[1]);
        params.put("public_source","fc5caa024640c1c559dec6bd885e2f36");
    }

    /**
     * 取消当前context网络连接的请求
     * @param context
     */
    public void cancelRequest(Context context) {
        mHttpClient.cancelRequests(context, true);
    }

    public interface AesListener{
        void onSuccess(String result);
        void onFail(int code, String result);
        void onWebError();
    }

    public interface AesListener2{
        void onSuccess(String errorCode, String errorMsg, String data);
        void onFail(String result);
    }
}
