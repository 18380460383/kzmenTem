package com.kzmen.sczxjf.bean.kzbean;

import java.io.Serializable;

/**
 * Created by pjj18 on 2017/9/7.
 */

public class OrderBean  implements Serializable{
    private String uid;//用户id
    private String order;//订单号
    private String money;//订单金额
    private String title;//订单标题
    private String balance;//余额
    private String source;//当前订单来源

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "uid='" + uid + '\'' +
                ", order='" + order + '\'' +
                ", money='" + money + '\'' +
                ", title='" + title + '\'' +
                ", balance='" + balance + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
