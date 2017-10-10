package com.kzmen.sczxjf.test.bean;

/**
 * Created by pjj18 on 2017/8/3.
 */

public class AnserItemBean {
    private String answer;
    private boolean isSelected ;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "AnserItemBean{" +
                "answer='" + answer + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
