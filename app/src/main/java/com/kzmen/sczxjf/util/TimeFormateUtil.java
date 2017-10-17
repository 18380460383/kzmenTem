package com.kzmen.sczxjf.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pjj18 on 2017/10/8.
 */

public class TimeFormateUtil {
    public static String formateTime(String time) {
        int musicTime = Integer.valueOf(time);
        int min = musicTime / 60;
        int sec = musicTime % 60;
        String show = (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
        return show;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        long time = Long.valueOf(s) * 1000L;
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(time);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}
