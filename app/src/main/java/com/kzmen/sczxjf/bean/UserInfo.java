package com.kzmen.sczxjf.bean;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 用户
 */
public class UserInfo {
    /* {
    "uid": "1775",
    "uc_uid": "22",
    "parentid": "0",
    "weixin": "o0a-Lt1HEuKmx4z56yEToIT5dNAo",
    "openid": "o_uGjxBIRycGj0gAwIzow8hvoOCw",
    "tokenweixin": "MDQyYzQ0M2UyZTUwMjBmMTkxMjc3MDc3NDM3MTk1YTU=",
    "username": "施文松",
    "imageurl": "http://wx.qlogo.cn/mmopen/AqVMchic84PL7zq1alwRmfhfWHiaRU17MKSAibsoClmMYicWpQsnHnicgqHEobjNSnub9EDvvhia5bBUz5UGY1iaWms9DTxfj2HRZIh/0",
    "on_phone": "18996601419",
    "on_pwd": "c9362d77832c6fcbee199fad69827be4",
    "profession": "厨师",
    "source": "appstore",
    "province": "辽宁",
    "city": "大连",
    "area": "普兰店市",
    "gender": "男",
    "age": "17",
    "fannum": "1000",
    "regdate": "2015-11-25 18:44:30",
    "logindate": "2016-04-25 11:27:14",
    "roles_money": "1000",
    "balance": "1128",
    "score": "1036131",
    "earn_money": "133",
    "miss_money": "1717",
    "level": "1",
    "state": "1",
    "hotnum": "1402",
    "relaynum": "10",
    "pushnum": "34",
    "screenshotnum": "1",
    "channel": "alipay",
    "channel_id": "291916410@qq.com",
    "channel_name": "小明",
    "test": "0",
    "yjtime": "2015-12-22 22:35:35",
    "isunus": "0",
    "yao_money": "400",
    "batch_money": "328",
    "withdraw_ok": "0",
    "withdraw_no": "0",
    "freeze_money": "0",
    "form": "android",
    "app_bate": "1.09",
    "jpushid": "0806de3c8e5",
    "isjpush": "1",
    "anps_source": "1",
    "gongz_openid": null,
    "is_accept": null,
    "lose": "0",
    "lose_uid": "0",
    "extras": [
        {
            "id": "1",
            "name": "企业家"
        },
        {
            "id": "2",
            "name": "媒体人"
        },
        {
            "id": "3",
            "name": "小V"
        }
    ]
}*/
    public int level;
    public int uid;
    public String weixin;
    public String username;
    public String token;
    public String imageurl;
    public String on_phone;
    /*状态，1正常，0冻结*/
    public String state;
    public String score;
    public List<ExtraEntity> extras;
    public  int balance;
    public int hotnum;
    public String province;
    public String  city;
    public String area;
    public String gender;
    public String emali;


    public void setExtras(List<ExtraEntity> extras) {
        this.extras = extras;
    }

    public static UserInfo parseJson(JSONObject jsonObj) {
        Gson gson = new Gson();
        UserInfo bean = gson.fromJson(jsonObj.toString(), UserInfo.class);
        return bean;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "level=" + level +
                ", uid=" + uid +
                ", weixin='" + weixin + '\'' +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", on_phone='" + on_phone + '\'' +
                ", state='" + state + '\'' +
                ", score='" + score + '\'' +
                ", extras=" + extras +
                ", balance=" + balance +
                ", hotnum=" + hotnum +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    public String getBalance() {
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        String format = df.format((double) balance / 100);
        return format;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public class ExtraEntity{

        public String id;
        public String name;
        public String state;

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }
}
