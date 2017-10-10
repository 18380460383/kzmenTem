package com.kzmen.sczxjf.bean.entitys;

import com.google.gson.Gson;
import com.kzmen.sczxjf.EnMoneySelectEntity;

/**
 * describe:
 * notice:
 * Created by FuPei on 2016/6/11.
 */
public class ReporterEntity extends EnMoneySelectEntity {


    /**
     * mobile_no : 13550086211
     * avatar :
     * email : 121223613@qq.com
     * register_source : 1
     * create_time : 2015
     * balance_money : 0.00
     * source_impl :
     * source_val : 614
     * name : 刘锐
     * uid : 63187
     * nickname :
     * media_type : 报纸
     * media_section : 新闻
     * is_shot : 0
     * account_status : 1
     * sex : 1
     * city : 成都
     * province : 四川
     * country : 中国
     * submit_time : null
     * editors_status : 230
     * is_interview : 1
     * is_ask : 1
     * recommend_index : 2
     * last_update_time : 2015
     * finish_order_count : 0
     * shift_order_count : 0
     * refuse_order_count : 0
     * refuse_remark :
     * order_count : 0
     * media_text :
     * ask : 0.0
     * service : 30.0
     * interview : 0.0
     */

    private String mobile_no;
    private String avatar;
    private String balance_money;
    private String name;
    private String nickname;
    private String media_type;
    private String media_section;
    private String city;
    private String province;
    private String country;
    private String editors_status;
    private String is_interview;
    private String is_ask;
    private String recommend_index;
    private String media_text;
    private String ask;
    private String service;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBalance_money() {
        return balance_money;
    }

    public void setBalance_money(String balance_money) {
        this.balance_money = balance_money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getMedia_section() {
        return media_section;
    }

    public void setMedia_section(String media_section) {
        this.media_section = media_section;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEditors_status() {
        return editors_status;
    }

    public void setEditors_status(String editors_status) {
        this.editors_status = editors_status;
    }

    public String getIs_interview() {
        return is_interview;
    }

    public void setIs_interview(String is_interview) {
        this.is_interview = is_interview;
    }

    public String getIs_ask() {
        return is_ask;
    }

    public void setIs_ask(String is_ask) {
        this.is_ask = is_ask;
    }

    public String getRecommend_index() {
        return recommend_index;
    }

    public void setRecommend_index(String recommend_index) {
        this.recommend_index = recommend_index;
    }

    public String getMedia_text() {
        return media_text;
    }

    public void setMedia_text(String media_text) {
        this.media_text = media_text;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public float getMoney() {
        try{
            return Float.valueOf(getService());
        } catch (Exception e) {
            System.out.println("meiqianmeiqianmeiqianmeiqianmeiqianmeiqianmeiqianmeiqianmeiqian");
        }
        return 0;
    }

    @Override
    public String getIdentify() {
        return mobile_no;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
