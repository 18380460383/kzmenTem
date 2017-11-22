package com.kzmen.sczxjf.bean.kzbean;

import java.util.List;

/**
 * Created by pjj18 on 2017/9/9.
 */

public class KnowageAskDetailBean {

    /**
     * qid : 3
     * cid : 1
     * content : 专家专家，有问题要问，专家专家，有问题要问，专家专家，有问题要问，专家专家，有问题要问
     * ok : 0
     * images : ["http://api.kzmen.cn/Uploads/Picture/2017-08-19/59980cd197843.png","http://api.kzmen.cn/Uploads/Picture/2017-08-16/59945ccbe223c.png"]
     * isopen : 1
     * isanony : 1
     * uid : 5002
     * username : 匿名用户
     * avatar :
     * money : 10.0
     * daojishi : 2017-09-04
     * interlocution : {"id":"1","title":"信用知识，快速问专家","image":"http://api.kzmen.cn/Uploads/Picture/2017-09-09/59b39b8df376b.png","des":"信用提升，维护，利用"}
     * answer_tishi : 1人已抢答，1个答案免费听，抢答被选中者可获得赏金
     * user : {"teacher":"1","role":"0"}
     * answer : [{"aid":"5","datetime":"6天前","zans":"0","views":"0","ok":"0","money":"0","content":"你说问题呢","media":"","media_time":"0","uid":"5001","username":"手机用户***5399","avatar":"","teacher":"0","media_status":1,"media_money":"1","media_button":"1元偷偷听","tid_title":"","iszan":0,"iscollect":0}]
     */

    private String qid;
    private String cid;
    private String content;
    private String ok;
    private String isopen;
    private String isanony;
    private String uid;
    private String username;
    private String avatar;
    private String money;
    private String daojishi = "";
    private String state = "";
    private InterlocutionBean interlocution;
    private String answer_tishi;
    private UserBean user;

    private List<String> images;
    private List<AnswerBean> answer;

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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDaojishi() {
        return daojishi;
    }

    public void setDaojishi(String daojishi) {
        this.daojishi = daojishi;
    }

    public InterlocutionBean getInterlocution() {
        return interlocution;
    }

    public void setInterlocution(InterlocutionBean interlocution) {
        this.interlocution = interlocution;
    }

    public String getAnswer_tishi() {
        return answer_tishi;
    }

    public void setAnswer_tishi(String answer_tishi) {
        this.answer_tishi = answer_tishi;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<AnswerBean> getAnswer() {
        return answer;
    }

    public void setAnswer(List<AnswerBean> answer) {
        this.answer = answer;
    }

    public static class InterlocutionBean {
        /**
         * id : 1
         * title : 信用知识，快速问专家
         * image : http://api.kzmen.cn/Uploads/Picture/2017-09-09/59b39b8df376b.png
         * des : 信用提升，维护，利用
         */

        private String id;
        private String title;
        private String image;
        private String des;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }

    public static class UserBean {
        /**
         * teacher : 1
         * role : 0
         */

        private String teacher;
        private String role;

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    public static class AnswerBean {
        /**
         * aid : 5
         * datetime : 6天前
         * zans : 0
         * views : 0
         * ok : 0
         * money : 0
         * content : 你说问题呢
         * media :
         * media_time : 0
         * uid : 5001
         * username : 手机用户***5399
         * avatar :
         * teacher : 0
         * media_status : 1
         * media_money : 1
         * media_button : 1元偷偷听
         * tid_title :
         * iszan : 0
         * iscollect : 0
         */

        private String aid;
        private String datetime;
        private String zans;
        private String views;
        private String ok;
        private String money;
        private String content;
        private String media;
        private String media_time;
        private String uid;
        private String username;
        private String avatar;
        private String teacher;
        private String media_status;
        private String media_money;
        private String media_button;
        private String tid_title;
        private int iszan;
        private int iscollect;

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getZans() {
            return zans;
        }

        public void setZans(String zans) {
            this.zans = zans;
        }

        public String getViews() {
            return views;
        }

        public void setViews(String views) {
            this.views = views;
        }

        public String getOk() {
            return ok;
        }

        public void setOk(String ok) {
            this.ok = ok;
        }

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

        public String getMedia() {
            return media;
        }

        public void setMedia(String media) {
            this.media = media;
        }

        public String getMedia_time() {
            return media_time;
        }

        public void setMedia_time(String media_time) {
            this.media_time = media_time;
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

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getMedia_status() {
            return media_status;
        }

        public void setMedia_status(String media_status) {
            this.media_status = media_status;
        }

        public String getMedia_money() {
            return media_money;
        }

        public void setMedia_money(String media_money) {
            this.media_money = media_money;
        }

        public String getMedia_button() {
            return media_button;
        }

        public void setMedia_button(String media_button) {
            this.media_button = media_button;
        }

        public String getTid_title() {
            return tid_title;
        }

        public void setTid_title(String tid_title) {
            this.tid_title = tid_title;
        }

        public int getIszan() {
            return iszan;
        }

        public void setIszan(int iszan) {
            this.iszan = iszan;
        }

        public int getIscollect() {
            return iscollect;
        }

        public void setIscollect(int iscollect) {
            this.iscollect = iscollect;
        }
    }
}
