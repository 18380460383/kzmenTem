package com.kzmen.sczxjf.bean.kzbean;

import java.util.List;

/**
 * Created by pjj18 on 2017/8/9.
 */

public class CourseListBean {
    private String tid;//老师ID
    private String tid_title;//老师头衔
    private String name;//老师姓名
    private String image;//老师形象
    private String describe;//老师描述
    private List<CourseBean> course_list;//课程数组

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTid_title() {
        return tid_title;
    }

    public void setTid_title(String tid_title) {
        this.tid_title = tid_title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<CourseBean> getCourse_list() {
        return course_list;
    }

    public void setCourse_list(List<CourseBean> course_list) {
        this.course_list = course_list;
    }

    @Override
    public String toString() {
        return "CourseListBean{" +
                "tid='" + tid + '\'' +
                ", tid_title='" + tid_title + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", describe='" + describe + '\'' +
                ", course_list=" + course_list +
                '}';
    }
}
