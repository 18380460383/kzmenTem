package com.kzmen.sczxjf.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.bean.entitys.EnterPriseAppEntity;
import com.kzmen.sczxjf.sql.enterpriseDB.EnDBHelper;


/**
 * describe:
 * notice:
 * Created by FuPei on 2016/5/25.
 */
public class EnDataUtils {

    private static final String NAME_ENPRISE = "enprise";
    private static final String KEY_PASSWORD = "password";
    public static final String KEY_ACCOUNT = "account";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_MONEY = "money";
    /**
     * 企业信息
     */
    public static final String KEY_PRISE_INFO = "priseinfo";

    public static void savePassword(String account, String password) {
        SharedPreferences sharedPreferences = AppContext.getInstance().getSharedPreferences(
                NAME_ENPRISE + "_" + account, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(KEY_PASSWORD, AESUtils.encrypt(password)).commit();
    }

    public static String getPassWord(String account) {
        SharedPreferences sharedPreferences = AppContext.getInstance().getSharedPreferences(
                NAME_ENPRISE + "_" + account, Context.MODE_PRIVATE);
        String password = sharedPreferences.getString(KEY_PASSWORD, null);
        if(password != null) {
            password = new String(AESUtils.decrypt(password));
        }
        return password;
    }

    public static void saveAccount(String account) {
        SharedPreferences sharedPreferences = AppContext.getInstance().getSharedPreferences(
                NAME_ENPRISE, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(KEY_ACCOUNT, account).commit();
    }

    public static String getAccount() {
        SharedPreferences sharedPreferences = AppContext.getInstance().getSharedPreferences(
                NAME_ENPRISE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ACCOUNT, null);
    }

    /**
     * 是否保持登录
     * @return
     */
    public static boolean isKeepLogin(Context context) {
        String account = getAccount();
        if(account != null) {
            String token = new EnDBHelper(context).getToken(account);
            if(token != null && token.length() > 0) {
                return true;
            }
        }
        return false;
    }

    public static String getToken(Context context) {
        EnDBHelper helper = new EnDBHelper(context);
        return helper.getToken(getAccount());
    }

    public static String getUserKey(Context context) {
        EnDBHelper helper = new EnDBHelper(context);
        return helper.getUserKey(getAccount());
    }

    public static void saveValueByKey(String key, String value) {
        SharedPreferences sharedPreferences = AppContext.getInstance().getSharedPreferences(
                NAME_ENPRISE, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static String getValueByKey(String key) {
        SharedPreferences sharedPreferences = AppContext.getInstance().getSharedPreferences(
                NAME_ENPRISE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    public static EnterPriseAppEntity getPriseEntity() {
        SharedPreferences sharedPreferences = AppContext.getInstance().getSharedPreferences(
                NAME_ENPRISE, Context.MODE_PRIVATE);
        String text = sharedPreferences.getString(KEY_PRISE_INFO, null);
        EnterPriseAppEntity entity = new Gson().fromJson(text, EnterPriseAppEntity.class);
        return entity;
    }

    public static String getUID() {
       /* EnterPriseAppEntity entity = getPriseEntity();
        if(entity != null) {
            return entity.getUid();
        }*/
        return  AppContext.getInstance().getPEUser().getUid();
    }
}
