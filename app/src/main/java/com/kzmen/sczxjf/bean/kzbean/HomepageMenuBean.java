package com.kzmen.sczxjf.bean.kzbean;

/**
 * Created by pjj18 on 2017/9/3.
 */

public class HomepageMenuBean {


    /**
     * mid : 1
     * name : 课程
     * icon : http://localhost:9005/Uploads/Picture/2017-08-11/598dd063d4663.png
     * plate : course
     */

    private String mid;
    private String name;
    private String icon;
    private String plate;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    @Override
    public String toString() {
        return "HomepageMenuBean{" +
                "mid='" + mid + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", plate='" + plate + '\'' +
                '}';
    }
}
