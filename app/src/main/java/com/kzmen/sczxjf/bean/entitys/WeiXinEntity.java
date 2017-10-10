package com.kzmen.sczxjf.bean.entitys;

import com.kzmen.sczxjf.EnMoneySelectEntity;

/**
 * describe:
 * notice:
 * Created by FuPei on 2016/5/25.
 */
public class WeiXinEntity extends EnMoneySelectEntity {

    /**
     * uid : 49
     * weixin_text : 测试麻辣成都
     * fans : 100000
     * multiple_one_price : 50.00
     * single_price : 5.00
     * multiple_three_real_price : 97.50
     * avatar : http://ipx.2016.360netnews.com/Uploads/2016-05-16/5739955bc73f7/malachengdu.jpg
     * recommend_index : 0
     * city : 成都
     * id : 0ecc6afbfee66877638a6dccebdef51a
     * is_collect : 0
     * category : 323
     * single_real_price : 7.50
     * multiple_two_price : 55.00
     * province : 四川
     * authentication_type : 0
     * authentication_text : null
     * desc_remark : null
     * falg : 0
     * multiple_three_price : 65.00
     * weixin_account : woaixxc
     * counts : 20928
     * categorystr : 娱乐八卦
     * multiple_two_real_price : 82.50
     * multiple_one_real_price : 75.00
     */

    private String uid;
    private String weixin_text;
    private String fans;
    private String single_price;
    private String avatar;
    private String recommend_index;
    private String city;
    private String id;
    private String province;
    private String weixin_account;
    private String categorystr;
    private String multiple_one_real_price;
    private String single_real_price;
    private String multiple_two_real_price;
    private String multiple_three_real_price;
    private String authentication_text;

    public String getAuthentication_text() {
        return authentication_text;
    }

    public void setAuthentication_text(String authentication_text) {
        this.authentication_text = authentication_text;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMultiple_two_real_price() {
        return multiple_two_real_price;
    }

    public void setMultiple_two_real_price(String multiple_two_real_price) {
        this.multiple_two_real_price = multiple_two_real_price;
    }

    public String getMultiple_three_real_price() {
        return multiple_three_real_price;
    }

    public void setMultiple_three_real_price(String multiple_three_real_price) {
        this.multiple_three_real_price = multiple_three_real_price;
    }

    public String getMultiple_one_real_price() {
        return multiple_one_real_price;
    }

    public void setMultiple_one_real_price(String multiple_one_real_price) {
        this.multiple_one_real_price = multiple_one_real_price;
    }

    public String getSingle_real_price() {
        return single_real_price;
    }

    public void setSingle_real_price(String single_real_price) {
        this.single_real_price = single_real_price;
    }

    public String getWeixin_text() {
        return weixin_text;
    }

    public void setWeixin_text(String weixin_text) {
        this.weixin_text = weixin_text;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getSingle_price() {
        return single_price;
    }

    public void setSingle_price(String single_price) {
        this.single_price = single_price;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getWeixin_account() {
        return weixin_account;
    }

    public void setWeixin_account(String weixin_account) {
        this.weixin_account = weixin_account;
    }

    public String getCategorystr() {
        return categorystr;
    }

    public void setCategorystr(String categorystr) {
        this.categorystr = categorystr;
    }

    @Override
    public float getMoney() {
        return getMinMoney();
    }

    @Override
    public String getIdentify() {
        return getId();
    }

    public float getMaxMoney() {
        float money = Float.MIN_VALUE;
        try{
            if(getSingle_real_price() != null && getSingle_real_price().indexOf("--") == -1) {
                if(Float.valueOf(getSingle_real_price()) >= money) {
                    money = Float.valueOf(getSingle_real_price());
                }
            }
            if(getMultiple_one_real_price() != null && getMultiple_one_real_price().indexOf("--") == -1) {
                if(Float.valueOf(getMultiple_one_real_price()) >= money) {
                    money = Float.valueOf(getMultiple_one_real_price());
                }
            }
            if(getMultiple_two_real_price() != null && getMultiple_two_real_price().indexOf("--") == -1) {
                if(Float.valueOf(getMultiple_two_real_price()) >= money) {
                    money = Float.valueOf(getMultiple_two_real_price());
                }
            }
            if(getMultiple_three_real_price() != null && getMultiple_three_real_price().indexOf("--") == -1) {
                if(Float.valueOf(getMultiple_three_real_price()) >= money) {
                    money = Float.valueOf(getMultiple_three_real_price());
                }
            }
        } catch (Exception e){

        }
        if(money == Float.MIN_VALUE) {
            money = 0;
        }
        return money;
    }

    public float getMinMoney() {
        float money = Float.MAX_VALUE;
        try{
            if(getSingle_real_price() != null && getSingle_real_price().indexOf("--") == -1) {
                if(Float.valueOf(getSingle_real_price()) <= money) {
                    money = Float.valueOf(getSingle_real_price());
                }
            }
            if(getMultiple_one_real_price() != null && getMultiple_one_real_price().indexOf("--") == -1) {
                if(Float.valueOf(getMultiple_one_real_price()) <= money) {
                    money = Float.valueOf(getMultiple_one_real_price());
                }
            }
            if(getMultiple_two_real_price() != null && getMultiple_two_real_price().indexOf("--") == -1) {
                if(Float.valueOf(getMultiple_two_real_price()) <= money) {
                    money = Float.valueOf(getMultiple_two_real_price());
                }
            }
            if(getMultiple_three_real_price() != null && getMultiple_three_real_price().indexOf("--") == -1) {
                if(Float.valueOf(getMultiple_three_real_price()) <= money) {
                    money = Float.valueOf(getMultiple_three_real_price());
                }
            }
        } catch (Exception e) {

        }
        if(money == Float.MAX_VALUE) {
            money = 0;
        }
        return money;
    }
}
