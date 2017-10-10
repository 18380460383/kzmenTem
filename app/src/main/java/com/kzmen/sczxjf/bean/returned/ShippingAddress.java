package com.kzmen.sczxjf.bean.returned;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/27.
 */
public class ShippingAddress implements Serializable{

    /**
     * id : 3
     * nickname : Frcfcfff
     * tel : 18323847977
     * province : 四川
     * city : 成都
     * area : 成华区
     * address : Hghrggrgggg
     * zip : 522369
     */

    private String id;
    private String nickname;
    private String tel;
    private String province;
    private String city;
    private String area;
    private String address;
    private String zip;

    public ShippingAddress() {

    }

    public ShippingAddress(String id, String nickname, String tel, String province, String city, String area, String address, String zip) {
        this.id = id;
        this.nickname = nickname;
        this.tel = tel;
        this.province = province;
        this.city = city;
        this.area = area;
        this.address = address;
        this.zip = zip;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTel() {
        return tel;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getArea() {
        return area;
    }

    public String getAddress() {
        return address;
    }

    public String getZip() {
        return zip;
    }
}
