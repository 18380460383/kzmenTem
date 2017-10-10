package com.kzmen.sczxjf.bean.returned;

/**
 * Created by Administrator on 2016/1/13.
 */
public class RedPacket {

    /**
     * not_open_msg : 拆红包
     * not_open_msg1 : 恭喜您
     * not_open_msg2 : 未开红包提示
     * opened_msg : 恭喜您
     * opened_msg1 : 获得享e下5元现金
     * packets_money : 5
     */

    private String not_open_msg;
    private String not_open_msg1;
    private String not_open_msg2;
    private String opened_msg;
    private String opened_msg1;
    private String packets_money;

    public void setNot_open_msg(String not_open_msg) {
        this.not_open_msg = not_open_msg;
    }

    public void setNot_open_msg1(String not_open_msg1) {
        this.not_open_msg1 = not_open_msg1;
    }

    public void setNot_open_msg2(String not_open_msg2) {
        this.not_open_msg2 = not_open_msg2;
    }

    public void setOpened_msg(String opened_msg) {
        this.opened_msg = opened_msg;
    }

    public void setOpened_msg1(String opened_msg1) {
        this.opened_msg1 = opened_msg1;
    }

    public void setPackets_money(String packets_money) {
        this.packets_money = packets_money;
    }

    public String getNot_open_msg() {
        return not_open_msg;
    }

    public String getNot_open_msg1() {
        return not_open_msg1;
    }

    public String getNot_open_msg2() {
        return not_open_msg2;
    }

    public String getOpened_msg() {
        return opened_msg;
    }

    public String getOpened_msg1() {
        return opened_msg1;
    }

    public String getPackets_money() {
        return packets_money;
    }
}
