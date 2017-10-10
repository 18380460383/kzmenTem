package com.kzmen.sczxjf.bean;

import android.text.TextUtils;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.bean.request.ChangeInfo;
import com.kzmen.sczxjf.bean.request.School;
import com.kzmen.sczxjf.bean.user.User_For_pe;
import com.kzmen.sczxjf.util.EshareLoger;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class UserDetailInfo {

    public int level;
    public String province;
    public String area;
    public String city;
    public String gender;
    public List<InterestBean> interest;
    public List<InterestBean> industry;
    public String uid;
    public int fannum;
    public int age;
    public String username;
    public String profession;
    public String imageurl;
    public List<School> school;
    public int did;
    public List<Roles> roles;
    public String roles_money;
    public String fannum_url;
    public String emali;

    public static UserDetailInfo parseJson(JSONObject jsonObject) {
        EshareLoger.logI("gson设置info:" + jsonObject.toString());
        Gson gson = new Gson();
        UserDetailInfo bean = gson.fromJson(jsonObject.toString(), UserDetailInfo.class);
        return bean;
    }

    public ChangeInfo toChangeInfo() {
        ChangeInfo info = new ChangeInfo();
        User_For_pe peUser = AppContext.getInstance().getPEUser();
        info.uid = peUser.getUid();
        info.token = peUser.getToken();
        info.gender = gender;
        info.profession = profession;
        info.province = province;
        info.city = city;
        info.area = area;
        info.age = age;
        info.fannum = fannum;
        info.roles_money = roles_money;
        if (did == 0) {
            info.did = industry.get(0).did;
        } else {
            info.did = did;
        }
        return info;
    }

    public static UserDetailInfo parseJsonReturn(JSONObject jsonObject) {
        UserDetailInfo info = new UserDetailInfo();
        JSONObject beanJson = null;
        try {
            JSONArray msg = jsonObject.optJSONArray("msg");
            beanJson = msg.optJSONObject(0);
            info.level = beanJson.optInt("level", 0);
            info.uid = beanJson.optString("uid", "");
            info.fannum = beanJson.optInt("fannum", 0);
            info.age = beanJson.optInt("age", 0);
            info.area = beanJson.optString("area", "");
            info.city = beanJson.optString("city", "");
            info.gender = beanJson.optString("gender", "");
            info.username = beanJson.optString("username", "");
            info.profession = beanJson.optString("profession", "");
            info.imageurl = beanJson.optString("imageurl", "");


//            JSONArray school1 = beanJson.optJSONArray("school");
//            if(null!=school1) {
//                JSONObject school = school1.getJSONObject(0);
//                if (null != school) {
//                    info.school = new Gson().fromJson(school.toString(), School.class);
//                }
//            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        try {
            info.interest = new ArrayList<>();
            JSONArray interest = beanJson.getJSONArray("interest");
            for (int i = 0; i < interest.length(); i++) {
                JSONObject jsonItem = interest.optJSONObject(i);
                InterestBean beanItem = new InterestBean();
                beanItem.iid = jsonItem.optInt("iid", 0);
                beanItem.item = jsonItem.optString("item", "");

                info.interest.add(beanItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            info.industry = new ArrayList<>();
            JSONArray industry = beanJson.getJSONArray("industry");
            for (int i = 0; i < industry.length(); i++) {
                JSONObject jsonItem = industry.optJSONObject(i);
                InterestBean beanItem = new InterestBean();
                beanItem.did = jsonItem.optInt("did", 0);
                beanItem.item = jsonItem.optString("item", "");

                info.industry.add(beanItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    @Override
    public String toString() {
        return "UserDetailInfo{" +
                "level=" + level +
                ", area='" + area + '\'' +
                ", city='" + city + '\'' +
                ", gender='" + gender + '\'' +
                ", interest=" + interest +
                ", industry=" + industry +
                ", uid=" + uid +
                ", fannum=" + fannum +
                ", age=" + age +
                ", username='" + username + '\'' +
                ", profession='" + profession + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", school=" + school +
                ", did=" + did +
                '}';
    }

    public class Roles {
        public String id;
        public String name;
        public String state;

    }

    public  boolean isCanAuth() {
        if (!TextUtils.isEmpty(province) &&
                !TextUtils.isEmpty(area) &&
                !TextUtils.isEmpty(city) &&
                !TextUtils.isEmpty(gender) &&
                !TextUtils.isEmpty(uid + "") &&
                !TextUtils.isEmpty(fannum + "") &&
                !TextUtils.isEmpty(age + "") &&
                !TextUtils.isEmpty(username) &&
                !TextUtils.isEmpty(profession) &&
                !TextUtils.isEmpty(imageurl) &&
                !TextUtils.isEmpty(did + "")) {
            return true;
        }else{
            return false;
        }
    }
}
