package com.kzmen.sczxjf.bean.agent;

/**
 * Created by pjj18 on 2017/10/18.
 */

public class ParProListBean {
    private String partner_project_id;
    private String name;
    private String join_count;
    private String total_fee;

    public String getPartner_project_id() {
        return partner_project_id;
    }

    public void setPartner_project_id(String partner_project_id) {
        this.partner_project_id = partner_project_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJoin_count() {
        return join_count;
    }

    public void setJoin_count(String join_count) {
        this.join_count = join_count;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }
}
