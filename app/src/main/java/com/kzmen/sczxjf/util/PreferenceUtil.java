package com.kzmen.sczxjf.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

/**
 * 首选项工具
 */
public class PreferenceUtil {

//    private static Gson gson = new Gson();

    private static String PREF_NAME = "rmisinfo.pref";


    /**
     * 是否存在'key'配置
     */
    public static boolean hasKey(Context context, final String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).contains(key);
    }

    /**
     * 清空配置项
     */
    public static void clearPreference(Context context, final SharedPreferences p) {
        final Editor editor = p.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 移除某个配置
     */
    public static void removePreference(Context context, String key) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().remove(key).commit();
    }


    public static String getPrefString(Context context, String key, final String defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getString(key, defaultValue);
    }

    public static void setPrefString(Context context, final String key, final String value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putString(key, value).apply();
    }

    public static boolean getPrefBoolean(Context context, final String key, final boolean defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getBoolean(key, defaultValue);
    }

    public static void setPrefBoolean(Context context, final String key, final boolean value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putBoolean(key, value).apply();
    }

    public static int getPrefInt(Context context, final String key, final int defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getInt(key, defaultValue);
    }

    public static void setPrefInt(Context context, final String key, final int value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putInt(key, value).apply();
    }

    public static float getPrefFloat(Context context, final String key, final float defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getFloat(key, defaultValue);
    }

    public static void setPrefFloat(Context context, final String key, final float value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putFloat(key, value).apply();
    }

    public static long getPrefLong(Context context, final String key, final long defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getLong(key, defaultValue);
    }

    public static void setPrefLong(Context context, final String key, final long value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putLong(key, value).apply();
    }



    /**
     * 取出一个对象dsq
     *
     */
    public static <T> T get(Context context, String key, Class<T> clazz) {
        Gson gson = new Gson();

        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        T t = null;
        String value = settings.getString(key, null);
        if (value != null) {
            t = gson.fromJson(value, clazz);
        }
        return t;
    }

    /**
     * 放入一个对象
     *
     */
    public static <T> void put(Context context, String key, T t) {
        Gson gson = new Gson();

        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String value = gson.toJson(t);
        settings.edit().putString(key, value).apply();
    }


}
