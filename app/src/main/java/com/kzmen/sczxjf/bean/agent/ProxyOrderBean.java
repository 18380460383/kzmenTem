package com.kzmen.sczxjf.bean.agent;

import java.io.Serializable;

/**
 * Created by pjj18 on 2017/10/24.
 */

public class ProxyOrderBean implements Serializable{
    private String discount_code_no;
    private String member_id;
    private String buy_count;
    private String price;
    private String title;
    private String order_type;

    public String getDiscount_code_no() {
        return discount_code_no;
    }

    public void setDiscount_code_no(String discount_code_no) {
        this.discount_code_no = discount_code_no;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getBuy_count() {
        return buy_count;
    }

    public void setBuy_count(String buy_count) {
        this.buy_count = buy_count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }
}
