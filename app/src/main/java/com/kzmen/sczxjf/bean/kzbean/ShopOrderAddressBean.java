package com.kzmen.sczxjf.bean.kzbean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/24.
 */

public class ShopOrderAddressBean implements Serializable{
    private String userName;
    private String userPhone;
    private String userAddress;
    private String userCountry;
    private String userYB;
    private String province;
    private String city;
    private String area;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getUserYB() {
        return userYB;
    }

    public void setUserYB(String userYB) {
        this.userYB = userYB;
    }

    @Override
    public String toString() {
        return "ShopOrderAddressBean{" +
                "userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userCountry='" + userCountry + '\'' +
                ", userYB='" + userYB + '\'' +
                '}';
    }
}
