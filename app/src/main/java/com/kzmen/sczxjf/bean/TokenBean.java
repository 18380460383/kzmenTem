package com.kzmen.sczxjf.bean;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * token
 */
public class TokenBean {


    public int errcode;
    public String errmsg;

    public String access_token;
    public int expires_in;
    public String refresh_token;
    public String openid;
    public String scope;
    public String unionid;



    public static TokenBean parseJson(JSONObject jsonObj) {
        Gson gson = new Gson();
        TokenBean bean = gson.fromJson(jsonObj.toString(), TokenBean.class);

        return bean;
    }

}
