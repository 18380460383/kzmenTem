package com.kzmen.sczxjf.bean;

import com.google.gson.Gson;

/**
 * 说明：摇一摇js返回的数据
 * Created by FuPei
 * on 2015/12/30 at 10:20
 */
public class YaoBean {

    public String Title;
    public String desc;
    public String imgUrl;
    public String Link;
    public String shareType;
    public String sid;

    public static YaoBean parseJson(String textjson) {
        Gson gson = new Gson();
        YaoBean bean = gson.fromJson(textjson, YaoBean.class);
        return bean;
    }
}
