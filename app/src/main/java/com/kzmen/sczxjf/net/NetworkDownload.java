package com.kzmen.sczxjf.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.bean.request.PublicParameter;
import com.kzmen.sczxjf.ui.activity.personal.MainTabActivity;
import com.kzmen.sczxjf.util.EshareLoger;
import com.kzmen.sczxjf.utils.DESUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 杨操 on 2015/12/1.
 * 描述：用于网络接口访问的简单工具类
 * 成功返回 不成功提示：网络访问失败
 * 可判断code 码
 * 需要：compile 'com.loopj.android:android-async-http:1.4.9'
 */
public class NetworkDownload {
    /**
     * 当前进行接口访问的管理类{@link AsyncHttpClient}对象
     */
    private static AsyncHttpClient asyncHttpClient;
    /**
     * 当前进行企业接口访问的管理类{@link AsyncHttpClient}对象
     */
    private static AsyncHttpClient asyncHttpClientEnterprise;
    /**
     * 接口访问时弹出的提示对话框
     */
    private static ProgressDialog dialog;
    /**
     * 存放对话框的信息{@link ArrayList}对象
     */
    private static List<String> dialogMSG = new ArrayList<>();
    /**
     * 判断当前需要显示几条信息的值
     */
    private static int i = 0;
    /**
     * 设置连接超时时间
     */
    private static int CONNECTTIMEOUT = 10000;
    private Header[] headers;

    public void setHeaders(Header[] headers,String appkey,String token) {
    }

    /**
     * 获取单例类型的接口访问的管理类{@link AsyncHttpClient}对象
     *
     * @return {@link AsyncHttpClient}对象
     */
    private static AsyncHttpClient getAsyncHttpClient() {
        if (null == asyncHttpClient) {
            asyncHttpClient = new AsyncHttpClient();

            asyncHttpClient.setConnectTimeout(CONNECTTIMEOUT);
            asyncHttpClient.setMaxRetriesAndTimeout(1,CONNECTTIMEOUT);
        }
        return asyncHttpClient;
    }


    /**
     * 设置连接超时时间，默认20秒
     *
     * @param timeout 超时时间
     */
    public static void setConnectTimeout(int timeout) {
        CONNECTTIMEOUT = timeout;
        asyncHttpClient.setConnectTimeout(timeout);
    }

    /**
     * 网络请求入参处理方法
     * @return {@link RequestParams}对象
     */
    private static RequestParams dealWthRequestParams(RequestParams params){
         if(null==params){
             return null;
         }
        if(params.has("data")){
            return params;
        }else {
            params = PublicParameter.getPublicParameter(AppContext.getInstance()).getRequestParams(params);
            System.out.println(params.toString());
        }
        return params;
    }

