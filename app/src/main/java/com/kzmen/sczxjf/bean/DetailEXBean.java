package com.kzmen.sczxjf.bean;

import com.google.gson.Gson;

/**
 * 说明：
 * note：
 * Created by FuPei
 * on 2016/1/26 at 11:30
 */
public class DetailEXBean {

    /**
     * code : 1
     * data : {"id":"1","order":"2016012509314783537","datetime":"2016-01-25 09:31:47","express":"","express_number":"","num":"2","score":"10000","money":"2900","state":"0","nickname":"赵洪菲","tel":"15801035399","address":"四川-成都-高新区 高新区盛邦街天合汇锦城3-2","goods_title":"全棉四件套纯棉学生四件套单人双人床1.5米1.8米","goods_score":"5000","goods_money":"0","goods_postage":"2900","goods_image":"http://192.168.0.111:9012/Uploads/Picture/2016-01-22/56a1eca852555.jpg"}
     * msg : 查询成功
     */

    private int code;
    /**
     * id : 1
     * order : 2016012509314783537
     * datetime : 2016-01-25 09:31:47
     * express :
     * express_number :
     * num : 2
     * score : 10000
     * money : 2900
     * state : 0
     * nickname : 赵洪菲
     * tel : 15801035399
     * address : 四川-成都-高新区 高新区盛邦街天合汇锦城3-2
     * goods_title : 全棉四件套纯棉学生四件套单人双人床1.5米1.8米
     * goods_score : 5000
     * goods_money : 0
     * goods_postage : 2900
     * goods_image : http://192.168.0.111:9012/Uploads/Picture/2016-01-22/56a1eca852555.jpg
     */

    private DataEntity data;
    private String msg;

    public static DetailEXBean parseEntity(String jsontext) {
        return new Gson().fromJson(jsontext, DetailEXBean.class);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public static class DataEntity {
        private String id;
        private String order;
        private String datetime;
        private String express;
        private String express_number;
        private String num;
        private String score;
        private String money;
        private String state;
        private String nickname;
        private String tel;
        private String address;
        private String goods_title;
        private String goods_score;
        private String goods_money;
        private String goods_postage;
        private String goods_image;
        private String state_str;
        private String redeemcode;
        private String type;
        private int sec;

        public String getRedeemcode() {
            return redeemcode;
        }

        public DataEntity setRedeemcode(String redeemcode) {
            this.redeemcode = redeemcode;
            return this;
        }

        public String getType() {
            return type;
        }

        public DataEntity setType(String type) {
            this.type = type;
            return this;
        }

        public String getExpress_url() {
            return express_url;
        }

        public void setExpress_url(String express_url) {
            this.express_url = express_url;
        }

        private String express_url;

        public int getSec() {
            return sec;
        }

        public void setSec(int sec) {
            this.sec = sec;
        }

        public String getState_str() {
            return state_str;
        }

        public void setState_str(String state_str) {
            this.state_str = state_str;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public void setExpress(String express) {
            this.express = express;
        }

        public void setExpress_number(String express_number) {
            this.express_number = express_number;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setGoods_title(String goods_title) {
            this.goods_title = goods_title;
        }

        public void setGoods_score(String goods_score) {
            this.goods_score = goods_score;
        }

        public void setGoods_money(String goods_money) {
            this.goods_money = goods_money;
        }

        public void setGoods_postage(String goods_postage) {
            this.goods_postage = goods_postage;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public String getId() {
            return id;
        }

        public String getOrder() {
            return order;
        }

        public String getDatetime() {
            return datetime;
        }

        public String getExpress() {
            return express;
        }

        public String getExpress_number() {
            return express_number;
        }

        public String getNum() {
            return num;
        }

        public String getScore() {
            return score;
        }

        public String getMoney() {
            return money;
        }

        public String getState() {
            return state;
        }

        public String getNickname() {
            return nickname;
        }

        public String getTel() {
            return tel;
        }

        public String getAddress() {
            return address;
        }

        public String getGoods_title() {
            return goods_title;
        }

        public String getGoods_score() {
            return goods_score;
        }

        public String getGoods_money() {
            return goods_money;
        }

        public String getGoods_postage() {
            return goods_postage;
        }

        public String getGoods_image() {
            return goods_image;
        }
    }
}
