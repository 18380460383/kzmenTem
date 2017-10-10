package com.kzmen.sczxjf.bean.returned;

/**
 * Created by Administrator on 2016/1/12.
 */
public class Activitys {

    /**
     * title : 摇一摇
     * imageurl : http://192.168.0.111:9012/Uploads/Picture/2016-01-12/5694890ac195d.png
     * linkurl : http://192.168.0.111:9012/wx_yaoyiyao/13.php
     * type : 1
     * startdate : 2016-01-12 13:03:08
     * enddate : 2017-01-12 13:33:08
     */
    private String id;
    private String title;
    private String imageurl;
    private String linkurl;
    private String hits;
    /**
     * 判断是否是连接
     * 0：App内部
     * 1.链接
     */
    private String type;
    private String startdate;
    private String enddate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getTitle() {
        return title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public String getType() {
        return type;
    }

    public String getStartdate() {
        return startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    @Override
    public String toString() {
        return "Activitys{" +
                "title='" + title + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", linkurl='" + linkurl + '\'' +
                ", type='" + type + '\'' +
                ", startdate='" + startdate + '\'' +
                ", enddate='" + enddate + '\'' +
                '}';
    }
}
