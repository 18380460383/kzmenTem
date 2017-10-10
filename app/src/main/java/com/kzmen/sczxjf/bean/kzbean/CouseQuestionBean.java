package com.kzmen.sczxjf.bean.kzbean;

import java.util.List;

/**
 * Created by pjj18 on 2017/9/6.
 */

public class CouseQuestionBean {

    /**
     * qid : 1
     * child : 1
     * content : 信用卡有什么用处吗？
     * zans : 57
     * views : 124
     * datetime : 1504419484
     * isopen : 0
     * isanony : 1
     * uid : 5001
     * username : 手机用户****5399
     * avatar :
     * media_status : 1
     * media_money : 1
     * media_button : 1元偷偷听
     * iszan : 0
     * isreport : 0
     * answer_id : 2
     * answer_content :
     * answer_media : http://api.kzmen.cn/Uploads/Download/2017-08-19/5997c36721b89.mp3
     * answer_media_time : 1
     * answer_avatar : http://api.kzmen.cn/Uploads/Picture/2017-08-16/59945d59ef2a1.png
     * zhuijia_list : [{"qid":"4","parent_qid":"1","content":"问题追加问题追加问题追加问题追加问题追加问题追加","zans":"0","views":"0","datetime":"1504436091","isopen":"1","isanony":"0","uid":"5001","username":"手机用户****5399","avatar":"","media_status":1,"media_money":"1","media_button":"1元偷偷听","iszan":0,"isreport":0,"answer_id":"6","answer_content":"","answer_media":"http://api.kzmen.cn/Uploads/Download/2017-08-19/5997c36721b89.mp3","answer_media_time":"1","answer_avatar":"http://api.kzmen.cn/Uploads/Picture/2017-08-16/59945d59ef2a1.png"}]
     */

    private String qid;
    private String child;
    private String content;
    private String zans;
    private String views;
    private String datetime;
    private String isopen;
    private String isanony;
    private String uid;
    private String username;
    private String avatar;
    private String media_status;
    private String media_money;
    private String media_button;
    private int iszan;
    private int isreport;
    private String answer_id;
    private String answer_content;
    private String answer_media;
    private String answer_media_time;
    private String answer_avatar;
    private String answer_username;
    private List<ZhuijiaListBean> zhuijia_list;

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
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

    public int getIszan() {
        return iszan;
    }

    public void setIszan(int iszan) {
        this.iszan = iszan;
    }

    public int getIsreport() {
        return isreport;
    }

    public void setIsreport(int isreport) {
        this.isreport = isreport;
    }

    public String getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(String answer_id) {
        this.answer_id = answer_id;
    }

    public String getAnswer_content() {
        return answer_content;
    }

    public void setAnswer_content(String answer_content) {
        this.answer_content = answer_content;
    }

    public String getAnswer_media() {
        return answer_media;
    }

    public void setAnswer_media(String answer_media) {
        this.answer_media = answer_media;
    }

    public String getAnswer_media_time() {
        return answer_media_time;
    }

    public void setAnswer_media_time(String answer_media_time) {
        this.answer_media_time = answer_media_time;
    }

    public String getAnswer_avatar() {
        return answer_avatar;
    }

    public void setAnswer_avatar(String answer_avatar) {
        this.answer_avatar = answer_avatar;
    }

    public List<ZhuijiaListBean> getZhuijia_list() {
        return zhuijia_list;
    }

    public void setZhuijia_list(List<ZhuijiaListBean> zhuijia_list) {
        this.zhuijia_list = zhuijia_list;
    }

    public String getAnswer_username() {
        return answer_username;
    }

    public void setAnswer_username(String answer_username) {
        this.answer_username = answer_username;
    }

    public static class ZhuijiaListBean {
        /**
         * qid : 4
         * parent_qid : 1
         * content : 问题追加问题追加问题追加问题追加问题追加问题追加
         * zans : 0
         * views : 0
         * datetime : 1504436091
         * isopen : 1
         * isanony : 0
         * uid : 5001
         * username : 手机用户****5399
         * avatar :
         * media_status : 1
         * media_money : 1
         * media_button : 1元偷偷听
         * iszan : 0
         * isreport : 0
         * answer_id : 6
         * answer_content :
         * answer_media : http://api.kzmen.cn/Uploads/Download/2017-08-19/5997c36721b89.mp3
         * answer_media_time : 1
         * answer_avatar : http://api.kzmen.cn/Uploads/Picture/2017-08-16/59945d59ef2a1.png
         */

        private String qid;
        private String parent_qid;
        private String content;
        private String zans;
        private String views;
        private String datetime;
        private String isopen;
        private String isanony;
        private String uid;
        private String username;
        private String avatar;
        private String media_status;
        private String media_money;
        private String media_button;
        private int iszan;
        private int isreport;
        private String answer_id;
        private String answer_content;
        private String answer_media;
        private String answer_media_time;
        private String answer_avatar;

        public String getQid() {
            return qid;
        }

        public void setQid(String qid) {
            this.qid = qid;
        }

        public String getParent_qid() {
            return parent_qid;
        }

        public void setParent_qid(String parent_qid) {
            this.parent_qid = parent_qid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
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

        public int getIszan() {
            return iszan;
        }

        public void setIszan(int iszan) {
            this.iszan = iszan;
        }

        public int getIsreport() {
            return isreport;
        }

        public void setIsreport(int isreport) {
            this.isreport = isreport;
        }

        public String getAnswer_id() {
            return answer_id;
        }

        public void setAnswer_id(String answer_id) {
            this.answer_id = answer_id;
        }

        public String getAnswer_content() {
            return answer_content;
        }

        public void setAnswer_content(String answer_content) {
            this.answer_content = answer_content;
        }

        public String getAnswer_media() {
            return answer_media;
        }

        public void setAnswer_media(String answer_media) {
            this.answer_media = answer_media;
        }

        public String getAnswer_media_time() {
            return answer_media_time;
        }

        public void setAnswer_media_time(String answer_media_time) {
            this.answer_media_time = answer_media_time;
        }

        public String getAnswer_avatar() {
            return answer_avatar;
        }

        public void setAnswer_avatar(String answer_avatar) {
            this.answer_avatar = answer_avatar;
        }
    }

    @Override
    public String toString() {
        return "CouseQuestionBean{" +
                "qid='" + qid + '\'' +
                ", child='" + child + '\'' +
                ", content='" + content + '\'' +
                ", zans='" + zans + '\'' +
                ", views='" + views + '\'' +
                ", datetime='" + datetime + '\'' +
                ", isopen='" + isopen + '\'' +
                ", isanony='" + isanony + '\'' +
                ", uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", media_status=" + media_status +
                ", media_money='" + media_money + '\'' +
                ", media_button='" + media_button + '\'' +
                ", iszan=" + iszan +
                ", isreport=" + isreport +
                ", answer_id='" + answer_id + '\'' +
                ", answer_content='" + answer_content + '\'' +
                ", answer_media='" + answer_media + '\'' +
                ", answer_media_time='" + answer_media_time + '\'' +
                ", answer_avatar='" + answer_avatar + '\'' +
                ", zhuijia_list=" + zhuijia_list +
                '}';
    }
}
