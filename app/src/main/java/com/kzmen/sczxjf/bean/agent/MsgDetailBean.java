package com.kzmen.sczxjf.bean.agent;

/**
 * Created by pjj18 on 2017/10/17.
 */

public class MsgDetailBean {
    private String partner_project_name;
    private String total_fee;
    private String join_count;
    private String contents;

    public String getPartner_project_name() {
        return partner_project_name;
    }

    public void setPartner_project_name(String partner_project_name) {
        this.partner_project_name = partner_project_name;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getJoin_count() {
        return join_count;
    }

    public void setJoin_count(String join_count) {
        this.join_count = join_count;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
