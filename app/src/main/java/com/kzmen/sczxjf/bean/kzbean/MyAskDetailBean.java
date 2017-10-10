package com.kzmen.sczxjf.bean.kzbean;

import java.util.List;

/**
 * Created by pjj18 on 2017/9/24.
 */

public class MyAskDetailBean {

    /**
     * uid : 5002
     * content : 专家专家，有问题要问，专家专家，有问题要问，专家专家，有问题要问，专家专家，有问题要问
     * title : 信用知识，快速问专家
     * ok_in : 0
     * answer : [{"uid":"5001","aid":"5","avatar":"","media_time":"0","iscollect":0,"content":"你说问题呢","username":"你的回答","zans":"0","iszan":0,"views":"0","tid_title":"","money":"0","media_status":1,"ok":"0","teacher":"0","datetime":"20天前","media":""}]
     * money : 10.0
     * state : 未采纳
     * qid : 3
     * images : ["http://api.kzmen.cn/Uploads/Picture/2017-08-19/59980cd197843.png","http://api.kzmen.cn/Uploads/Picture/2017-08-16/59945ccbe223c.png"]
     * ok : 0
     * isanony : 1
     * cid : 1
     * state_right : 2017-09-04 共2个回答
     */

    private String uid;
    private String content;
    private String title;
    private String ok_in;
    private String money;
    private String state;
    private String qid;
    private String ok;
    private String isanony;
    private String cid;
    private String state_right;
    private String ok_status;//问题状态码 1已采纳 2平分
    private String ok_status_str;//平分文字内容
    private List<AnswerBean> answer;
    private List<String> images;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOk_in() {
        return ok_in;
    }

    public void setOk_in(String ok_in) {
        this.ok_in = ok_in;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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

    public String getOk_status() {
        return ok_status;
    }

    public void setOk_status(String ok_status) {
        this.ok_status = ok_status;
    }

    public String getOk_status_str() {
        return ok_status_str;
    }

    public void setOk_status_str(String ok_status_str) {
        this.ok_status_str = ok_status_str;
    }

    public List<AnswerBean> getAnswer() {
        return answer;
    }

    public void setAnswer(List<AnswerBean> answer) {
        this.answer = answer;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public static class AnswerBean {
        /**
         * uid : 5001
         * aid : 5
         * avatar :
         * media_time : 0
         * iscollect : 0
         * content : 你说问题呢
         * username : 你的回答
         * zans : 0
         * iszan : 0
         * views : 0
         * tid_title :
         * money : 0
         * media_status : 1
         * ok : 0
         * teacher : 0
         * datetime : 20天前
         * media :
         */

        private String uid;
        private String aid;
        private String avatar;
        private String media_time;
        private int iscollect;
        private String content;
        private String username;
        private String zans;
        private int iszan;
        private String views;
        private String tid_title;
        private String money;
        private String media_status;
        private String ok;
        private String teacher;
        private String datetime;
        private String media;
        private String ok_status;
        private String ok_status_str;

        public String getOk_status() {
            return ok_status;
        }

        public void setOk_status(String ok_status) {
            this.ok_status = ok_status;
        }

        public String getOk_status_str() {
            return ok_status_str;
        }

        public void setOk_status_str(String ok_status_str) {
            this.ok_status_str = ok_status_str;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getMedia_time() {
            return media_time;
        }

        public void setMedia_time(String media_time) {
            this.media_time = media_time;
        }

        public int getIscollect() {
            return iscollect;
        }

        public void setIscollect(int iscollect) {
            this.iscollect = iscollect;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getZans() {
            return zans;
        }

        public void setZans(String zans) {
            this.zans = zans;
        }

        public int getIszan() {
            return iszan;
        }

        public void setIszan(int iszan) {
            this.iszan = iszan;
        }

        public String getViews() {
            return views;
        }

        public void setViews(String views) {
            this.views = views;
        }

        public String getTid_title() {
            return tid_title;
        }

        public void setTid_title(String tid_title) {
            this.tid_title = tid_title;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getMedia_status() {
            return media_status;
        }

        public void setMedia_status(String media_status) {
            this.media_status = media_status;
        }

        public String getOk() {
            return ok;
        }

        public void setOk(String ok) {
            this.ok = ok;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getMedia() {
            return media;
        }

        public void setMedia(String media) {
            this.media = media;
        }
    }
}
