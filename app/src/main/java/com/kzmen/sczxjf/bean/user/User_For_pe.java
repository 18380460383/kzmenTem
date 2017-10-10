package com.kzmen.sczxjf.bean.user;

/**
 * 创建者：Administrator
 * 时间：2016/8/8
 * 功能描述：个人企业共同使用的账户信息的javaBean
 */
public class User_For_pe {

    /**
     * uid : 3f5bebb1aedc5541c6f07ad50aa20dd4
     * login_name : 18323847977
     * mobile_no : 18323847977
     * identity : 138,544
     * security_key : 5a618dcd1cb1668c1edfab0fcafd4491
     * avatar :
     * name : daive
     * unionid :
     * hotnum : 1
     * username : daive
     * imageurl :
     * weixin :
     * on_phone : 18323847977
     * score : 0
     * email :
     * logindate : 2016-08-08 09:00:21
     * add_score : 0
     * state : 1
     * enterprise_name:"asf"
     * userkey : 5a618dcd1cb1668c1edfab0fcafd4491
     * recharge_desc:"充值说明"
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIzZjViZWJiMWFlZGM1NTQxYzZmMDdhZDUwYWEyMGRkNCIsImxvZ2luX25hbWUiOiIxODMyMzg0Nzk3NyIsIm1vYmlsZV9ubyI6IjE4MzIzODQ3OTc3IiwiaWRlbnRpdHkiOiIxMzgsNTQ0Iiwic2VjdXJpdHlfa2V5IjoiNWE2MThkY2QxY2IxNjY4YzFlZGZhYjBmY2FmZDQ0OTEiLCJhdmF0YXIiOiIiLCJuYW1lIjoiZGFpdmUiLCJ1bmlvbmlkIjoiIn0.-b25IzEbGJauyH_HfF8qEqEb-uDoLJJfQSGCtGLgTes
     */

    /**
     * 用户uid
     */
    private String uid;
    /**
     * 登录名
     */
    private String login_name;
    /**
     * 手机号
     */
    private String mobile_no;
    private String identity;
    /**
     * 用户userkey
     */
    private String security_key;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户微信unionid
     */
    private String unionid;
    private String hotnum;
    private String username;
    private String imageurl;
    private String weixin;
    private String on_phone;
    private String score;
    private String email;
    private String logindate;
    /**
     * 增加的积分
     */
    private int add_score;
    private int state;
    private String userkey;
    /**
     * 用户Token
     */
    private String token;
    private double balance;
    private String city;
    private String province;
    private String enterprise_name;
    private String status;
    private String recharge_desc;

    public String getRecharge_desc() {
        return recharge_desc;
    }

    public void setRecharge_desc(String recharge_desc) {
        this.recharge_desc = recharge_desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnterprise_name() {
        return enterprise_name;
    }

    public void setEnterprise_name(String enterprise_name) {
        this.enterprise_name = enterprise_name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCity() {
        if(city==null){
            city="";
        }
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        if(province==null){
            province="";
        }
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public void setSecurity_key(String security_key) {
        this.security_key = security_key;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public void setHotnum(String hotnum) {
        this.hotnum = hotnum;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public void setOn_phone(String on_phone) {
        this.on_phone = on_phone;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogindate(String logindate) {
        this.logindate = logindate;
    }

    public void setAdd_score(int add_score) {
        this.add_score = add_score;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        if(uid==null){
            uid="";
        }
        return uid;
    }

    public String getLogin_name() {
        if(login_name==null){
            login_name="";
        }
        return login_name;
    }

    public String getMobile_no() {
        if(mobile_no==null){

        }
        return mobile_no;
    }

    public String getIdentity() {
        if(identity==null){
            identity="";
        }
        return identity;
    }

    public String getSecurity_key() {
        if(security_key==null){
            security_key="";
        }
        return security_key;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getUnionid() {
        return unionid;
    }

    public String getHotnum() {
        return hotnum;
    }

    public String getUsername() {
        return username;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getWeixin() {
        return weixin;
    }

    public String getOn_phone() {
        return on_phone;
    }

    public String getScore() {
        return score;
    }

    public String getEmail() {
        return email;
    }

    public String getLogindate() {
        return logindate;
    }

    public int getAdd_score() {
        return add_score;
    }

    public int getState() {
        return state;
    }

    public String getUserkey() {
        return userkey;
    }

    public String getToken() {
        if(token==null){
            token="";
        }
        return token;
    }

    @Override
    public String toString() {
        return "User_For_pe{" +
                "uid='" + uid + '\'' +
                ", login_name='" + login_name + '\'' +
                ", mobile_no='" + mobile_no + '\'' +
                ", identity='" + identity + '\'' +
                ", security_key='" + security_key + '\'' +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", unionid='" + unionid + '\'' +
                ", hotnum='" + hotnum + '\'' +
                ", username='" + username + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", weixin='" + weixin + '\'' +
                ", on_phone='" + on_phone + '\'' +
                ", score='" + score + '\'' +
                ", email='" + email + '\'' +
                ", logindate='" + logindate + '\'' +
                ", add_score=" + add_score +
                ", state=" + state +
                ", userkey='" + userkey + '\'' +
                ", token='" + token + '\'' +
                ", balance=" + balance +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", enterprise_name='" + enterprise_name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
