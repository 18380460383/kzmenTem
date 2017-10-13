package com.kzmen.sczxjf.net;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.bean.kzbean.BaseBean;
import com.kzmen.sczxjf.bean.kzbean.OrderBean;
import com.kzmen.sczxjf.control.CustomProgressDialog;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.ui.activity.kzmessage.IndexActivity;
import com.kzmen.sczxjf.ui.activity.menu.PayTypeAcitivity;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.lzy.okhttputils.model.HttpHeaders;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogSureCancel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/8/17.
 */

public class OkhttpUtilManager {
    public static String URL = "http://api.kzmen.cn/api.php/";
    public static String URL_INTEGAL = URL + "html/score_rule";//我的积分规则
    public static String URL_WITHDRAWAL = URL + "html/withdraw_rule";//提现规则
    public static String URL_RECHARGE = URL + "html/recharge_rule";//充值说明
    public static String URL_VIP_CNENTER = URL + "html/help";//会员中心 帮助
    public static String URL_ABOUT = URL + "html/about";//关于卡掌门
    public static String URL_ASK = URL + "html/disclaimer";//提问-》免责
    public static String URL_USER_RULE = URL + "html/user_agreement\n";//提问-》免责
    private Context mContext;
    private OkhttpUtilManager manager;
    private static RxDialogSureCancel rxDialogSureCancel;

