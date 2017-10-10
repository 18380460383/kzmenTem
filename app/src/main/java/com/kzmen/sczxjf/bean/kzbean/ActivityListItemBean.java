package com.kzmen.sczxjf.bean.kzbean;

/**
 * Created by pjj18 on 2017/9/2.
 */

public class ActivityListItemBean {

    /**
     * id : 1
     * title : 金秋十月，换种姿势解锁全套课程
     * imageurl : http://localhost:9005/Uploads/Picture/2017-08-12/598e79f84636e.png
     * linkurl : https://www.baidu.com/
     * type : 1
     * hits : 671
     * startdate : 2017-08-23
     * enddate : 2017-08-24
     * jiezhisj : 活动已结束
     */

    private String id;
    private String title;
    private String imageurl;
    private String linkurl;
    private String type;
    private String hits;
    private String startdate;
    private String enddate;
    private String jiezhisj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
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

    public String getJiezhisj() {
        return jiezhisj;
    }

    public void setJiezhisj(String jiezhisj) {
        this.jiezhisj = jiezhisj;
    }
}
