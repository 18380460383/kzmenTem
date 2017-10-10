package com.kzmen.sczxjf.bean.kzbean;

import java.io.Serializable;

/**
 * Created by pjj18 on 2017/9/9.
 */

public class KnowageAskItemBean implements Serializable{

    /**
     * qid : 3
     * content : 专家专家，有问题要问，专家专家，有问题要问，专家专家，有问题要问，专家专家，有问题要问
     * ok : 0
     * images : 1
     * isopen : 1
     * isanony : 1
     * uid : 5002
     * username : 匿名用户
     * avatar :
     * daojishi : 2017-09-04
     * answer : {"username":"手机用户***5399","datetime":"6天内抢答","title":""}
     * state : 1人已抢答
     * money : 10.0
     */

    private String qid;
    private String content;
    private String ok;
    private int images;
    private String isopen;
    private String isanony;
    private String uid;
    private String username;
    private String avatar;
    private String daojishi;
    private AnswerBean answer;
    private String state;
    private String money;

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public String getIsopen() {
        return isopen;
    }

    public void setIsopen(String isopen) {
        this.isopen = isopen;
    }

    public String getIsanony() {
        return isanony;
    }

    public void setIsanony(String isanony) {
        this.isanony = isanony;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDaojishi() {
        return daojishi;
    }

    public void setDaojishi(String daojishi) {
        this.daojishi = daojishi;
    }

    public AnswerBean getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerBean answer) {
        this.answer = answer;
    }

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

    public static class AnswerBean {
        /**
         * username : 手机用户***5399
         * datetime : 6天内抢答
         * title :
         */

        private String username;
        private String datetime;
        private String title;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    @Override
    public String toString() {
        return "KnowageAskItemBean{" +
                "qid='" + qid + '\'' +
                ", content='" + content + '\'' +
                ", ok='" + ok + '\'' +
                ", images=" + images +
                ", isopen='" + isopen + '\'' +
                ", isanony='" + isanony + '\'' +
                ", uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", daojishi='" + daojishi + '\'' +
                ", answer=" + answer +
                ", state='" + state + '\'' +
                ", money='" + money + '\'' +
                '}';
    }
}