    /**
     * 普通的返回字节数组的网络GET请求方法
     * @param context  上下文
     * @param url      路径
     * @param params   参数{@link RequestParams}形式
     * @param byteback 访问成功的的回调接口
     */
    public static void byteGet(final Context context, String url, RequestParams params, final NetworkDownloadCallBackbyte byteback) {
        if(isNetworking(context)) {
            params = dealWthRequestParams(params);
            getAsyncHttpClient().get(context, url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    byteback.onSuccess(i, headers, bytes);
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    cacheMiss(context, i);
                    byteback.onFailure();
                }
            });
        }else{
            byteback.onFailure();
        }
    }

    /**
     * 普通的返回字节数组的网络POST请求方法
     * @param context  上下文
     * @param url      路径
     * @param params   参数{@link RequestParams}形式
     * @param byteback 访问成功的的回调接口
     */
    public static void bytePost(final Context context, String url, RequestParams params, final NetworkDownloadCallBackbyte byteback) {
        if(isNetworking(context)) {
            params = dealWthRequestParams(params);
            getAsyncHttpClient().post(context, url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    byteback.onSuccess(statusCode, headers, responseBody);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    cacheMiss(context, statusCode);
                    byteback.onFailure();
                }
            });
        }else{
            byteback.onFailure();
        }
    }
    /**
     * 普通的返回JSON字符串的网络GET请求方法
     * 当中途出现错误时调用返回访问失败的回调方法
     * @param context  上下文
     * @param url      路径
     * @param params   参数{@link RequestParams}形式
     * @param jsonback 访问成功的的回调接口
     */
    public static void jsonGet(final Context context, String url, RequestParams params, final NetworkDownloadCallBackJson jsonback) {
        if(isNetworking(context)) {
            params = dealWthRequestParams(params);
            getAsyncHttpClient().get(context, url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        byte[] data = dealWthResponseBody(responseBody);
                        if (null != data) {
                            String json = new String(data);
                            Log.i("responseBody", json);
                            JSONObject jsonObject = new JSONObject(json);
                            jsonback.onSuccess(statusCode, headers, jsonObject);
                        } else {
                            jsonback.onFailure();
                        }
                    } catch (JSONException e) {
                        ToastJsonErr(context);
                        jsonback.onFailure();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    cacheMiss(context, statusCode);
                    jsonback.onFailure();
                }
            });
        }else{
            jsonback.onFailure();
        }
    }

    /**
     * 普通的返回JSON字符串的网络POST请求方法
     * 当中途出现错误时调用返回访问失败的回调方法
     * @param context  上下文
     * @param url      路径
     * @param params   参数{@link RequestParams}形式
     * @param jsonback 访问成功的的回调接口
     */
    public static void jsonPost(final Context context, String url, RequestParams params, final NetworkDownloadCallBackJson jsonback) {
        if(isNetworking(context)) {
            params = dealWthRequestParams(params);
            getAsyncHttpClient().post(context, url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        byte[] data = dealWthResponseBody(responseBody);
                        if (null != data) {
                            String json = new String(data);
                            Log.i("responseBody", json);
                            JSONObject jsonObject = new JSONObject(json);
                            jsonback.onSuccess(statusCode, headers, jsonObject);
                        } else {
                            jsonback.onFailure();
                        }
                    } catch (JSONException e) {
                        ToastJsonErr(context);
                        jsonback.onFailure();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    cacheMiss(context, statusCode);
                    jsonback.onFailure();
                }
            });
        }else{
            jsonback.onFailure();
        }
    }

    /**
     * 判断CODE后返回JSON字符串的网络GET请求方法
     * 当中途出现错误时调用返回访问失败的回调方法
     * @param context  上下文
     * @param url      路径
     * @param params   参数{@link RequestParams}形式
     * @param jsonback 访问成功的的回调接口
     */
    public static void jsonGetForCode(final Context context, String url, RequestParams params, final String codestr, final int code, final String datastr, final NetworkDownloadCallBackJson jsonback) {
        if(isNetworking(context)) {
            params = dealWthRequestParams(params);
            getAsyncHttpClient().get(context, url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String json = new String(dealWthResponseBody(responseBody));
                        Log.i("responseBody", json);
                        JSONObject jsonObject = new JSONObject(json);
                        if (jsonObject.optInt(codestr) == code) {
                            jsonback.onSuccess(statusCode, headers, jsonObject.optJSONObject(datastr));
                        }

                    } catch (JSONException e) {
                        ToastJsonErr(context);
                        jsonback.onFailure();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    cacheMiss(context, statusCode);
                    jsonback.onFailure();
                }
            });
        }else{
            jsonback.onFailure();
        }
    }

    /**
     * 判断CODE后返回JSON字符串的网络POST请求方法
     * 当中途出现错误时调用返回访问失败的回调方法
     * @param context  上下文
     * @param url      路径
     * @param params   参数{@link RequestParams}形式
     * @param jsonback 访问成功的的回调接口
     */
    public static void jsonPostForCode(final Context context, String url, RequestParams params, final String codestr, final int code, final String datastr, final NetworkDownloadCallBackJson jsonback) {
        if(isNetworking(context)) {
            params = dealWthRequestParams(params);
            getAsyncHttpClient().post(context, url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String json = new String(dealWthResponseBody(responseBody));
                        Log.i("responseBody", json);
                        JSONObject jsonObject = new JSONObject(json);
                        if (jsonObject.optInt(codestr) == code) {
                            jsonback.onSuccess(statusCode, headers, jsonObject.optJSONObject(datastr));
                        }
                    } catch (JSONException e) {
                        ToastJsonErr(context);
                        jsonback.onFailure();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    cacheMiss(context, statusCode);
                    jsonback.onFailure();
                }
            });
        }else{
            jsonback.onFailure();
        }
    }

    /**
     * 判断CODE等于1后返回JSON字符串的网络GET请求方法
     * 当中途出现错误时调用返回访问失败的回调方法
     * @param context  上下文
     * @param url      路径
     * @param params   参数{@link RequestParams}形式
     * @param jsonback 访问成功的的回调接口
     */
    public static void jsonGetForCode1(final Context context, String url, RequestParams params, final NetworkDownloadCallBackJson jsonback) {
        if(isNetworking(context)) {
            params = dealWthRequestParams(params);
            getAsyncHttpClient().get(context, url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        byte[] data = dealWthResponseBody(responseBody);
                        if (null != data) {
                            String json = new String(data);
                            Log.i("responseBody", json);
                            JSONObject jsonObject = new JSONObject(json);
                            if (jsonObject.optInt("code") == 1) {
                                jsonback.onSuccess(statusCode, headers, jsonObject);
                            } else {
                                if (isToastMsg(context)) {
                                    Toast.makeText(context, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                                }
                                jsonback.onFailure();
                            }
                        }
                    } catch (JSONException e) {
                        ToastJsonErr(context);
                        jsonback.onFailure();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    cacheMiss(context, statusCode);
                    jsonback.onFailure();
                }
            });
        }else{
            jsonback.onFailure();
        }
    }

    /**
     * 判断CODE等于1后返回JSON字符串的网络POST请求方法
     * 当中途出现错误时调用返回访问失败的回调方法
     * @param context  上下文
     * @param url      路径
     * @param params   参数{@link RequestParams}形式
     * @param jsonback 访问成功的的回调接口
     */
    public static void jsonPostForCode1(final Context context, final String url, RequestParams params, final NetworkDownloadCallBackJson jsonback) {
        if(isNetworking(context)) {
            params = dealWthRequestParams(params);
            getAsyncHttpClient().post(context, url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        byte[] data = dealWthResponseBody(responseBody);
                        if (null != data) {
                            String json = new String(data);
                            Log.i("responseBody", json);
                            JSONObject jsonObject = new JSONObject(json);
                            if (jsonObject.optInt("code") == 1) {
                                dealJiFen(jsonObject, url);

                                jsonback.onSuccess(statusCode, headers, jsonObject);
                            } else {
                                if (jsonObject.optInt("code") == 4) {
                                    Toast.makeText(context, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                                }
                                if (isToastMsg(context)) {
                                    EshareLoger.logI("toast");
                                    Toast.makeText(context, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                                }
                                jsonback.onFailure();
                            }
                        }
                    } catch (JSONException e) {
                        ToastJsonErr(context);
                        jsonback.onFailure();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    cacheMiss(context, statusCode);
                    jsonback.onFailure();
                }
            });
        }else{
            jsonback.onFailure();
        }
    }

    private static void dealJiFen(JSONObject jsonObject, String url) {
       /* if(url.equals(Constants.URL_POST_ADD_NEWS_COMMENT)||url.equals(Constants.URL_POST_LOGIN)){
            JSONObject object = jsonObject.optJSONObject("data");
            try {
                double add_score = object.optDouble("add_score");
                if (add_score > 0) {
                    Toast.makeText(AppContext.getInstance(), "+" + add_score + "积分", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){

            }
        }*/
    }



    public  static AsyncHttpClient getAsyncHttpClientForEnterprise() {
        if(asyncHttpClientEnterprise==null){
            asyncHttpClientEnterprise=new AsyncHttpClient();
        }
        return asyncHttpClientEnterprise;
    }

    private static Header[] getHeaders() {
        return new Header[0];
    }

    /**
     * 用于关闭某一个上下文所需要的网络访问
     *
     * @param context 上下文
     */
    public static void stopRequest(Context context) {
        getAsyncHttpClient().cancelRequests(context, true);
        if(asyncHttpClientEnterprise!=null){
            getAsyncHttpClientForEnterprise().cancelRequests(context, true);
        }

    }

    /**
     * 用于关闭所有的网络访问
     */
    public static void stopALLRequest(Context context) {
        getAsyncHttpClient().cancelAllRequests(true);
        if(asyncHttpClientEnterprise!=null) {
            getAsyncHttpClientForEnterprise().cancelAllRequests(true);
        }
    }


    /**
     * 网络访问返回字节数组的回调接口
     */
    public interface NetworkDownloadCallBackbyte {
        /**
         * 网络访问返回字节数组的回调方法
         */
        void onSuccess(int statusCode, Header[] headers, byte[] responseBody);

        void onFailure();
    }

    /**
     * 网络访问返回JSON字符串的回调接口
     */
    public interface NetworkDownloadCallBackJson {
        /**
         * 网络访问返回JSON字符串的回调方法
         */
        void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException;

        void onFailure();
    }

    /**
     * 通过不同的返回码显示提示信息
     *
     * @param context  上下文
     * @param missCode 返回码
     */
    private static void cacheMiss(Context context, int missCode) {
        if(isToastMsg(context)){
        switch (missCode) {
            case 400:
                Toast.makeText(context, "服务器不理解的请求", Toast.LENGTH_SHORT).show();
                break;
            case 401:
                Toast.makeText(context, "未授权的请求", Toast.LENGTH_SHORT).show();
                break;
            case 403:
                Toast.makeText(context, "服务器拒绝了请求", Toast.LENGTH_SHORT).show();
                break;
            case 404:
                Toast.makeText(context, "找不到的请求", Toast.LENGTH_SHORT).show();
                break;
            case 405:
                Toast.makeText(context, "请求中指定的方法被禁用了", Toast.LENGTH_SHORT).show();
                break;
            case 406:
                Toast.makeText(context, "服务器没有接受此次请求", Toast.LENGTH_SHORT).show();
                break;
            case 407:
                Toast.makeText(context, "此次请求需要代理授权", Toast.LENGTH_SHORT).show();
                break;
            case 408:
                Toast.makeText(context, "请求超时", Toast.LENGTH_SHORT).show();
                break;
            case 409:
                Toast.makeText(context, "此次请求产生了冲突", Toast.LENGTH_SHORT).show();
                break;
            case 410:
                Toast.makeText(context, "请求的资源不存在", Toast.LENGTH_SHORT).show();
                break;
            case 411:
                Toast.makeText(context, "请求的类容无效", Toast.LENGTH_SHORT).show();
                break;
            case 413:
                Toast.makeText(context, "请求实体过大", Toast.LENGTH_SHORT).show();
                break;
            case 414:
                Toast.makeText(context, "请求的URI过长", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(context, "网络访问失败", Toast.LENGTH_SHORT).show();
                break;

        }
        }
    }

    /**
     * JSON数据解析出错的提示
     *
     * @param context 上下文对象
     */
    private static void ToastJsonErr(Context context) {
        if(context==AppContext.getInstance()) {
            Toast.makeText(context, "数据解析错误", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 对网络获得的数据进行解析前的处理
     * @param responseBody 需要处理的字节数组
     * @return 处理后的字节数组
     */
    private static byte[] dealWthResponseBody(byte[] responseBody) {
        return DESUtils.ebotongDecrypto(responseBody);
    }
    private static boolean isToastMsg(Context context){
        if(context==AppContext.getInstance()||(null != context && context == AppContext.getInstance().getOneActivity())||(null!=context&&AppContext.getInstance().getOneActivity() instanceof MainTabActivity)){
            return true;
        }
        return false;
    }
    private static boolean isNetworking(Context context){
        ConnectivityManager cm = (ConnectivityManager) AppContext.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        boolean a=ni != null && ni.isConnectedOrConnecting();
        if(!a){
                if(isToastMsg(context)){
                    Toast.makeText(context,"网络无法使用请设置",Toast.LENGTH_LONG).show();
                }
        }
        return a;
    }

}
