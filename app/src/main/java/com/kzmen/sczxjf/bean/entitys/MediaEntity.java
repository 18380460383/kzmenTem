package com.kzmen.sczxjf.bean.entitys;

import com.kzmen.sczxjf.EnMoneySelectEntity;

/**
 * describe:
 * notice:
 * Created by FuPei on 2016/5/26.
 */
public class MediaEntity extends EnMoneySelectEntity {

    /**
     * collec_id : 5fb7246c657e71a900794e1e63d2a7ba
     * uid : 49
     * media_type : 601
     * is_source : 2
     * index_order_no : 0
     * remark :
     * collection : 0
     * recommend_index : 1
     * media_text : 新浪新闻
     * order_no : 10000
     * city : 全国
     * real_price : 330.00
     * media_price : 220.00
     * id : 66499
     * is_collect : 1
     * news_source : 带新闻源
     * province : 全国
     * black : 0
     * media_industry : 新闻资讯
     * media_logo : http://ipx.2016.360netnews.com/Public/uploads/recomment/2015-07/xinlangxinwen.jpg
     * showfalg : 18
     * deal_fail_source :
     * status : 2
     * media_url : null
     * link_pic : 都不可以带
     * title_count : 6
     * is_week : 2
     * media_entry : 0
     * update_time : 1449798265
     * search_engines : 2
     * create_time : 1449798265
     * falg : 52
     * counts : 13635
     * mid : 577
     * channel :
     * pay_real_price : 330
     * portal_type : null
     */

    private String media_type;
    private String mid;
    private String uid;

    /**
     * 推荐指数
     */
    private String recommend_index;
    /**
     * 媒体名字
     */
    private String media_text;
    /**
     * 城市
     */
    private String city;
    /**
     * 转发价格
     */
    private String real_price;
    private String id;
    /**
     * 是否新闻源
     */
    private String news_source;
    /**
     * 类型
     */
    private String media_industry;
    /**
     * logo链接
     */
    private String media_logo;
    /**
     * 是否带图片
     */
    private String link_pic;

    private String province;

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRecommend_index() {
        return recommend_index;
    }

    public void setRecommend_index(String recommend_index) {
        this.recommend_index = recommend_index;
    }

    public String getMedia_text() {
        return media_text;
    }

    public void setMedia_text(String media_text) {
        this.media_text = media_text;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getReal_price() {
        return real_price;
    }

    public void setReal_price(String real_price) {
        this.real_price = real_price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNews_source() {
        return news_source;
    }

    public void setNews_source(String news_source) {
        this.news_source = news_source;
    }

    public String getMedia_industry() {
        return media_industry;
    }

    public void setMedia_industry(String media_industry) {
        this.media_industry = media_industry;
    }

    public String getMedia_logo() {
        return media_logo;
    }

    public void setMedia_logo(String media_logo) {
        this.media_logo = media_logo;
    }

    public String getLink_pic() {
        return link_pic;
    }

    public void setLink_pic(String link_pic) {
        this.link_pic = link_pic;
    }

    @Override
    public float getMoney() {
        try{
          return Float.valueOf(getReal_price());
        } catch (Exception e) {

        }
        return 0;
    }

    @Override
    public String getIdentify() {
        return getId();
    }
}
