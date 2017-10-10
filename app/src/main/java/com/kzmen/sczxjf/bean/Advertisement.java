package com.kzmen.sczxjf.bean;

/**
 * Created by Administrator on 2015/12/31.
 */
public class Advertisement {


    /**
     * 广告传到app的方式类型
     */
    private String type;
    private String times;
    private String linkurl;
    private String imgurl;
    private String id;
    private String startdate;
    private String enddate;
    private String title;
    private String subtitle;

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getType() {
        return type;
    }

    public String getTimes() {
        return times;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public String getImgurl() {
        return imgurl;
    }
}
