package com.kzmen.sczxjf.bean;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * 消息对象
 * Created by FuPei
 * on 2015/11/19.
 */
public class MsgBean {

    public String datetime;
    public String content;
    /*1：已读， 0：未读*/
    public String isread;
    public String type;
    public String title;
    /*消息的id*/
    public String id;
    /*用户的id*/
    public String uid;
    public String content_url;

    public String nid;
    public String content_preg;


    /**
     * isurl  1 链接  0内容
     */
    public String isurl ;
    public static MsgBean parseJson(JSONObject jsonObj) {
        Gson gson = new Gson();
        MsgBean bean = gson.fromJson(jsonObj.toString(), MsgBean.class);
        return bean;
    }
}
