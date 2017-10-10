package com.kzmen.sczxjf.ebean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/4.
 */
public class Province {
    private String item;
    private List<City> listCity=new ArrayList<City>();

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public List<City> getListCity() {
        return listCity;
    }

    public void setListCity(List<City> listCity) {
        this.listCity = listCity;
    }

    @Override
    public String toString() {
        return "Province{" +
                "item='" + item + '\'' +
                ", listCity=" + listCity +
                '}';
    }
}
