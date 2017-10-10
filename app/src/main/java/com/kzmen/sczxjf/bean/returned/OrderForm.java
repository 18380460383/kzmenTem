package com.kzmen.sczxjf.bean.returned;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/27.
 */
public class OrderForm implements Serializable{

    /**
     * uid : 93364
     * order : 2016012716535443283
     * money : 2100
     * title : 韩国新品潮流女包
     * balance : 600
     * score :积分
     */

    private String uid;
    private String order;
    private double money;
    private String title;
    private double balance;
    private String score;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getUid() {
        return uid;
    }

    public String getOrder() {
        return order;
    }

    public double getMoney() {
        return money;
    }

    public String getTitle() {
        return title;
    }

    public double getBalance() {
        return balance;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
