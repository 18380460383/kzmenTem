package com.kzmen.sczxjf.bean.entitys;

import com.kzmen.sczxjf.EnMoneySelectEntity;

/**
 * describe:
 * notice:
 * Created by FuPei on 2016/5/26.
 */
public class WeiBoEntity extends EnMoneySelectEntity {

    /**
     * collec_id : 0
     * uid : 49
     * media_type : null
     * remark :
     * fans : 1190000
     * avatar : http://tp2.sinaimg.cn/2476481021/180/22868769150/1
     * recommend_index : 1
     * url : http://weibo.com/u/2476481021
     * media_text : 教你整死身边小人
     * city : 3412
     * id : 23043
     * is_collect : 0
     * category : 346
     * pay_turn_real_price : null
     * province : 全国
     * authentication_text : null
     * authentication_type : 236
     * pay_straight_real_price : 3105
     * falg : 0
     * counts : 14728
     * straight_real_price : 3105.000000
     * categorystr : 综合
     * turn_real_price : 3105.000000
     */

    private int collec_id;
    private String fans;
    private String avatar;
    private String recommend_index;
    private String media_text;
    private String id;
    private String uid;
    private String province;
    private String pay_straight_real_price;
    private String straight_real_price;
    private String categorystr;
    private String pay_turn_real_price;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPay_turn_real_price() {
        return pay_turn_real_price;
    }

    public void setPay_turn_real_price(String pay_turn_real_price) {
        this.pay_turn_real_price = pay_turn_real_price;
    }

    public int getCollec_id() {
        return collec_id;
    }

    public void setCollec_id(int collec_id) {
        this.collec_id = collec_id;
    }

    public String getFans() {
        return fans;
    }

    public String getStraight_real_price() {
        return straight_real_price;
    }

    public void setStraight_real_price(String straight_real_price) {
        this.straight_real_price = straight_real_price;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPay_straight_real_price() {
        return pay_straight_real_price;
    }

    public void setPay_straight_real_price(String pay_straight_real_price) {
        this.pay_straight_real_price = pay_straight_real_price;
    }

    public String getCategorystr() {
        return categorystr;
    }

    public void setCategorystr(String categorystr) {
        this.categorystr = categorystr;
    }

    @Override
    public float getMoney() {
        try{
            return Float.valueOf(getStraight_real_price());
        } catch (Exception e) {}
        return 0;
    }

    @Override
    public String getIdentify() {
        return getId();
    }

    @Override
    public String toString() {
        return "WeiBoEntity{" +
                "collec_id=" + collec_id +
                ", fans='" + fans + '\'' +
                ", avatar='" + avatar + '\'' +
                ", recommend_index='" + recommend_index + '\'' +
                ", media_text='" + media_text + '\'' +
                ", id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", province='" + province + '\'' +
                ", pay_straight_real_price='" + pay_straight_real_price + '\'' +
                ", straight_real_price='" + straight_real_price + '\'' +
                ", categorystr='" + categorystr + '\'' +
                ", pay_turn_real_price='" + pay_turn_real_price + '\'' +
                '}';
    }
}
