package com.kzmen.sczxjf.bean.kzbean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */

public class HomeCourseBean {

    /**
     * cid : 1
     * tid : 1
     * type : 1
     * title : 大咖带你玩转信用卡，提升额度不是梦
     * describe : 针对谈卡色变的卡盲们的初级课程
     * views : 560
     * sid : 1
     * isunlock : 1
     * tid_title : 财经专家
     * tid_name : 易军
     * image : /Uploads/Picture/2017-08-16/59945ccbe223c.png
     * kejian_arr : [{"id":"1","title":"为何要做信用卡的额度提升？","media":"http://api.kzmen.cn/Uploads/Download/2017-08-19/5997c36721b89.mp3","media_time":"1"},{"id":"7","title":"为何要做信用卡的额度提升？","media":"http://api.kzmen.cn/Uploads/Download/2017-08-19/5997c36721b89.mp3","media_time":"1"}]
     * xiaojiang_arr : [{"id":"2","title":"为什么要办信用卡","media":"http://api.kzmen.cn/Uploads/Download/2017-08-19/5997cceccf4d1.wav","media_time":"1","charge_type":"0"},{"id":"4","title":"信用卡为什么总是批不下来？","media":"http://api.kzmen.cn/Uploads/Download/2017-08-19/5997c36721b89.mp3","media_time":"1","charge_type":"2"}]
     */

    private String cid;
    private String tid;
    private String type;
    private String title;
    private String describe;
    private String views;
    private String sid;
    private int isunlock;
    private String tid_title;
    private String tid_name;
    private String image;
    private List<KejianArrBean> kejian_arr;
    private List<XiaojiangArrBean> xiaojiang_arr;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getIsunlock() {
        return isunlock;
    }

    public void setIsunlock(int isunlock) {
        this.isunlock = isunlock;
    }

    public String getTid_title() {
        return tid_title;
    }

    public void setTid_title(String tid_title) {
        this.tid_title = tid_title;
    }

    public String getTid_name() {
        return tid_name;
    }

    public void setTid_name(String tid_name) {
        this.tid_name = tid_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<KejianArrBean> getKejian_arr() {
        return kejian_arr;
    }

    public void setKejian_arr(List<KejianArrBean> kejian_arr) {
        this.kejian_arr = kejian_arr;
    }

    public List<XiaojiangArrBean> getXiaojiang_arr() {
        return xiaojiang_arr;
    }

    public void setXiaojiang_arr(List<XiaojiangArrBean> xiaojiang_arr) {
        this.xiaojiang_arr = xiaojiang_arr;
    }

    public static class KejianArrBean {
        /**
         * id : 1
         * title : 为何要做信用卡的额度提升？
         * media : http://api.kzmen.cn/Uploads/Download/2017-08-19/5997c36721b89.mp3
         * media_time : 1
         */

        private String id;
        private String title;
        private String media;
        private String media_time;

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
    }

    public static class XiaojiangArrBean {
        /**
         * id : 2
         * title : 为什么要办信用卡
         * media : http://api.kzmen.cn/Uploads/Download/2017-08-19/5997cceccf4d1.wav
         * media_time : 1
         * charge_type : 0
         */

        private String id;
        private String title;
        private String media;
        private String media_time;
        private String charge_type;

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

        public String getCharge_type() {
            return charge_type;
        }

        public void setCharge_type(String charge_type) {
            this.charge_type = charge_type;
        }
    }
}
