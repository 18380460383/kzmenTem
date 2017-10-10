package com.kzmen.sczxjf.bean.kzbean;

/**
 * Created by Administrator on 2017/8/8.
 */

public class MainColumnItemBean {
    private String name;
    private int id;

    public MainColumnItemBean(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
