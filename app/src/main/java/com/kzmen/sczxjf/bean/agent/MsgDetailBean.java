package com.kzmen.sczxjf.bean.agent;

/**
 * Created by pjj18 on 2017/10/17.
 */

public class MsgDetailBean {
    private String partner_project_name;
    private String total_fee;
    private String join_count;
    private String contents;
    private String partner_project_id;
    private String is_join;

    public String getPartner_project_id() {
        return partner_project_id;
    }

    public void setPartner_project_id(String partner_project_id) {
        this.partner_project_id = partner_project_id;
    }

    public String getIs_join() {
        return is_join;
    }

    public void setIs_join(String is_join) {
        this.is_join = is_join;
    }

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
