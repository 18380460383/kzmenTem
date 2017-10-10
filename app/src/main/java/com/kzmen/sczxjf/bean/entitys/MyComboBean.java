package com.kzmen.sczxjf.bean.entitys;

import com.kzmen.sczxjf.EnConstants;

import java.util.List;

/**
 * 创建者：Administrator
 * 时间：2016/7/25
 * 功能描述：
 */
public class MyComboBean {


    /**
     * id : 25074c4367200de9ae81dd1a5b23a90d
     * uid : 91
     * cid : 69
     * create_time : 1468825846
     * package_title : 时尚类基础套餐
     * package_price : 1740.00
     * package_logo : /Public/uploads/recomment/2015-08/shishang.jpg
     * package_old_price : 1570.00
     * status : 2
     * start_time : 1438099200
     * end_time : 1477843200
     * package_media : [{"pid":"2698","mid":"5391","media_logo":"/uploadfile/2014/0320/20140320060230963.jpg","price":"140.00","uid":"49","package_price":"140.00","real_price":"210.00","media_type":"9","0":"","text":"","media_name":"搜狐网时尚"},{"pid":"66463","mid":"70677","media_logo":"/./Public/uploads/images/recomment/55aaa80aef5b3.gif","price":"280.00","uid":"143","package_price":"280.00","real_price":"420.00","media_type":"9","0":"","text":"","media_name":"凤凰时尚"},{"pid":"851","mid":"851","media_logo":"/rwpic/huanqiuwangshishang.png","price":"130.00","uid":"49","package_price":"130.00","real_price":"195.00","media_type":"9","0":"","text":"","media_name":"环球网时尚"},{"pid":"2803","mid":"5561","media_logo":"/uploadfile/2014/1211/20141211015050421.jpg","price":"100.00","uid":"49","package_price":"100.00","real_price":"150.00","media_type":"9","0":"","text":"","media_name":"光明网女性"},{"pid":"835","mid":"835","media_logo":"/rwpic/ruilifushi.png","price":"90.00","uid":"49","package_price":"90.00","real_price":"135.00","media_type":"9","0":"","text":"","media_name":"瑞丽服饰"},{"pid":"838","mid":"838","media_logo":"/rwpic/minashishangwang.png","price":"90.00","uid":"49","package_price":"90.00","real_price":"135.00","media_type":"9","0":"","text":"","media_name":"米娜时尚网"},{"pid":"1459","mid":"1459","media_logo":"/uploadfile/2014/0318/20140318031339102.jpg","price":"80.00","uid":"49","package_price":"80.00","real_price":"120.00","media_type":"9","0":"","text":"","media_name":"嘉人网"},{"pid":"839","mid":"839","media_logo":"/rwpic/ailishishangwang.png","price":"90.00","uid":"49","package_price":"90.00","real_price":"135.00","media_type":"9","0":"","text":"","media_name":"爱丽时尚网"},{"pid":"829","mid":"829","media_logo":"/rwpic/YOKAshishangwangmeirong.png","price":"80.00","uid":"49","package_price":"80.00","real_price":"120.00","media_type":"9","0":"","text":"","media_name":"YOKA时尚网美容"},{"pid":"833","mid":"833","media_logo":"/rwpic/zhongguoshishangwang.png","price":"80.00","uid":"49","package_price":"80.00","real_price":"120.00","media_type":"9","0":"","text":"","media_name":"中国时尚网"}]
     * media_type : 9
     * content : null
     * effect_show : null
     * ad_desc :
     * use_secene : 1
     * package_type : 0
     * count : 1
     */

    private String id;
    private String uid;
    private String cid;
    private String create_time;
    private String package_title;
    private String package_price;
    private String package_logo;
    private String package_old_price;
    private String status;
    private String start_time;
    private String end_time;
    private String media_type;
    private String content;
    private String effect_show;
    private String ad_desc;
    private String use_secene;
    private String package_type;
    private String count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPackage_title() {
        return package_title;
    }

    public void setPackage_title(String package_title) {
        this.package_title = package_title;
    }

    public String getPackage_price() {
        return package_price;
    }

    public void setPackage_price(String package_price) {
        this.package_price = package_price;
    }

    public String getPackage_logo() {
        if(!package_logo.contains("http")){
            package_logo= EnConstants.IMG_HTTP+package_logo;
        }
        return package_logo;
    }

    public void setPackage_logo(String package_logo) {
        this.package_logo = package_logo;
    }

    public String getPackage_old_price() {
        return package_old_price;
    }

    public void setPackage_old_price(String package_old_price) {
        this.package_old_price = package_old_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEffect_show() {
        return effect_show;
    }

    public void setEffect_show(String effect_show) {
        this.effect_show = effect_show;
    }

    public String getAd_desc() {
        return ad_desc;
    }

    public void setAd_desc(String ad_desc) {
        this.ad_desc = ad_desc;
    }

    public String getUse_secene() {
        return use_secene;
    }

    public void setUse_secene(String use_secene) {
        this.use_secene = use_secene;
    }

    public String getPackage_type() {
        return package_type;
    }

    public void setPackage_type(String package_type) {
        this.package_type = package_type;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<PackageMediaEntity> getPackage_media() {
        return package_media;
    }

    public void setPackage_media(List<PackageMediaEntity> package_media) {
        this.package_media = package_media;
    }

    private java.util.List<PackageMediaEntity> package_media;
    public class PackageMediaEntity{

        /**
         * pid : 2803
         * mid : 5561
         * media_logo : /uploadfile/2014/1211/20141211015050421.jpg
         * price : 100.00
         * uid : 49
         * package_price : 100.00
         * real_price : 150.00
         * media_type : 9
         * text :
         * media_name : 光明网女性
         */

        private String pid;
        private String mid;
        private String media_logo;
        private  double price;
        private String uid;
        private String package_price;
        private String real_price;
        private String media_type;
        private String text;
        private String media_name;

        public void setPid(String pid) {
            this.pid = pid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public void setMedia_logo(String media_logo) {
            this.media_logo = media_logo;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setPackage_price(String package_price) {
            this.package_price = package_price;
        }

        public void setReal_price(String real_price) {
            this.real_price = real_price;
        }

        public void setMedia_type(String media_type) {
            this.media_type = media_type;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setMedia_name(String media_name) {
            this.media_name = media_name;
        }

        public String getPid() {
            return pid;
        }

        public String getMid() {
            return mid;
        }

        public String getMedia_logo() {
            return media_logo;
        }

        public double getPrice() {
            return price;
        }

        public String getUid() {
            return uid;
        }

        public String getPackage_price() {
            return package_price;
        }

        public String getReal_price() {
            return real_price;
        }

        public String getMedia_type() {
            return media_type;
        }

        public String getText() {
            return text;
        }

        public String getMedia_name() {
            return media_name;
        }
    }
}
