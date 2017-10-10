package com.kzmen.sczxjf.bean.kzbean;

/**
 * Created by pjj18 on 2017/9/2.
 */

public class CaseListItemBean {

    /**
     * id : 1
     * title : 辛识平：习近平贡献消除贫困的中国方案
     * describe : 不久前，在亚的斯亚贝巴举行的中非减贫发展高端对话
     * image : http://api.kzmen.cn/Uploads/Picture/2017-08-20/5999a79eefb82.png
     * views : 8.8万
     * update_time : 2017-08-23
     */

    private String id;
    private String title;
    private String describe;
    private String image;
    private String views;
    private String update_time;

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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "CaseListItemBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", describe='" + describe + '\'' +
                ", image='" + image + '\'' +
                ", views='" + views + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}
