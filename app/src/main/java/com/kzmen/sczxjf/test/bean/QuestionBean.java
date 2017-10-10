package com.kzmen.sczxjf.test.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 保存数据库数据
 * Created by LGL on 2016/6/4.
 */
public class QuestionBean {
    public String question;
    public int answer;
    public int ID;
    public List<AnserItemBean>answerList=new ArrayList<>();

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public List<AnserItemBean> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<AnserItemBean> answerList) {
        this.answerList = answerList;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
