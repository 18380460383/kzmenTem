package com.kzmen.sczxjf.bean.entitys;

import com.google.gson.Gson;

/**
 * describe:
 * notice:
 * Created by FuPei on 2016/5/26.
 */
public class SelectEntity {

    private int isCheck;

    public SelectEntity() {
        isCheck = 0;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
