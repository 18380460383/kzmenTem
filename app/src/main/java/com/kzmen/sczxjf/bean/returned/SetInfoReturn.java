package com.kzmen.sczxjf.bean.returned;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * 说明：
 * note：
 * Created by FuPei
 * on 2015/12/1 at 14:21
 */
public class SetInfoReturn {
    public String uid;
    public String username;
    public String on_phone;
    public double withdraw_ok;
    public double earn_money;
    public String isjpush;
    public String relay;
    public String relay_perc;
    public static SetInfoReturn parseJson(JSONObject jsonObj) {
        Gson gson = new Gson();
        SetInfoReturn bean = gson.fromJson(jsonObj.toString(), SetInfoReturn.class);
        return bean;
    }
}
