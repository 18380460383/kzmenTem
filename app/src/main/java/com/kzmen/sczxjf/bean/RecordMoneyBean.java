package com.kzmen.sczxjf.bean;


import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * 提现明细
 */
public class RecordMoneyBean {

    public String date;
    public double money;
    public int isdraw;

    public static RecordMoneyBean parseJson(JSONObject jsonObject){
        Gson gson = new Gson();
        RecordMoneyBean bean = gson.fromJson(jsonObject.toString(),RecordMoneyBean.class);

        return bean;
    }

    public String getMoney() {
        DecimalFormat nf = new DecimalFormat("0.00");
        return nf.format(money / 100);
    }



}
