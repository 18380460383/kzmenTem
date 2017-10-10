package com.kzmen.sczxjf.util;

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
}