    private static void setDialog(final Context mContext, final BaseBean bean, final OkhttpUtilResult result) {
        rxDialogSureCancel = new RxDialogSureCancel(mContext);
        rxDialogSureCancel.setContent(bean.getMessage());
        rxDialogSureCancel.setIsShow();
        rxDialogSureCancel.setSure("去登录");
        rxDialogSureCancel.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.onErrorWrong(1024, bean.getMessage());
                AppContext.getInstance().setPersonageOnLine(false);
                AppContext.getInstance().setUserLoginOut();
                mContext.startActivity(new Intent(mContext, IndexActivity.class));
                rxDismiss();
            }
        });
        rxDialogSureCancel.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.onErrorWrong(bean.getCode(), bean.getMessage());
                rxDismiss();
            }
        });
        rxDialogSureCancel.show();
    }

    public static void postNoCacah(final Context mContext, final String url, Map<String, String> param, final OkhttpUtilResult result) {
        Gson gson = new Gson();
        HttpHeaders headers = new HttpHeaders();
        headers.put("sign", AppContext.sign);    //所有的 header 都 不支持 中文
        headers.put("token", AppContext.token);
        headers.put("publicdeviceversion", AppContext.public_deviceVersion);    //所有的 header 都 不支持 中文
        headers.put("publicdevicetype", AppContext.public_deviceType);
        headers.put("publicdeviceid", AppContext.public_deviceId);    //所有的 header 都 不支持 中文
        OkHttpUtils.post(URL + url)
                .tag(mContext)
                .params(param)
                .headers(headers)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            if (result == null) {
                                return;
                            }
                            final BaseBean bean = BaseBean.parseEntity(object);
                            if (bean.getCode() == 200) {
                                result.onSuccess(100, bean.getData());
                            } else if (bean.getCode() == 998 || bean.getCode() == 997) {
                                if (url.equals("public/login") || url.equals("public/weixinLogin")) {
                                    result.onErrorWrong(1023, bean.getMessage());
                                } else {
                                    setDialog(mContext, bean, result);
                                }
                            } else {
                                result.onErrorWrong(bean.getCode(), bean.getMessage());
                            }
                        } catch (JSONException e) {
                            result.onErrorWrong(99, "测试" + e.toString());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (result == null) {
                            return;
                        }
                        result.onErrorWrong(99, e.toString());
                    }
                });
    }

    public static void postObjec(final Context mContext, final String url, Map<String, String> param, File paramFile, final OkhttpUtilResult result) {
        Gson gson = new Gson();
        String data = gson.toJson(param);
        HttpHeaders headers = new HttpHeaders();
        headers.put("sign", AppContext.sign);    //所有的 header 都 不支持 中文
        headers.put("token", AppContext.token);
        headers.put("publicdeviceversion", AppContext.public_deviceVersion);    //所有的 header 都 不支持 中文
        headers.put("publicdevicetype", AppContext.public_deviceType);
        headers.put("publicdeviceid", AppContext.public_deviceId);    //所有的 header 都 不支持 中文
        OkHttpUtils.post(URL + url)
                .tag(mContext)
                .params(param)
                //.params("mediafile", paramFile)
                .params("mediafile", paramFile, "recoder.mp3", MediaType.parse("application/octet-stream"))
                .headers(headers)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            if (result == null) {
                                return;
                            }
                            final BaseBean bean = BaseBean.parseEntity(object);
                            if (bean.getCode() == 200) {
                                result.onSuccess(100, bean.getData());
                            } else if (bean.getCode() == 998 || bean.getCode() == 997) {
                                if (url.equals("public/login") || url.equals("public/weixinLogin")) {
                                    result.onErrorWrong(1023, bean.getMessage());
                                } else {
                                    setDialog(mContext, bean, result);
                                }
                               /* AppContext.getInstance().setPersonageOnLine(false);
                                mContext.startActivity(new Intent(mContext, IndexActivity.class));*/
                            } else {
                                result.onErrorWrong(bean.getCode(), bean.getMessage());
                            }
                        } catch (JSONException e) {
                            result.onErrorWrong(99, "测试" + e.toString());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (result == null) {
                            return;
                        }
                        result.onErrorWrong(99, e.toString());
                    }
                });
    }

    public static void postObjec(final Context mContext, final String url, Map<String, String> param, List<File> paramFile, final OkhttpUtilResult result) {
        Gson gson = new Gson();
        String data = gson.toJson(param);
        HttpHeaders headers = new HttpHeaders();
        headers.put("sign", AppContext.sign);    //所有的 header 都 不支持 中文
        headers.put("token", AppContext.token);
        headers.put("publicdeviceversion", AppContext.public_deviceVersion);    //所有的 header 都 不支持 中文
        headers.put("publicdevicetype", AppContext.public_deviceType);
        headers.put("publicdeviceid", AppContext.public_deviceId);    //所有的 header 都 不支持 中文
        OkHttpUtils.post(URL + url)
                .tag(mContext)
                .params(param)
                .addFileParams("data[mediafile]", paramFile)
                //.params("data[mediafile]", paramFile.get(0), "audio/mp3")
                .headers(headers)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            if (result == null) {
                                return;
                            }
                            final BaseBean bean = BaseBean.parseEntity(object);
                            if (bean.getCode() == 200) {
                                result.onSuccess(100, bean.getData());
                            } else if (bean.getCode() == 998 || bean.getCode() == 997) {
                                if (url.equals("public/login") || url.equals("public/weixinLogin")) {
                                    result.onErrorWrong(1023, bean.getMessage());
                                } else {
                                    setDialog(mContext, bean, result);
                                }
                                /*AppContext.getInstance().setPersonageOnLine(false);
                                mContext.startActivity(new Intent(mContext, IndexActivity.class));*/
                            } else {
                                result.onErrorWrong(bean.getCode(), bean.getMessage());
                            }
                        } catch (JSONException e) {
                            result.onErrorWrong(99, "测试" + e.toString());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (result == null) {
                            return;
                        }
                        result.onErrorWrong(99, e.toString());
                    }
                });
    }

    /**
     * 网络请求对话框
     */
    private static CustomProgressDialog progressDialog;

    public static void setOrder(final Context mContext, String url, Map<String, String> param) {
        progressDialog = new CustomProgressDialog(mContext);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setText("生成订单中");
        progressDialog.show();
        Gson gson = new Gson();
        String data = gson.toJson(param);
        //Log.e("tst",data);
        HttpHeaders headers = new HttpHeaders();
        headers.put("sign", AppContext.sign);    //所有的 header 都 不支持 中文
        headers.put("token", AppContext.token);
        headers.put("publicdeviceversion", AppContext.public_deviceVersion);    //所有的 header 都 不支持 中文
        headers.put("publicdevicetype", AppContext.public_deviceType);
        headers.put("publicdeviceid", AppContext.public_deviceId);    //所有的 header 都 不支持 中文
        OkHttpUtils.post(URL + url)
                .tag(mContext)
                .params(param)
                .headers(headers)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("order", s);
                        try {
                            JSONObject object = new JSONObject(s);
                            final BaseBean bean = BaseBean.parseEntity(object);
                            if (bean.getCode() == 200) {
                                Gson gson = new Gson();
                                JSONObject object1 = new JSONObject(bean.getData());
                                OrderBean orderBean = gson.fromJson(object1.getString("data"), OrderBean.class);
                                Intent intent = new Intent(mContext, PayTypeAcitivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("orderBean", orderBean);
                                intent.putExtras(bundle);
                                mContext.startActivity(intent);
                            } else if (bean.getCode() == 998 || bean.getCode() == 997) {
                                rxDialogSureCancel = new RxDialogSureCancel(mContext);
                                rxDialogSureCancel.setTitle(bean.getMessage());
                                rxDialogSureCancel.setSure("去登录");
                                rxDialogSureCancel.setSureListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        AppContext.getInstance().setPersonageOnLine(false);
                                        mContext.startActivity(new Intent(mContext, IndexActivity.class));
                                        rxDismiss();
                                    }
                                });
                                rxDialogSureCancel.setCancelListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //result.onErrorWrong(bean.getCode(),bean.getMessage());
                                        rxDismiss();
                                    }
                                });
                                rxDialogSureCancel.show();
                                /*AppContext.getInstance().setPersonageOnLine(false);
                                mContext.startActivity(new Intent(mContext, IndexActivity.class));*/
                            } else {
                                RxToast.normal("" + bean.getMessage());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        RxToast.normal("订单生成失败");
                        progressDialog.dismiss();
                    }
                });
    }

    private static void rxDismiss() {
        if (null != rxDialogSureCancel) {
            rxDialogSureCancel.dismiss();
        }
    }

    public static void setUserOrder(final Context mContext, Map<String, String> param, final OkhttpUtilResult result) {
        progressDialog = new CustomProgressDialog(mContext);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setText("生成订单中");
        progressDialog.show();
        Gson gson = new Gson();
        String data = gson.toJson(param);
        HttpHeaders headers = new HttpHeaders();
        headers.put("sign", AppContext.sign);    //所有的 header 都 不支持 中文
        headers.put("token", AppContext.token);
        headers.put("publicdeviceversion", AppContext.public_deviceVersion);    //所有的 header 都 不支持 中文
        headers.put("publicdevicetype", AppContext.public_deviceType);
        headers.put("publicdeviceid", AppContext.public_deviceId);    //所有的 header 都 不支持 中文
        OkHttpUtils.post(URL + "Order/UserOrderPay")
                .tag(mContext)
                .params(param)
                .headers(headers)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("order", s);
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.getInt("code");
                            final BaseBean bean = BaseBean.parseEntity(object);
                            if (bean.getCode() == 200) {
                                JSONObject object1 = new JSONObject(bean.getData());
                                JSONObject jsonObject = new JSONObject(object1.getString("data"));
                                if (result != null) {
                                    result.onSuccess(code, jsonObject.getString("charge"));
                                }
                            } else if (bean.getCode() == 998 || bean.getCode() == 997) {
                                rxDialogSureCancel = new RxDialogSureCancel(mContext);
                                rxDialogSureCancel.setTitle(bean.getMessage());
                                rxDialogSureCancel.setSure("去登录");
                                rxDialogSureCancel.setSureListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        result.onErrorWrong(1024, bean.getMessage());
                                        AppContext.getInstance().setPersonageOnLine(false);
                                        mContext.startActivity(new Intent(mContext, IndexActivity.class));
                                        rxDismiss();
                                    }
                                });
                                rxDialogSureCancel.setCancelListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        result.onErrorWrong(bean.getCode(), bean.getMessage());
                                        rxDismiss();
                                    }
                                });
                                rxDialogSureCancel.show();
                                /*AppContext.getInstance().setPersonageOnLine(false);
                                mContext.startActivity(new Intent(mContext, IndexActivity.class));*/
                            } else {
                                result.onErrorWrong(code, bean.getMessage());
                                RxToast.normal("" + bean.getMessage());
                            }
                        } catch (JSONException e) {
                            result.onErrorWrong(9999, e.toString());
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        progressDialog.dismiss();
                        result.onErrorWrong(999, "用户订单生成失败");
                    }
                });
    }

}
