package com.kzmen.sczxjf.bean.kzbean;

/**
 * Created by pjj18 on 2017/9/3.
 */

public class CourseBean {
    private String title;//课程标题
    private String describe;//课程描述
    private String views;//学习人数
    private String cid;//课程ID
    private String type;//课程类型1初级2中级3高级

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CourseBean{" +
                "title='" + title + '\'' +
                ", describe='" + describe + '\'' +
                ", views='" + views + '\'' +
                ", cid='" + cid + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
