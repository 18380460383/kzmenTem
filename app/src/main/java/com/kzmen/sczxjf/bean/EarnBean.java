package com.kzmen.sczxjf.bean;

/**
 * 说明：
 * note：
 * Created by FuPei
 * on 2015/12/1 at 15:41
 */
public class EarnBean {
    public double money;
    public String datetime;
    public String title;

    @Override
    public String toString() {
        return "title = " + title + ", datetime = " + datetime + ", money = " + money;
    }
}
