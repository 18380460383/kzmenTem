package com.kzmen.sczxjf.bean.returned;

import com.kzmen.sczxjf.bean.ProjectBean;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

/**
 * @author wu
 * @version 1.0
 */
public class PageListReturn {

    public String statuscode;
    public List<ProjectBean> msg;

    public static PageListReturn parseJson(JSONObject jsonObj) {
        Gson gson = new Gson();
        PageListReturn bean = gson.fromJson(jsonObj.toString(), PageListReturn.class);
        return bean;
    }

}


