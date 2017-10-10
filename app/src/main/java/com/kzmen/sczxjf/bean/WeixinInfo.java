package com.kzmen.sczxjf.bean;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * token
 */
public class WeixinInfo {
    public int errcode;
    public String errmsg;
    public String openid;
    public String nickname;
    public int sex;
    public String province;
    public String city;
    public String country;
    public String headimgurl;
//    public String[] privilege;
    public String unionid;



    public static WeixinInfo parseJson(JSONObject jsonObj) {
        Gson gson = new Gson();
        WeixinInfo bean = gson.fromJson(jsonObj.toString(), WeixinInfo.class);

        return bean;
    }

}
