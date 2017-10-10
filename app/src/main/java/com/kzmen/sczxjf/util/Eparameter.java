package com.kzmen.sczxjf.util;

import com.kzmen.sczxjf.utils.AppUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 说明：请求接口的参数类
 * note：
 * Created by FuPei
 * on 2015/11/25 at 9:28
 */
public class Eparameter extends HashMap<String, Object> {
    private String url;

    public Eparameter(String url) {
        this.url = url;
    }
    /**
     * 获取Post的参数
     * @return
     */
    private RequestParams getPostParameter() {
        new RequestParams();
        RequestParams params = AppUtils.getParmObj(this);
        Iterator iterator = this.entrySet().iterator();
//        while(iterator.hasNext()) {
//            Map.Entry entry = (Map.Entry) iterator.next();
//        }
        return params;
    }

    /**
     * 获得Get的参数链接
     * @return
     */
    private String getGetParameter() {
        String url = "";
        Iterator iterator = this.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            url += "/" + entry.getKey() + "/" + entry.getValue();
        }
        return url;
    }

    /**
     * 当参数有data[]时，调用此方法格式化参数
     */
    public void formatData() {
        Iterator iterator = this.entrySet().iterator();
        Map<String, Object> map = new HashMap<>();
        while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            map.put("data[" + entry.getKey().toString() + "]", entry.getValue());
        }
        this.clear();
        this.putAll(map);
    }

    public void doGet(AsyncHttpResponseHandler handler) {
        new AsyncHttpClient().get(url + getGetParameter(), handler);
    }

    public void doPost(AsyncHttpResponseHandler handler) {
        new AsyncHttpClient().post(url, getPostParameter(), handler);
    }
}
