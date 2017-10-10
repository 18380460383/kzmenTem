package com.kzmen.sczxjf.bean.entitys;

import com.kzmen.sczxjf.EnConstants;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：Administrator
 * 时间：2016/7/20
 * 功能描述：
 */
public class EnComboBean implements Serializable {


    /**
     * cid : a539c5a299a7ff4bc8af1677a9f00b4f
     * package_title : 测试套餐
     * industry : 290
     * content : 0
     * package_logo : 0
     * start_time : 1469462400
     * use_secene : 1
     * end_time : 1470412800
     * effect_show :
     * ad_desc : 门户网站一把抓，超值优惠套餐热浪来袭！！！！！
     * money : 1290.00
     * old_money : 1290.00
     * package_media : [{"price":"300.00","real_price":"450.00","package_price":"450.00","media_type":"9","text":"","mid":"a11508da119dc641673b51eff9443e65","pid":"68a26b5d275f70640e991f07c6b60d0a","media_name":"\u6d4b\u767e\u5ea6\u5730\u56fe1","media_logo":"http:\/\/ipx.2016.360netnews.com\/Uploads\/2016-05-18\/573c300716062.png","uid":"273dbfab77d9203b4a6a9c140251a9d6","login_name":"writer-test","mobile_no":"13889888888","name":"writer-test"},{"price":"200.00","real_price":"300.00","package_price":"300.00","media_type":"9","text":"","mid":"7b3e5ca423d54fa777765b5b22998e7b","pid":"afb1cdb59d8c94f9177cd570c427ff35","media_name":"\u6d4b\u767e\u5ea6\u767e\u79d11","media_logo":"http:\/\/ipx.2016.360netnews.com\/Uploads\/2016-05-18\/573c300716062.png","uid":"273dbfab77d9203b4a6a9c140251a9d6","login_name":"writer-test","mobile_no":"13889888888","name":"writer-test"},{"price":"300.00","real_price":"540.00","package_price":"540.00","media_type":"9","text":"","mid":"70898","pid":"66840","media_name":"\u817e\u8baf\u5927\u6210\u7f51\u65b0\u95fb","media_logo":"http:\/\/ipx.2016.360netnews.com\/Public\/uploads\/images\/recomment\/55ee51e6c83da.png","uid":"49","login_name":"cbadmin","mobile_no":"18583296230","name":"\u7f51\u7acb\u65b9\u4e92\u52a8"}]
     * media_type : 9
     * total_count : 120
     * sale_count : 0
     * industry_text : 新闻资讯
     * position_text : null
     */

    private String cid;
    private String package_title;
    private String industry;
    private String content;
    private String package_logo;
    private long start_time;
    private String use_secene;
    private long end_time;
    private String effect_show;
    private String ad_desc;
    private double money;
    private double old_money;
    private List<MyComboBean.PackageMediaEntity> package_media;
    private String media_type;
    private int total_count;
    private int sale_count;
    private String industry_text;
    private Object position_text;
    private int order_no;

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setPackage_title(String package_title) {
        this.package_title = package_title;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPackage_logo(String package_logo) {
        this.package_logo = package_logo;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public void setUse_secene(String use_secene) {
        this.use_secene = use_secene;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public void setEffect_show(String effect_show) {
        this.effect_show = effect_show;
    }

    public void setAd_desc(String ad_desc) {
        this.ad_desc = ad_desc;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setOld_money(double old_money) {
        this.old_money = old_money;
    }

    public void setPackage_media(List<MyComboBean.PackageMediaEntity> package_media) {
        this.package_media = package_media;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public void setSale_count(int sale_count) {
        this.sale_count = sale_count;
    }

    public void setIndustry_text(String industry_text) {
        this.industry_text = industry_text;
    }

    public void setPosition_text(Object position_text) {
        this.position_text = position_text;
    }

    public String getCid() {
        return cid;
    }

    public String getPackage_title() {
        return package_title;
    }

    public String getIndustry() {
        return industry;
    }

    public String getContent() {
        return content;
    }

    public String getPackage_logo() {
        if(!package_logo.contains("http")){
            package_logo= EnConstants.IMG_HTTP+package_logo;
        }
        return package_logo;
    }

    public long getStart_time() {
        return start_time;
    }

    public String getUse_secene() {
        return use_secene;
    }

    public long getEnd_time() {
        return end_time;
    }

    public String getEffect_show() {
        return effect_show;
    }

    public String getAd_desc() {
        return ad_desc;
    }

    public double getMoney() {
        return money;
    }

    public double getOld_money() {
        return old_money;
    }

    public List<MyComboBean.PackageMediaEntity> getPackage_media() {
        return package_media;
    }

    public String getMedia_type() {
        return media_type;
    }

    public int getTotal_count() {
        return total_count;
    }

    public int getSale_count() {
        return sale_count;
    }

    public String getIndustry_text() {
        return industry_text;
    }

    public Object getPosition_text() {
        return position_text;
    }
}
