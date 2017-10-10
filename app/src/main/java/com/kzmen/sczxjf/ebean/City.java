package com.kzmen.sczxjf.ebean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/4.
 */
public class City {
    private String item;
    private List<String> areaList=new ArrayList<String>();

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public List<String> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<String> areaList) {
        this.areaList = areaList;
    }

    @Override
    public String toString() {
        return "City{" +
                "item='" + item + '\'' +
                ", areaList=" + areaList +
                '}';
    }
}
