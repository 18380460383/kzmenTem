package com.kzmen.sczxjf.bean.entitys;

import com.google.gson.Gson;

/**
 * describe:
 * notice:
 * Created by FuPei on 2016/6/2.
 */
public class MultiMediaEntity {

    private WeiBoEntity weibo;
    private WeiXinEntity weixin;
    private MediaEntity media;
    private WriterEntity writer;
    private ReporterEntity reporter;

    public ReporterEntity getReporter() {
        return reporter;
    }

    public void setReporter(ReporterEntity reporter) {
        this.reporter = reporter;
    }

    public WriterEntity getWriter() {
        return writer;
    }

    public void setWriter(WriterEntity writer) {
        this.writer = writer;
    }

    public WeiBoEntity getWeibo() {
        return weibo;
    }

    public void setWeibo(WeiBoEntity weibo) {
        this.weibo = weibo;
    }

    public MediaEntity getMedia() {
        return media;
    }

    public void setMedia(MediaEntity media) {
        this.media = media;
    }

    public WeiXinEntity getWeixin() {
        return weixin;
    }

    public void setWeixin(WeiXinEntity weixin) {
        this.weixin = weixin;
    }

    @Override
    public String toString() {
        if(media != null) {
            return new Gson().toJson(media);
        }
        if(weixin != null) {
            return new Gson().toJson(weixin);
        }
        if(weibo != null) {
            return new Gson().toJson(weibo);
        }
        if(writer != null) {
            return new Gson().toJson(writer);
        }
        if(reporter != null) {
            return new Gson().toJson(reporter);
        }
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if(media != null) {
            return media.getId().equals(((MultiMediaEntity)o).getMedia());
        }
        if(weibo != null) {
            return weibo.getId().equals(((MultiMediaEntity)o).getWeibo());
        }
        if(weixin != null) {
            return weixin.getId().equals(((MultiMediaEntity)o).getWeixin());
        }
        return super.equals(o);
    }
}
