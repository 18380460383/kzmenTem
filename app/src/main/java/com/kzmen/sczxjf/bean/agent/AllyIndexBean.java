package com.kzmen.sczxjf.bean.agent;

/**
 * Created by pjj18 on 2017/10/17.
 */

public class AllyIndexBean {

    /**
     * member_count : 2
     * not_pay_member_count : 2
     * pay_member_count : 0
     * today_income : 0
     * today_new_member : 0
     * total_income : 0
     */

    private String member_count;
    private String not_pay_member_count;
    private String pay_member_count;
    private String today_income;
    private String today_new_member;
    private String total_income;

    public String getMember_count() {
        return member_count;
    }

    public void setMember_count(String member_count) {
        this.member_count = member_count;
    }

    public String getNot_pay_member_count() {
        return not_pay_member_count;
    }

    public void setNot_pay_member_count(String not_pay_member_count) {
        this.not_pay_member_count = not_pay_member_count;
    }

    public String getPay_member_count() {
        return pay_member_count;
    }

    public void setPay_member_count(String pay_member_count) {
        this.pay_member_count = pay_member_count;
    }

    public String getToday_income() {
        return today_income;
    }

    public void setToday_income(String today_income) {
        this.today_income = today_income;
    }

    public String getToday_new_member() {
        return today_new_member;
    }

    public void setToday_new_member(String today_new_member) {
        this.today_new_member = today_new_member;
    }

    public String getTotal_income() {
        return total_income;
    }

    public void setTotal_income(String total_income) {
        this.total_income = total_income;
    }
}
