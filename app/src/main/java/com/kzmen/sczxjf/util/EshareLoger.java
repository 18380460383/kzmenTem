package com.kzmen.sczxjf.util;

import android.util.Log;

/**
 * 打印日志
 * Created by Group
 * on 2015/11/20.
 * at 15:30
 */
public class EshareLoger {

    private static final String TAG = "fupei";
    private static final boolean CAN_LOG = true;

    public static void logI(String info) {
        if(CAN_LOG) {
            if(null == info) {
                Log.i(TAG, "打印了空对象");
            } else {
                Log.i(TAG, info);
            }
        }
    }

    public static void logI(boolean show, String info) {
        if(CAN_LOG && show) {
            if(null == info) {
                Log.i(TAG, "打印了空对象");
            } else {
                Log.i(TAG, info);
            }
        }
    }

    public static void logW(String info) {
        if(CAN_LOG) {
            if(null == info) {
                Log.w(TAG, "打印了空对象");
            } else {
                Log.w(TAG, info);
            }
        }
    }

    public static void logD(String info) {
        if(CAN_LOG) {
            if(null == info) {
                Log.d(TAG, "打印了空对象");
            } else {
                Log.d(TAG, info);
            }
        }
    }

    public static void logE(String info) {
        if(CAN_LOG) {
            if(null == info) {
                Log.e(TAG, "打印了空对象");
            } else {
                Log.e(TAG, info);
            }
        }
    }
}
