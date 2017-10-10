package com.kzmen.sczxjf.bean;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * 说明：
 * note：
 * Created by FuPei
 * on 2016/1/26 at 9:55
 */
public class BaseBean {

    public String msg;
    public String code;
    public String info;

    public static BaseBean parseEntity(JSONObject json) {
        BaseBean bean;
        bean = new Gson().fromJson(json.toString(), BaseBean.class);
        try{
            bean.info = json.getString("data");
        } catch (Exception e){
            bean.info = "";
        }
        return bean;
    }
}
