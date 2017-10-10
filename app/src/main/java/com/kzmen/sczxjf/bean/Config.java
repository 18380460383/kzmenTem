package com.kzmen.sczxjf.bean;

import java.util.Map;

/**
 * Created by 杨操 on 2015/12/15.
 */
public class Config {
    private Map<String,String> devmap;
    private String ver;
    private Map<String,String> promap;



    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public Map<String, String> getDevmap() {
        return devmap;
    }

    public void setDevmap(Map<String, String> devmap) {
        this.devmap = devmap;
    }

    public Map<String, String> getPromap() {
        return promap;
    }

    public void setPromap(Map<String, String> promap) {
        this.promap = promap;
    }

    @Override
    public String toString() {
        return "Config{" +
                "devmap=" + devmap +
                ", ver='" + ver + '\'' +
                ", promap=" + promap +
                '}';
    }
}
