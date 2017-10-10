package com.kzmen.sczxjf.interfaces;

/**
 * Created by pjj18 on 2017/9/6.
 */

public interface UserOperate {
    void onOperateSuccess(String opType,String type,String state,String id);
    void onError(String type);
}
