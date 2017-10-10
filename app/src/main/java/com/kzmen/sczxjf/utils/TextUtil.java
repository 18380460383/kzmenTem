package com.kzmen.sczxjf.utils;

/**
 * Created by pjj18 on 2017/8/29.
 */

public class TextUtil {
    public static  boolean isEmpty(String str){
        if(str==null){
            return true;
        }else if(str.isEmpty()){
            return true;
        }
        return false;
    }
}
