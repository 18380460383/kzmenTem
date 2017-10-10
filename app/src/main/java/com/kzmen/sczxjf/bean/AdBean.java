package com.kzmen.sczxjf.bean;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * 广告条目
 */
public class AdBean {


    public String linkurl;
    public String imageurl;
    /**
     * json对象
     */
    public String parameter;
    /**
     * 类名
     */
    public String className;

    public int hid;
    public String  title;

    @Override
    public String toString() {
        return "AdBean{" +
                "linkurl='" + linkurl + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", parameter='" + parameter + '\'' +
                ", className='" + className + '\'' +
                ", hid=" + hid +
                ", title='" + title + '\'' +
                '}';
    }

    public static AdBean parseJson(JSONObject jsonObj) {
        Gson gson = new Gson();
        AdBean bean = gson.fromJson(jsonObj.toString(), AdBean.class);
        return bean;
    }



}
