package com.kzmen.sczxjf.bean;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * 说明：
 * note：
 * Created by FuPei
 * on 2016/1/26 at 9:55
 */
public class ExchangeBean {

    /**
     * id : 2
     * orider: 210321351
     * title : 衣帽架落地实木挂衣架楠竹衣帽架
     * image : http://192.168.0.111:9012/Uploads/Picture/2016-01-22/56a1efef119ed.jpg
     * express : 全峰快递
     * express_number : 20569369001
     * score : 3999
     * money : 3900
     * state : 已发货
     */

    private String id;
    private String gid;
    private String title;
    private String image;
    private String express;
    private String express_number;
    private String score;
    private String money;
    private int state;
    private String type;
    private String redeemcode;
    private String state_str;
    private String order;

    public String getOrider() {
        return order;
    }

    public void setOrider(String orider) {
        this.order = orider;
    }

    public String getRedeemcode() {
        return redeemcode;
    }

    public ExchangeBean setRedeemcode(String redeemcode) {
        this.redeemcode = redeemcode;
        return this;
    }

    public String getType() {
        return type;
    }

    public ExchangeBean setType(String type) {
        this.type = type;
        return this;
    }

    public String getGid() {
        return gid;
    }

    public ExchangeBean setGid(String gid) {
        this.gid = gid;
        return this;
    }

    public String getState_str() {
        return state_str;
    }

    public void setState_str(String state_str) {
        this.state_str = state_str;
    }

    public static ExchangeBean parseEntity(JSONObject jsonObject) {
//        BaseBean bean = BaseBean.parseEntity(jsonObject);
        return new Gson().fromJson(jsonObject.toString(), ExchangeBean.class);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public void setExpress_number(String express_number) {
        this.express_number = express_number;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getExpress() {
        return express;
    }

    public String getExpress_number() {
        return express_number;
    }

    public String getScore() {
        return score;
    }

    public String getMoney() {
        return money;
    }

    public int getState() {
        return state;
    }
}
