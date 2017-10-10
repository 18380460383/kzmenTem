package com.kzmen.sczxjf.bean.kzbean;

/**
 * Created by pjj18 on 2017/9/12.
 */

public class EventBusBean {
    private int opType;
    private String className;

    public EventBusBean() {
    }

    public int getOpType() {
        return opType;
    }

    public void setOpType(int opType) {
        this.opType = opType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "EventBusBean{" +
                "opType=" + opType +
                ", className='" + className + '\'' +
                '}';
    }
}
