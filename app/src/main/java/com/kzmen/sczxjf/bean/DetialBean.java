package com.kzmen.sczxjf.bean;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * 条目
 */
public class DetialBean {

    public double freemedia_cast;
    public String projectname;
    public String projecturl;
    public String imageurl;
    public String enddate;
    public String nowdate;
    public int isrelay;
    public int isscreenshot;
    public int isneedscreenshot;
    public int ismoney;
    private boolean hasGetPrecent = false;
    private int precent;
    public String reward;
    public String url;
    public int sec;

    public String getMoney() {
        DecimalFormat nf = new DecimalFormat("0.00");
        if(1==ismoney) {
            return nf.format(freemedia_cast / 100);
        }else if(0==ismoney){
            return "0.00";
        }
        return "0.00";
    }



    public static DetialBean parseJson(JSONObject jsonObj) {
        Gson gson = new Gson();
        DetialBean bean = gson.fromJson(jsonObj.toString(), DetialBean.class);

        return bean;
    }




}
