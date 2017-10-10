package com.kzmen.sczxjf.bean.kzbean;

import java.util.List;

/**
 * Created by pjj18 on 2017/9/15.
 * 余额界面的javabean
 */

public class MoneyLogBean {
    private String balance;
    private String withdraw;
    private List<data> data;

    public static class data {
        private String state;//0增加 1减少
        private String money;// 金额 单位：分
        private String datetime;//时间
        private String type_str;//类型文字
        private String status_str;//提现状态

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getType_str() {
            return type_str;
        }

        public void setType_str(String type_str) {
            this.type_str = type_str;
        }

        public String getStatus_str() {
            return status_str;
        }

        public void setStatus_str(String status_str) {
            this.status_str = status_str;
        }
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(String withdraw) {
        this.withdraw = withdraw;
    }

    public List<MoneyLogBean.data> getData() {
        return data;
    }

    public void setData(List<MoneyLogBean.data> data) {
        this.data = data;
    }
}
