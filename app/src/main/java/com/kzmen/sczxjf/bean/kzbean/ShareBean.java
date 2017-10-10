package com.kzmen.sczxjf.bean.kzbean;

import java.io.Serializable;

/**
 * Created by pjj18 on 2017/10/2.
 */

public class ShareBean implements Serializable {
    private String title;
    private String des;
    private String image;
    private String linkurl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }
}
