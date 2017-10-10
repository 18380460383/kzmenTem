package com.kzmen.sczxjf.bean.entitys;

import com.kzmen.sczxjf.EnMoneySelectEntity;

import java.util.List;

/**
 * describe:
 * notice:
 * Created by FuPei on 2016/6/2.
 */
public class WriterEntity extends EnMoneySelectEntity {


    /**
     * uid : 49
     * remark : 2122
     * writer_level_text : 明星级
     * writer_type_text : 段子手
     * recommend_index : 3
     * is_collect : 0
     * recommend_id :
     * refuse_order_count : 2
     * writer_level : 603
     * refuse_remark : null
     * source_val : 614
     * good_fields_text : 食品
     * qq : 11
     * register_source : 1
     * id_code : 51081219920618183X
     * id_src_back : http://ipx.2016.360netnews.com/./Public/uploads/images/idcard/55683b07b3432.jpg
     * status : 230
     * nickname : 小七
     * writer_type : 452
     * flow_order_count : 0
     * avatar : http://ipx.2016.360netnews.com/static/images/writerph15.jpg
     * id_src_front : http://ipx.2016.360netnews.com/./Public/uploads/images/recomment/55683b1b64f67.jpg
     * source_impl :
     * good_fields : ,17,
     * price : [{"word_count_title":"","price":300}]
     * update_time : 1463463387
     * case_count : 0
     * order_count : 9
     * finish_order_count : 5
     * submit_time : 1456381581
     */

    private String recommend_index;
    private String remark;
    private String writer_type_text;
    private String nickname;
    private String avatar;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String id_code;
    /**
     * word_count_title :
     * price : 300
     */

    private List<PriceBean> price;

    public String getRecommend_index() {
        return recommend_index;
    }

    public void setRecommend_index(String recommend_index) {
        this.recommend_index = recommend_index;
    }

    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
        this.id_code = id_code;
    }

    @Override
    public float getMoney() {
        if(price != null) {
            float money = Float.MAX_VALUE;
            for(PriceBean bean : price) {
                if(Float.valueOf(bean.getPrice()) < money) {
                    money = Float.valueOf(bean.getPrice());
                }
            }
            return money;
        }
        return 0;
    }

    public float getMaxMoney() {
        if(price != null) {
            float money = Float.MIN_VALUE;
            for(PriceBean bean : price) {
                if(Float.valueOf(bean.getPrice()) > money) {
                    money = Float.valueOf(bean.getPrice());
                }
            }
            return money;
        }
        return 0;
    }

    @Override
    public String getIdentify() {
        return this.toString();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWriter_type_text() {
        return writer_type_text;
    }

    public void setWriter_type_text(String writer_type_text) {
        this.writer_type_text = writer_type_text;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<PriceBean> getPrice() {
        return price;
    }

    public void setPrice(List<PriceBean> price) {
        this.price = price;
    }

    public class PriceBean {
        private String word_count_title;
        private String price;

        public String getWord_count_title() {
            return word_count_title;
        }

        public void setWord_count_title(String word_count_title) {
            this.word_count_title = word_count_title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
