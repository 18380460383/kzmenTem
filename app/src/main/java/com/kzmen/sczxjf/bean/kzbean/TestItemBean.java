package com.kzmen.sczxjf.bean.kzbean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */

public class TestItemBean {

    /**
     * tid : 1
     * type : 1
     * title : 有过贷款吗
     * result : [{"opt_id":"A","opt_title":"有","opt_true":0},{"opt_id":"B","opt_title":"忘记了","opt_true":"1"},{"opt_id":"C","opt_title":"没有","opt_true":0}]
     * correct : B
     * sec : 5
     */
    private String tid;
    private String type;
    private String title;
    private String correct;
    private String sec;
    private List<ResultBean> result;

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

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * opt_id : A
         * opt_title : 有
         * opt_true : 0
         */

        private String opt_id;
        private String opt_title;
        private int opt_true;

        public String getOpt_id() {
            return opt_id;
        }

        public void setOpt_id(String opt_id) {
            this.opt_id = opt_id;
        }

        public String getOpt_title() {
            return opt_title;
        }

        public void setOpt_title(String opt_title) {
            this.opt_title = opt_title;
        }

        public int getOpt_true() {
            return opt_true;
        }

        public void setOpt_true(int opt_true) {
            this.opt_true = opt_true;
        }
    }
}
