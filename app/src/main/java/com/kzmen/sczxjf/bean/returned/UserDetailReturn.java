package com.kzmen.sczxjf.bean.returned;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 说明：
 * note：
 * Created by FuPei
 *
 * on 2015/11/25 at 11:51
 */
public class UserDetailReturn {
    public String code;
    public String msg;
    public JSONObject data;
    public static UserDetailReturn parseJson(JSONObject jsonObject){
        Gson gson = new Gson();
        UserDetailReturn bean = gson.fromJson(jsonObject.toString(), UserDetailReturn.class);
        try {
            bean.data = jsonObject.getJSONObject("data");
        } catch (JSONException e) {

        }
        return bean;
    }
}
