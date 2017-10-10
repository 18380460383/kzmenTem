package com.kzmen.sczxjf.bean.returned;

import com.kzmen.sczxjf.bean.DetialBean;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

/**
 * @author wu
 * @version 1.0
 */
public class DetialReturn {

    public String statuscode;
    public List<DetialBean> data;
    public String msg;

    public static DetialReturn parseJson(JSONObject jsonObj) {
        Gson gson = new Gson();
        DetialReturn bean = gson.fromJson(jsonObj.toString(), DetialReturn.class);
        return bean;
    }

}


