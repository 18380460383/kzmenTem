package com.kzmen.sczxjf.bean.agent;

/**
 * Created by pjj18 on 2017/10/17.
 */

public class AllyListBean {
    private String member_id;
    private String member_name;
    private boolean pay_status;
    private String register_date;

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public boolean getPay_status() {
        return pay_status;
    }

    public void setPay_status(boolean pay_status) {
        this.pay_status = pay_status;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }
}
