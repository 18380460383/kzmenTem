package com.kzmen.sczxjf.bean;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * webview显示的数据
 */
public class ProjectBean implements Serializable{

    public double freemedia_cast;
    public String projectname;
    public int pid;
    public String projecturl;
    public String imageurl;
    public String enddate;
    public String nowdate;
    public int currentcost;
    public int totalcost;
    private boolean hasGetPrecent = false;
    private int precent;
    public String reward;

    public String getMoney() {
        DecimalFormat nf = new DecimalFormat("0.00");
        return nf.format(freemedia_cast / 100);
    }

    /**
     * 获取进度
     * @return
     */
    public int getPrecent() {
        if(hasGetPrecent)
            return precent;
        if(totalcost == 0) {
            precent = 0;
        } else {
            float pre = (float)currentcost / totalcost;
            DecimalFormat nf = new DecimalFormat("0.00");
            precent = (int)(Float.parseFloat(nf.format(pre)) * 100);
        }
        hasGetPrecent = true;
        return precent;
    }

    public static ProjectBean parseJson(JSONObject jsonObj) {
        Log.i("json", jsonObj.toString());
        Gson gson = new Gson();
        ProjectBean bean = gson.fromJson(jsonObj.toString(), ProjectBean.class);
        return bean;
    }

}
