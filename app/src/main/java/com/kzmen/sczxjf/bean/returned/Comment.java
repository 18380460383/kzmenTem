package com.kzmen.sczxjf.bean.returned;

/**
 * 创建者：Administrator
 * 时间：2016/4/28
 * 功能描述：
 */
public class Comment {

    /**
     * cid : 1
     * uid : 69
     * opt_id : A
     * content : 选择A
     * datetime : 2016年04月26日14: 34
     * likes : 12
     * relay : 2
     * report : 2
     * is_reply : 1
     * to_username : 施文松
     * username : 路过
     * imageurl : http: //wx.qlogo.cn/mmopen/Q3auHgzwzM6j3bCH5Kn2jX1oUU423JPySWBnzXjB8Vep3ZUv8ALbbUoVl7XgldIpiagUXUmQCKibeDYhsvc8vu5A/0
     * relay_url : http: //192.168.0.163: 9012/api.php/App/findOneNewsContent/nid/21/uid/1775/cid/1/time/1461807176
     */

    private int cid;
    private int uid;
    private String opt_id;
    private String content;
    private String datetime;
    private int likes;
    private int relay;
    private int report;
    private String is_reply;
    private String to_username;
    private String username;
    private String imageurl;
    private String relay_url;
    private int is_z = 0;
    private int bestc=0;

    public int getBestc() {
        return bestc;
    }

    public void setBestc(int bestc) {
        this.bestc = bestc;
    }

    public int getIs_z() {
        return is_z;
    }

    public void setIs_z(int is_z) {
        this.is_z = is_z;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setOpt_id(String opt_id) {
        this.opt_id = opt_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setRelay(int relay) {
        this.relay = relay;
    }

    public void setReport(int report) {
        this.report = report;
    }

    public void setIs_reply(String is_reply) {
        this.is_reply = is_reply;
    }

    public void setTo_username(String to_username) {
        this.to_username = to_username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setRelay_url(String relay_url) {
        this.relay_url = relay_url;
    }

    public int getCid() {
        return cid;
    }

    public int getUid() {
        return uid;
    }

    public String getOpt_id() {
        return opt_id;
    }

    public String getContent() {
        return content;
    }

    public String getDatetime() {
        return datetime;
    }

    public int getLikes() {
        return likes;
    }

    public int getRelay() {
        return relay;
    }

    public int getReport() {
        return report;
    }

    public String getIs_reply() {
        return is_reply;
    }

    public String getTo_username() {
        return to_username;
    }

    public String getUsername() {
        return username;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getRelay_url() {
        return relay_url;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "cid=" + cid +
                ", uid=" + uid +
                ", opt_id='" + opt_id + '\'' +
                ", content='" + content + '\'' +
                ", datetime='" + datetime + '\'' +
                ", likes=" + likes +
                ", relay=" + relay +
                ", report=" + report +
                ", is_reply='" + is_reply + '\'' +
                ", to_username='" + to_username + '\'' +
                ", username='" + username + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", relay_url='" + relay_url + '\'' +
                ", is_z=" + is_z +
                '}';
    }
}
