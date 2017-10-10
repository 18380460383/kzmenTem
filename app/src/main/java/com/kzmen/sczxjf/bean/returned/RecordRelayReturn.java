package com.kzmen.sczxjf.bean.returned;

import com.kzmen.sczxjf.bean.RecordRelayBean;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

/**
 * @author wu
 * @version 1.0
 */
public class RecordRelayReturn {

    public String code;
    public List<RecordRelayBean> data;
    public String msg;

    public static RecordRelayReturn parseJson(JSONObject jsonObj) {
        Gson gson = new Gson();
        RecordRelayReturn bean = gson.fromJson(jsonObj.toString(), RecordRelayReturn.class);
        return bean;
    }

}


