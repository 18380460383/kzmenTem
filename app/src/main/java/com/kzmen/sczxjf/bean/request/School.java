package com.kzmen.sczxjf.bean.request;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/6.
 */
public class School implements Serializable{
    private String sid="null";
    private String id="null";
    private String sc_name="null";
    private String xi="null";
    private String zhuany="null";
    private String nianj="null";
    private String banj="null";
    public static School parseJsonReturn(JSONObject json) {
        if(json == null || json.toString().length() == 0) {
             new School();
        }
        Gson gson = new Gson();
        School school = gson.fromJson(json.toString(), School.class);
        return school;
    }

    public String getSc_name() {
        return sc_name;
    }

    public void setSc_name(String sc_name) {
        this.sc_name = sc_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String sid) {
        this.id = sid;
    }

    public String getXi() {
        return xi;
    }

    public void setXi(String xi) {
        this.xi = xi;
    }

    public String getZhuany() {
        return zhuany;
    }

    public void setZhuany(String zhuany) {
        this.zhuany = zhuany;
    }

    public String getNianj() {
        return nianj;
    }

    public void setNianj(String nianj) {
        this.nianj = nianj;
    }

    public String getBanj() {
        return banj;
    }

    public void setBanj(String banj) {
        this.banj = banj;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    @Override
    public String toString() {
        return "School{" +
                "sid='" + sid + '\'' +
                ", id='" + id + '\'' +
                ", sc_name='" + sc_name + '\'' +
                ", xi='" + xi + '\'' +
                ", zhuany='" + zhuany + '\'' +
                ", nianj='" + nianj + '\'' +
                ", banj='" + banj + '\'' +
                '}';
    }
}
