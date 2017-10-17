package com.kzmen.sczxjf.bean.agent;

/**
 * Created by pjj18 on 2017/10/16.
 */

public class MsgListBean {
    private String member_message_no;
    private String title;
    private String is_read;
    private String create_time;
    private String contents;
    private String partner_project_id;

    public String getMember_message_no() {
        return member_message_no;
    }

    public void setMember_message_no(String member_message_no) {
        this.member_message_no = member_message_no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getPartner_project_id() {
        return partner_project_id;
    }

    public void setPartner_project_id(String partner_project_id) {
        this.partner_project_id = partner_project_id;
    }
}
