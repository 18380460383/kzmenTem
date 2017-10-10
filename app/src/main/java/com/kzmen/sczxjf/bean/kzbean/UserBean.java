package com.kzmen.sczxjf.bean.kzbean;

import java.io.Serializable;

/**
 * Created by pjj18 on 2017/9/1.
 */

public class UserBean  implements Serializable{

    /**
     * uid : 5001
     * username : 手机用户****5399
     * role : 0
     * avatar :
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzYWx0IjoiZjZmNzIzIiwiaW52aXRlX2NvZGUiOiJNU1VMVEoiLCJ0aW1lIjoxNTA0ODc4MjU0fQ.udZLOQO6chG2Y_uB8-qMU_L47nc1dCcrpgXM_93mj3E
     * state : 1
     * weixin :
     * hotnum : 1
     * phone : 15801035399
     * score : 8
     * balance : 0
     * invite_code : MSULTJ
     * sign : 0dfbba9f79a327a8c59da1dfcb8ca4c2
     */

    private String uid;
    private String username;
    private String role;
    private String avatar;
    private String token;
    private String state;
    private String weixin;
    private String hotnum;
    private String phone;
    private String score;
    private String balance;
    private String invite_code;
    private String sign;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getHotnum() {
        return hotnum;
    }

    public void setHotnum(String hotnum) {
        this.hotnum = hotnum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", avatar='" + avatar + '\'' +
                ", token='" + token + '\'' +
                ", state='" + state + '\'' +
                ", weixin='" + weixin + '\'' +
                ", hotnum='" + hotnum + '\'' +
                ", phone='" + phone + '\'' +
                ", score='" + score + '\'' +
                ", balance='" + balance + '\'' +
                ", invite_code='" + invite_code + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
