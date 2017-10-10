package com.kzmen.sczxjf.bean.kzbean;

/**
 * Created by pjj18 on 2017/9/3.
 */

import java.util.List;

/**
 * 阶段信息
 */

public class CourseStageBean {
    private String sid;
    private String stage_name;
    private String sort;
    private int isunlock;
    private List<CourseKejianBean>kejian_list;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getStage_name() {
        return stage_name;
    }

    public void setStage_name(String stage_name) {
        this.stage_name = stage_name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getIsunlock() {
        return isunlock;
    }

    public void setIsunlock(int isunlock) {
        this.isunlock = isunlock;
    }

    public List<CourseKejianBean> getKejian_list() {
        return kejian_list;
    }

    public void setKejian_list(List<CourseKejianBean> kejian_list) {
        this.kejian_list = kejian_list;
    }

    @Override
    public String toString() {
        return "CourseStageBean{" +
                "sid='" + sid + '\'' +
                ", stage_name='" + stage_name + '\'' +
                ", sort='" + sort + '\'' +
                ", isunlock=" + isunlock +
                ", kejian_list=" + kejian_list +
                '}';
    }
}
