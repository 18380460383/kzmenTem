package com.kzmen.sczxjf.utils;

import android.text.format.DateUtils;

import java.util.Locale;

/**
 * Created by pjj18 on 2017/9/21.
 */

public class SystemUtil {
    public static String form(long time) {
        return formatTime("mm:ss", time);
    }

    public static String formatTime(String pattern, long milli) {
        int m = (int) (milli / DateUtils.MINUTE_IN_MILLIS);
        int s = (int) ((milli / DateUtils.SECOND_IN_MILLIS) % 60);
        String mm = String.format(Locale.getDefault(), "%02d", m);
        String ss = String.format(Locale.getDefault(), "%02d", s);
        return pattern.replace("mm", mm).replace("ss", ss);
    }
}
