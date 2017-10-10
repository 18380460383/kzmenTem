package com.kzmen.sczxjf.bean.request;

import com.kzmen.sczxjf.bean.InterestBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wu
 * @version 1.0
 * @created 2015/10/22
 */
public class ChangeInfo {

    public String uid;
    public String token;
    public String gender;
    public String profession;
    public String province;
    public String city;
    public String area;
    public int age;
    public int fannum;
    public int did;
    public String school="";
    public String xi="";
    public String banj="";
    public String nianj="";
    public String zhuany="";
    public List<Integer> iid_array = new ArrayList<>();
    public String roles_money;

/*    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }*/

    public void setIid(List<InterestBean> interest) {
        if(interest == null)
            return;
        for(InterestBean bean : interest) {
            if(bean.checked) {
                iid_array.add(bean.iid);
            }
        }
    }

    @Override
    public String toString() {
        return "{" +
                "uid=" + uid +
                ", token='" + token + '\'' +
                ", gender='" + gender + '\'' +
                ", profession='" + profession + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", age=" + age +
                ", fannum=" + fannum +
                ", did=" + did +
                ", school='" + school + '\'' +
                ", xi='" + xi + '\'' +
                ", banj='" + banj + '\'' +
                ", nianj='" + nianj + '\'' +
                ", zhuany='" + zhuany + '\'' +
                ", iid_array=" + iid_array +
                '}';
    }
}
