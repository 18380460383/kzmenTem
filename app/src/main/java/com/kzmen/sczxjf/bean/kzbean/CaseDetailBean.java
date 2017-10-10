package com.kzmen.sczxjf.bean.kzbean;

/**
 * Created by pjj18 on 2017/9/2.
 */

public class CaseDetailBean {
    private String id;
    private String title;
    private String describe;
    private String views;
    private String update_time;
    private String source;
    private String video;
    private String sharepic;
    private String linkurl;
    private String content_url;

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

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getSharepic() {
        return sharepic;
    }

    public void setSharepic(String sharepic) {
        this.sharepic = sharepic;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public String getContent_url() {
        return content_url;
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }

    @Override
    public String toString() {
        return "CaseDetailBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", describe='" + describe + '\'' +
                ", views='" + views + '\'' +
                ", update_time='" + update_time + '\'' +
                ", source='" + source + '\'' +
                ", video='" + video + '\'' +
                ", sharepic='" + sharepic + '\'' +
                ", linkurl='" + linkurl + '\'' +
                ", content_url='" + content_url + '\'' +
                '}';
    }
}
