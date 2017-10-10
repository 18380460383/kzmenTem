package com.kzmen.sczxjf.bean.kzbean;

import java.util.List;

/**
 * Created by pjj18 on 2017/9/15.
 */

public class SpecialPowerBean {

    /**
     * avatar :
     * username : 坚果
     * role : 0
     * role_date : 0
     * role_money : 200.00
     * content : ["解锁所有老师初级课程","解锁回答问题权利","赠送50张偷听体验券","享受平台所有中高级课程8.5折优惠","赠送价值¥98.00元内参考资料一本","积分商城享受8.5折兑换优惠"]
     * role_str : ["普通会员","认证会员"]
     */

    private String avatar;
    private String username;
    private String role;
    private String role_date;
    private String role_money;
    private List<String> content;
    private List<String> role_str;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole_date() {
        return role_date;
    }

    public void setRole_date(String role_date) {
        this.role_date = role_date;
    }

    public String getRole_money() {
        return role_money;
    }

    public void setRole_money(String role_money) {
        this.role_money = role_money;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public List<String> getRole_str() {
        return role_str;
    }

    public void setRole_str(List<String> role_str) {
        this.role_str = role_str;
    }
}
