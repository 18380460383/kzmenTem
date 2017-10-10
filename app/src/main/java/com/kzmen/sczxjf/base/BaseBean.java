package com.kzmen.sczxjf.base;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * @author wu
 * @version 2.0
 * @created 2015/9/24
 */
public class BaseBean  {

    @SerializedName("ErrorCode")
    public int errorCode;

    @SerializedName("Success")
    public boolean success;

    @SerializedName("Details")
    public String detials;

    @SerializedName("SourceResult")
    public BaseBean sourceResult;


    public static BaseBean parseJson(JSONObject jsonObj) {
        Gson gson = new Gson();
        BaseBean bean = gson.fromJson(jsonObj.toString(), BaseBean.class);

        return bean;
    }

}
