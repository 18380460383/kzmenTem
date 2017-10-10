package com.kzmen.sczxjf.sql.enterpriseDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * describe:
 * notice:
 * Created by FuPei on 2016/5/25.
 */
public class EnDBHelper extends SQLiteOpenHelper {

    /**
     * 数据库名称
     */
    private static final String DB_NAME = "enpriseDB";
    /**
     * 数据库版本
     */
    private final static int DB_VERSION = 1;

    public static final String KEY_ACCOUNT = "account";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_DATA = "date";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE =  "phone";
    public static final String KEY_USERKEY = "userkey";
    public static final String TABLE_USER = "en_user";

    private static final String CREATE_USER =
            "create table en_user(" +
            "account text," +
            "date text," +
            "name text," +
            "token text," +
            "phone text," +
            "userkey text" +
            ")";

    public EnDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String getValueByKey(String account, String key) {
        if(getToken(account).length() > 0) {
            Cursor cursor = getReadableDatabase().rawQuery("select " + key + " from " + TABLE_USER + " where account=?",
                    new String[]{account});
            if(cursor.moveToNext()) {
                return cursor.getString(0);
            }
        }
        return "";
    }

    /**
     * 添加用户信息
     * @param userDB
     */
    public void addEnUser(EnUserDB userDB) {
        removeUser(userDB.getAccount());
        getWritableDatabase().execSQL("insert into " + TABLE_USER + " (" +
                        KEY_ACCOUNT + "," + KEY_DATA + "," + KEY_NAME + "," + KEY_PHONE + "," +
                        KEY_TOKEN + "," + KEY_USERKEY + ") values(?,?,?,?,?,?)",
                new String[]{userDB.getAccount(), userDB.getDate(), userDB.getName(), userDB.getPhone(),
                        userDB.getToken(), userDB.getUserkey()});
    }

    /**
     * 移除账号记录
     * @param account
     */
    public void removeUser(String account) {
        getReadableDatabase().execSQL("delete from " + TABLE_USER + " where account=?",
                new String[]{account});
    }

    public String getToken(String account) {
        try {
            Cursor cursor = getReadableDatabase().rawQuery("select token,date from " + TABLE_USER + " where account=?",
                    new String[]{account});
            if (cursor.moveToNext()) {
                long date = cursor.getLong(1);
                long time = System.currentTimeMillis() - date;
                //判断是否过期（token一周过期）
                if (time / (24 * 60 * 60 * 1000 * 7) != 0) {
                    removeUser(account);
                    return "";
                }
                return cursor.getString(0);
            }
        }catch (Exception e){

        }
        return "";
    }

    public String getUserKey(String account){
        Cursor cursor = getReadableDatabase().rawQuery("select userkey from " + TABLE_USER + " where account=?",
                new String[]{account});
        if(cursor.moveToNext()) {
            return cursor.getString(0);
        }
        return "";
    }
}
