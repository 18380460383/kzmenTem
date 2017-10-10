package com.kzmen.sczxjf.bean.kzbean;

import org.json.JSONObject;

/**
 * 说明：
 * note：
 * Created by FuPei
 * on 2016/1/26 at 9:55
 */
public class BaseBean {

    public int code;
    public String message;
    public String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static BaseBean parseEntity(JSONObject json) {
        BaseBean bean=new BaseBean();
        try{
            bean.setCode(json.getInt("code"));
            bean.setMessage(json.getString("message"));
            bean.data = json.getString("data");
        } catch (Exception e){
            bean.data = "";
        }
        return bean;
    }
}
