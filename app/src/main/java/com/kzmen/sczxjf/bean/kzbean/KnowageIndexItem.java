package com.kzmen.sczxjf.bean.kzbean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pjj18 on 2017/9/9.
 */

public class KnowageIndexItem implements Serializable{

    /**
     * id : 1
     * title : 信用知识，快速问专家
     * image : http://api.kzmen.cn/Uploads/Picture/2017-09-09/59b39b8df376b.png
     * des : 信用提升，维护，利用
     * options : ["100%解答","认证专家学者","快速响应"]
     * share_title : 信用知识，快速问专家分享标题
     * share_des : 信用提升，维护，利用分享摘要
     * share_image : http://api.kzmen.cn/Uploads/Picture/2017-09-09/59b39b8df376b.png
     * share_linkurl : https://www.baidu.com/
     */

    private String id;
    private String title;
    private String image;
    private String des;
    private String share_title;
    private String share_des;
    private String share_image;
    private String share_linkurl;
    private List<String> options;

    public KnowageIndexItem(String id, String title, String image, String des) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.des = des;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    public String getShare_des() {
        return share_des;
    }

    public void setShare_des(String share_des) {
        this.share_des = share_des;
    }

    public String getShare_image() {
        return share_image;
    }

    public void setShare_image(String share_image) {
        this.share_image = share_image;
    }

    public String getShare_linkurl() {
        return share_linkurl;
    }

    public void setShare_linkurl(String share_linkurl) {
        this.share_linkurl = share_linkurl;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
