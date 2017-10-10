package com.kzmen.sczxjf.bean.request;

/**
 * Created by Administrator on 2015/11/11.
 */
public class UpdateMsg {

    /**
     * bate : 1.0
     * download : http://pre.im/4fed
     * isqz : 0
     * qzcont : ss
     */

    private int bate;
    private String showversion;
    private String download;
    private String isqz;
    private String qzcont;
    private String not_version_alert;

    public String getNot_version_alert() {
        return not_version_alert;
    }

    public void setNot_version_alert(String not_version_alert) {
        this.not_version_alert = not_version_alert;
    }

    public void setBate(int bate) {
        this.bate = bate;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public void setIsqz(String isqz) {
        this.isqz = isqz;
    }

    public void setQzcont(String qzcont) {
        this.qzcont = qzcont;
    }

    public int getBate() {
        return bate;
    }

    public String getDownload() {
        return download;
    }

    public String getIsqz() {
        return isqz;
    }

    public String getQzcont() {
        return qzcont;
    }

    public String getShowversion() {
        return showversion;
    }

    public void setShowversion(String showversion) {
        this.showversion = showversion;
    }

    @Override
    public String toString() {
        return "UpdateMsg{" +
                "bate=" + bate +
                ", showversion='" + showversion + '\'' +
                ", download='" + download + '\'' +
                ", isqz='" + isqz + '\'' +
                ", qzcont='" + qzcont + '\'' +
                '}';
    }
}
