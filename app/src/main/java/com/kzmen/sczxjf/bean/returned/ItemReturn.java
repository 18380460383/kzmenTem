package com.kzmen.sczxjf.bean.returned;

import com.kzmen.sczxjf.bean.InterestBean;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

/**
 * @author wu
 * @version 1.0
 */
public class ItemReturn {

    public String statuscode;
    public List<InterestBean> msg;

    public static ItemReturn parseJson(JSONObject jsonObj) {
        Gson gson = new Gson();
        ItemReturn bean = gson.fromJson(jsonObj.toString(), ItemReturn.class);
        return bean;
    }


    public InterestBean get(int i) {
        if(msg != null && i < msg.size()) {
            return msg.get(i);
        }
        return new InterestBean();
    }

    public String[] toArea() {
        if(msg == null) {
            return new String[0];
        }
        String[] arr = new String[msg.size()];
        for(int i=0;i<msg.size();i++) {
            arr[i] = msg.get(i).item;
        }
        return arr;
    }

}


