package com.kzmen.sczxjf.bean.kzbean;

/**
 * Created by pjj18 on 2017/9/2.
 */

public class TestListItemBean {
    private String id;
    private String title;
    private String views;
    private String image;
    private String iscepin;
    private String cepin_text;

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

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIscepin() {
        return iscepin;
    }

    public void setIscepin(String iscepin) {
        this.iscepin = iscepin;
    }

    public String getCepin_text() {
        return cepin_text;
    }

    public void setCepin_text(String cepin_text) {
        this.cepin_text = cepin_text;
    }

    @Override
    public String toString() {
        return "TestListItemBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", views='" + views + '\'' +
                ", image='" + image + '\'' +
                ", iscepin='" + iscepin + '\'' +
                ", cepin_text='" + cepin_text + '\'' +
                '}';
    }
}
