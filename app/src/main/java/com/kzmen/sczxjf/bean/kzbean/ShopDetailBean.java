package com.kzmen.sczxjf.bean.kzbean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pjj18 on 2017/9/26.
 */

public class ShopDetailBean implements Serializable{

    /**
     * id : 2
     * isexchange : 1
     * title : 名称二
     * money : 0
     * score : 888
     * images : []
     * image : http://localhost:9005/Uploads/Picture/2017-08-26/59a13258bd4a0.jpg
     * type : 0
     * stocks : 200
     * iscollect : 0
     * content_url : http://api.kzmen.cn/api.php/Goods/getGoodsContent/gid/2
     * postage : 1900
     */

    private String id;
    private int isexchange;
    private String title;
    private String money;
    private String score;
    private String image;
    private String type;
    private String stocks;
    private int iscollect;
    private String content_url;
    private String postage;
    private List<String> images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsexchange() {
        return isexchange;
    }

    public void setIsexchange(int isexchange) {
        this.isexchange = isexchange;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStocks() {
        return stocks;
    }

    public void setStocks(String stocks) {
        this.stocks = stocks;
    }

    public int getIscollect() {
        return iscollect;
    }

    public void setIscollect(int iscollect) {
        this.iscollect = iscollect;
    }

    public String getContent_url() {
        return content_url;
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
