package com.kzmen.sczxjf.bean.kzbean;

import java.io.Serializable;

/**
 * Created by pjj18 on 2017/9/26.
 */

public class AddressBean implements Serializable{

    /**
     * zip : 246000
     * area : 枞阳县
     * address : 街道
     * nickname : 123
     * tel : 18380460383
     * province : null
     * aid : 3
     * city : 安庆市
     */

    private String zip;
    private String area;
    private String address;
    private String nickname;
    private String tel;
    private String province;
    private String aid;
    private String city;

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
