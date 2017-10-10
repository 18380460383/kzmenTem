package com.kzmen.sczxjf.ebean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/29.
 */
public class User implements Serializable{


    /**
     * own_id : 63235
     * own_name : 312312hrfhhrr个护肤
     * email : null
     * own_mob_no : 13594015206
     * own_logname : 13594015206
     * acc_status : 479
     * acc_status_str : 待上传资料
     * type_id : 148
     * type_id_str : 财经
     * amount_account : 5105.05
     * company_name : 213123
     * company_address : 3123123vvvbvvbb
     * user_rank : 0
     * is_banding : 1
     * faq : http://www.baidu.com/
     */

    private String own_id;
    private String own_name;
    private String email;
    private String own_mob_no;
    private String own_logname;
    private String acc_status;
    private String acc_status_str;
    private String type_id;
    private String type_id_str;
    private String amount_account;
    private String company_name;
    private String company_address;
    private String user_rank;
    private int is_banding;
    private String faq;
    private String appkey;
    private String sing;
    private String token;
    private String userkey;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getSing() {
        return sing;
    }

    public void setSing(String sing) {
        this.sing = sing;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserkey() {
        return userkey;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    public void setOwn_id(String own_id) {
        this.own_id = own_id;
    }

    public void setOwn_name(String own_name) {
        this.own_name = own_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOwn_mob_no(String own_mob_no) {
        this.own_mob_no = own_mob_no;
    }

    public void setOwn_logname(String own_logname) {
        this.own_logname = own_logname;
    }

    public void setAcc_status(String acc_status) {
        this.acc_status = acc_status;
    }

    public void setAcc_status_str(String acc_status_str) {
        this.acc_status_str = acc_status_str;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public void setType_id_str(String type_id_str) {
        this.type_id_str = type_id_str;
    }

    public void setAmount_account(String amount_account) {
        this.amount_account = amount_account;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public void setUser_rank(String user_rank) {
        this.user_rank = user_rank;
    }

    public void setIs_banding(int is_banding) {
        this.is_banding = is_banding;
    }

    public void setFaq(String faq) {
        this.faq = faq;
    }

    public String getOwn_id() {
        return own_id;
    }

    public String getOwn_name() {
        return own_name;
    }

    public String getEmail() {
        return email;
    }

    public String getOwn_mob_no() {
        return own_mob_no;
    }

    public String getOwn_logname() {
        return own_logname;
    }

    public String getAcc_status() {
        return acc_status;
    }

    public String getAcc_status_str() {
        return acc_status_str;
    }

    public String getType_id() {
        return type_id;
    }

    public String getType_id_str() {
        return type_id_str;
    }

    public String getAmount_account() {
        return amount_account;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getCompany_address() {
        return company_address;
    }

    public String getUser_rank() {
        return user_rank;
    }

    public int getIs_banding() {
        return is_banding;
    }

    public String getFaq() {
        return faq;
    }

    @Override
    public String toString() {
        return "User{" +
                "own_id='" + own_id + '\'' +
                ", own_name='" + own_name + '\'' +
                ", email='" + email + '\'' +
                ", own_mob_no='" + own_mob_no + '\'' +
                ", own_logname='" + own_logname + '\'' +
                ", acc_status='" + acc_status + '\'' +
                ", acc_status_str='" + acc_status_str + '\'' +
                ", type_id='" + type_id + '\'' +
                ", type_id_str='" + type_id_str + '\'' +
                ", amount_account='" + amount_account + '\'' +
                ", company_name='" + company_name + '\'' +
                ", company_address='" + company_address + '\'' +
                ", user_rank='" + user_rank + '\'' +
                ", is_banding=" + is_banding +
                ", faq='" + faq + '\'' +
                '}';
    }
}
