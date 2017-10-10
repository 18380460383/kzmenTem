package com.kzmen.sczxjf.bean;

/**
 * Created by 杨操 on 2016/1/22.
 */
public class ShopBean  {
    private int a;
    private String id;
    private String title;
    private String score;
    private String image;

    public void setA(int a) {
        this.a = a;
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

    public ShopBean(int a) {
        this.a = a;
    }

    public int getA() {
        return a;
    }
}
