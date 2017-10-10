package com.kzmen.sczxjf.bean.entitys;

import com.google.gson.Gson;

/**
 * describe:
 * notice:
 * Created by FuPei on 2016/5/27.
 */
public class EnterPriseAppEntity {

    /**
     * uid : 73
     * identity : 138
     * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiI3MyIsImxvZ2luX25hbWUiOiJsZWlrYWl4aW4iLCJtb2JpbGVfbm8iOiIxNTIwODMzMDIzMSIsImlkZW50aXR5IjoiMTM4Iiwic2VjdXJpdHlfa2V5IjoiNmI1MTRlNDUyNTJmM2Q2YTNmNGE4MTUwNTlhNjQxNjMiLCJhdmF0YXIiOiIiLCJuYW1lIjoi6Zu35byA5pawMiIsImV4cCI6MTQ2NDk0ODIzMH0.FGzCDc3eITKiFDxo_HA8dzwvwm_kezhA-gjzKhD5wsU
     * unionid : null
     * name : 雷开新2
     * login_name : leikaixin
     * avatar :
     * userkey : 6b514e45252f3d6a3f4a815059a64163
     * security_key : 6b514e45252f3d6a3f4a815059a64163
     * mobile_no : 15208330231
     */

    private String uid;
    private String identity;
    private String token;
    private Object unionid;
    private String name;
    private String login_name;
    private String avatar;
    private String userkey;
    private String security_key;
    private String mobile_no;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getUnionid() {
        return unionid;
    }

    public void setUnionid(Object unionid) {
        this.unionid = unionid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserkey() {
        return userkey;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    public String getSecurity_key() {
        return security_key;
    }

    public void setSecurity_key(String security_key) {
        this.security_key = security_key;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
