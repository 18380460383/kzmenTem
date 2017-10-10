package com.kzmen.sczxjf.bean;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * 广告条目
 */
public class MoneyBean {



    public String channel;
    public String channel_id;
    public String channel_name;
    public double miss_money;
    public double last_money;
    public double earn_money;
    public double freeze_money;
    public double focheck_money;
    public double balance;
    public String score;
    public  String scorehelpurl;

    public String getScorehelpurl() {
        return scorehelpurl;
    }

    public void setScorehelpurl(String scorehelpurl) {
        this.scorehelpurl = scorehelpurl;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getFreeze_money() {
        DecimalFormat nf = new DecimalFormat("0.00");
        return nf.format(freeze_money / 100);
    }

    public void setFreeze_money(double freeze_money) {
        this.freeze_money = freeze_money;
    }

    public String getFocheck_money() {
        DecimalFormat nf = new DecimalFormat("0.00");
        return nf.format(focheck_money / 100);
    }

    public void setFocheck_money(double focheck_money) {
        this.focheck_money = focheck_money;
    }

    public String getEarnMoney() {
        DecimalFormat nf = new DecimalFormat("0.00");
        return nf.format(earn_money / 100);
    }
    public String getMissMoney() {
        DecimalFormat nf = new DecimalFormat("0.00");
        return nf.format(miss_money / 100);
    }
    public String getLastMoney() {
        DecimalFormat nf = new DecimalFormat("0.00");
        return nf.format(last_money / 100);
    }
    public String getBalance() {
        DecimalFormat nf = new DecimalFormat("0.00");
        return nf.format(balance / 100);
    }



    public static MoneyBean parseJson(JSONObject jsonObj) {
        Gson gson = new Gson();
        MoneyBean bean = gson.fromJson(jsonObj.toString(), MoneyBean.class);

        return bean;
    }

    @Override
    public String toString() {
        return "MoneyBean{" +
                "earn_money=" + earn_money +
                ", channel='" + channel + '\'' +
                ", channel_id='" + channel_id + '\'' +
                ", channel_name='" + channel_name + '\'' +
                ", miss_money=" + miss_money +
                ", last_money=" + last_money +
                ", balance=" + balance +
                '}';
    }
}
