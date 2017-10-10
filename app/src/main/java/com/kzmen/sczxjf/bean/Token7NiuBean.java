package com.kzmen.sczxjf.bean;


import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * 7niu token
 */
public class Token7NiuBean {

    public String statuscode;
    public String msg;

    public static Token7NiuBean parseJson(JSONObject jsonObject){
        Gson gson = new Gson();
        Token7NiuBean bean = gson.fromJson(jsonObject.toString(),Token7NiuBean.class);

        return bean;
    }



}
