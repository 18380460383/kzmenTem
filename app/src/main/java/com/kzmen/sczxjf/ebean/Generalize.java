package com.kzmen.sczxjf.ebean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/4.
 */
public class Generalize {
    private String pid;
    private String uid;
    private String company_name;
    private String projectname;
    private String imageurl;
    private String projecturl;
    private String totalcost;
    private String age;
    private String gender;
    private String province;
    private String city;
    private String area;
    private String startdate;
    private String enddate;
    private String isneedscreenshot;
    private String media_select;
    private String media_cast;
    private String media_money;
    private String freemedia_select;
    private String freemedia_cast;
    private String freemedia_money;
    private String  isfans;
    private List<Integer> iidid=new ArrayList<Integer>();
    private List<Integer> didid=new ArrayList<Integer>();
    private List<Pushroles> pushroles=new ArrayList<>();

    public List<Pushroles> getPushroles() {
        return pushroles;
    }

    public void setPushroles(List<Pushroles> pushroles) {
        this.pushroles .clear();
        this.pushroles.addAll(pushroles);
    }


    public String getIsfans() {
        return isfans;
    }

    public void setIsfans(String isfans) {
        this.isfans = isfans;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getProjecturl() {
        return projecturl;
    }

    public void setProjecturl(String projecturl) {
        this.projecturl = projecturl;
    }

    public String getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(String totalcost) {
        this.totalcost = totalcost;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getIsneedscreenshot() {
        return isneedscreenshot;
    }

    public void setIsneedscreenshot(String isneedscreenshot) {
        this.isneedscreenshot = isneedscreenshot;
    }

    public String getMedia_select() {
        return media_select;
    }

    public void setMedia_select(String media_select) {
        this.media_select = media_select;
    }

    public String getMedia_cast() {
        return media_cast;
    }

    public void setMedia_cast(String media_cast) {
        this.media_cast = media_cast;
    }

    public String getMedia_money() {
        return media_money;
    }

    public void setMedia_money(String media_money) {
        this.media_money = media_money;
    }

    public String getFreemedia_select() {
        return freemedia_select;
    }

    public void setFreemedia_select(String freemedia_select) {
        this.freemedia_select = freemedia_select;
    }

    public String getFreemedia_cast() {
        return freemedia_cast;
    }

    public void setFreemedia_cast(String freemedia_cast) {
        this.freemedia_cast = freemedia_cast;
    }

    public String getFreemedia_money() {
        return freemedia_money;
    }

    public void setFreemedia_money(String freemedia_money) {
        this.freemedia_money = freemedia_money;
    }

    public List<Integer> getIidid() {
        return iidid;
    }

    public void setIidid(List<Integer> iidid) {
        this.iidid = iidid;
    }

    public List<Integer> getDidid() {
        return didid;
    }

    public void setDidid(List<Integer> didid) {
        this.didid = didid;
    }
     public static class Pushroles implements Serializable{
        private String uid;
        private String rid;
        private double  money;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

         @Override
         public String toString() {
             return "{'uid':"+uid+",'rid':"+rid+",'money':"+money+"}";
         }
     }

    @Override
    public String toString() {
        return "Generalize{" +
                "pid='" + pid + '\'' +
                ", uid='" + uid + '\'' +
                ", company_name='" + company_name + '\'' +
                ", projectname='" + projectname + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", projecturl='" + projecturl + '\'' +
                ", totalcost='" + totalcost + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", startdate='" + startdate + '\'' +
                ", enddate='" + enddate + '\'' +
                ", isneedscreenshot='" + isneedscreenshot + '\'' +
                ", media_select='" + media_select + '\'' +
                ", media_cast='" + media_cast + '\'' +
                ", media_money='" + media_money + '\'' +
                ", freemedia_select='" + freemedia_select + '\'' +
                ", freemedia_cast='" + freemedia_cast + '\'' +
                ", freemedia_money='" + freemedia_money + '\'' +
                ", isfans='" + isfans + '\'' +
                ", iidid=" + iidid +
                ", didid=" + didid +
                ", pushroles=" + pushroles +
                '}';
    }
}
