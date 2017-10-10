package com.kzmen.sczxjf.bean.kzbean;

/**
 * Created by pjj18 on 2017/9/3.
 */

public class HomeBanerBean {

    /**
     * hid : 2
     * title : 内部跳转
     * imageurl : http://localhost:9005/Uploads/Picture/2017-08-26/59a16145ee560.jpg
     * linkurl : 1
     * linktype : 1
     * className : ankec
     * parameter : iosprem
     */

    private String hid;
    private String title;
    private String imageurl;
    private String linkurl;
    private String linktype;
    private String className;
    private String parameter;

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
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

    public String getLinktype() {
        return linktype;
    }

    public void setLinktype(String linktype) {
        this.linktype = linktype;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public String toString() {
        return "HomeBanerBean{" +
                "hid='" + hid + '\'' +
                ", title='" + title + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", linkurl='" + linkurl + '\'' +
                ", linktype='" + linktype + '\'' +
                ", className='" + className + '\'' +
                ", parameter='" + parameter + '\'' +
                '}';
    }
}
