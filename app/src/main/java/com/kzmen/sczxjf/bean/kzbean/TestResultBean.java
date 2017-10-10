package com.kzmen.sczxjf.bean.kzbean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/5.
 */

public class TestResultBean implements Serializable{

    /**
     * correct : 0
     * error : 0
     * miss : 4
     * score : 0
     * true_score : 0
     */

    private int correct;
    private int error;
    private int miss;
    private int score;
    private int true_score;

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getMiss() {
        return miss;
    }

    public void setMiss(int miss) {
        this.miss = miss;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTrue_score() {
        return true_score;
    }

    public void setTrue_score(int true_score) {
        this.true_score = true_score;
    }

    @Override
    public String toString() {
        return "TestResultBean{" +
                "correct=" + correct +
                ", error=" + error +
                ", miss=" + miss +
                ", score=" + score +
                ", true_score=" + true_score +
                '}';
    }
}
