package com.kzmen.sczxjf.bean.kzbean;

import java.io.Serializable;

/**
 * Created by pjj18 on 2017/9/10.
 */

public class KzCoursePlayBean implements Serializable{
    private String id;
    private String chapter_id;
    private String name;
    private int type;//0章节 1课程
    private String title;
    private String media;
    private String media_time;

    public KzCoursePlayBean(String id, String chapter_id, String name, int type, String media, String media_time) {
        this.id = id;
        this.chapter_id = chapter_id;
        this.name = name;
        this.type = type;
        this.media = media;
        this.media_time = media_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
