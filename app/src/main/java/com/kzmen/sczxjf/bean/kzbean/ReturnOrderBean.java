package com.kzmen.sczxjf.bean.kzbean;

/**
 * Created by pjj18 on 2017/9/13.
 */

public class ReturnOrderBean {
    private int type;
    private String price;
    private String msg;
    private String errType;
    public ReturnOrderBean(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getErrType() {
        return errType;
    }

    public void setErrType(String errType) {
        this.errType = errType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ReturnOrderBean{" +
                "type=" + type +
                ", msg='" + msg + '\'' +
                '}';
    }
}
