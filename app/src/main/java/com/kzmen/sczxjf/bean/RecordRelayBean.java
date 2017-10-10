package com.kzmen.sczxjf.bean;


import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * 转发明细
 */
public class RecordRelayBean {

    public int ischeck;
    public int isneedscreenshot;
    public int isscreenshot;
    public double freemedia_cast;
    public String relay_date;
    public String projectname;
    public int pid;
    public String relay_income;

    public static RecordRelayBean parseJson(JSONObject jsonObject){
        Gson gson = new Gson();
        RecordRelayBean bean = gson.fromJson(jsonObject.toString(),RecordRelayBean.class);

        return bean;
    }

    public String getMoney() {
        DecimalFormat nf = new DecimalFormat("0.00");
        return nf.format(freemedia_cast / 100);
    }



}
