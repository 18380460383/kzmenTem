package com.kzmen.sczxjf.bean.kzbean;

import java.util.List;

/**
 * Created by pjj18 on 2017/9/24.
 */

public class MyAskBean {

    /**
     * count : 1
     * data : [{"content":"专家专家，有问题要问，专家专家，有问题要问，专家专家，有问题要问，专家专家，有问题要问","uid":"5002","title":"信用知识，快速问专家","state":"已回答","qid":"3","images":["http://api.kzmen.cn/Uploads/Picture/2017-08-19/59980cd197843.png","http://api.kzmen.cn/Uploads/Picture/2017-08-16/59945ccbe223c.png"],"ok":"0","datetime":"1504430877","isanony":"1","cid":"1","state_right":"共2个回答 未采纳"},{"content":"个人信用有什么作用？","uid":"5001","title":"信用知识，快速问专家","state":"已解答","qid":"2","images":[],"ok":"1","datetime":"1504430714","isanony":"0","cid":"1","state_right":"共3个回答 已采纳满意答案"}]
     */

    private String count;
    private List<DataBean> data;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * content : 专家专家，有问题要问，专家专家，有问题要问，专家专家，有问题要问，专家专家，有问题要问
         * uid : 5002
         * title : 信用知识，快速问专家
         * state : 已回答
         * qid : 3
         * images : ["http://api.kzmen.cn/Uploads/Picture/2017-08-19/59980cd197843.png","http://api.kzmen.cn/Uploads/Picture/2017-08-16/59945ccbe223c.png"]
         * ok : 0
         * datetime : 1504430877
         * isanony : 1
         * cid : 1
         * state_right : 共2个回答 未采纳
         */

        private String content;
        private String uid;
        private String title;
        private String state;
        private String qid;
        private String ok;
        private String datetime;
        private String isanony;
        private String cid;
        private String state_right;
        private String money;
        private List<String> images;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getQid() {
            return qid;
        }

        public void setQid(String qid) {
            this.qid = qid;
        }

        public String getOk() {
            return ok;
        }

        public void setOk(String ok) {
            this.ok = ok;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getIsanony() {
            return isanony;
        }

        public void setIsanony(String isanony) {
            this.isanony = isanony;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getState_right() {
            return state_right;
        }

        public void setState_right(String state_right) {
            this.state_right = state_right;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
