package com.kzmen.sczxjf.server;

import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 异常捕获
 * Created by hzwangchenyan on 2016/1/25.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault());

    private Thread.UncaughtExceptionHandler mDefaultHandler;

    public static CrashHandler getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static CrashHandler instance = new CrashHandler();
    }

    public void init() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        saveCrashInfo(ex);
        mDefaultHandler.uncaughtException(thread, ex);
    }

    private void saveCrashInfo(Throwable ex) {

    }

    private String getInfo() {
        String time = TIME_FORMAT.format(new Date());
        String device = Build.MANUFACTURER + "/" + Build.MODEL + "/" + Build.VERSION.RELEASE;
        StringBuilder sb = new StringBuilder();
        sb.append("*** time: ").append(time).append(" ***").append("\n");
        sb.append("*** device: ").append(device).append(" ***").append("\n");
        return sb.toString();
    }
}
