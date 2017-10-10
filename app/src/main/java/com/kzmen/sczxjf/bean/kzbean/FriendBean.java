package com.kzmen.sczxjf.bean.kzbean;

/**
 * Created by pjj18 on 2017/9/14.
 */

public class FriendBean {
    private String uid;
    private String username;
    private String avatar;
    private String nickname;
    private String balance;
    private String score;
    private String role;
    private String teacher;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "FriendBean{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", nickname='" + nickname + '\'' +
                ", balance='" + balance + '\'' +
                ", score='" + score + '\'' +
                ", role='" + role + '\'' +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
