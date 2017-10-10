package com.kzmen.sczxjf.bean;

import com.kzmen.sczxjf.util.StringUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * 兴趣
 * 行业
 */
public class InterestBean {


    public int iid;// 兴趣
    public int did;// 行业
    public int sid;
    public int cid;
    public int aid;
    public int fid;// 职业
    public String item;

    public boolean checked = false;


    public static InterestBean parseJson(JSONObject jsonObj) {
        Gson gson = new Gson();
        InterestBean bean = gson.fromJson(jsonObj.toString(), InterestBean.class);

        return bean;
    }

    @Override
    public String toString() {
        return StringUtils.isEmpty(item) ? super.toString() : item;
    }
}
