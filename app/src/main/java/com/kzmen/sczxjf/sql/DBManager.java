package com.kzmen.sczxjf.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 创建者：杨操
 * 时间：2016/4/27
 * 功能描述：本地数据库操作类
 */
public class DBManager {
    private static DBManager dbManager;
    private EshareSQLHelper helper;
    private SQLiteDatabase database;

    private DBManager(Context context) {
        helper=new EshareSQLHelper(context);
    }
    public static DBManager getDBManager(Context context){
        if(dbManager==null){
            dbManager=new DBManager(context);
        }
        return  dbManager;
    }

    /**
     * 插入数据
     * @param tableName 表名
     * @param nullColumnHack 传值全为空时存的字段
     * @param values 键值对 数据
     * @return 当前插入的行
     */
    public long insert(String tableName,String nullColumnHack,ContentValues values){
        getDatabase();
        database.beginTransaction();  //开始事务
        try{
            long insert = database.insert(tableName, nullColumnHack, values);
            database.setTransactionSuccessful();  //设置事务成功完成
            return  insert;
        }catch (Exception e){

        }finally {
            database.endTransaction();
        }
        return 0;
    }

    private void getDatabase() {
        if(database==null){
            database=helper.getWritableDatabase();
        }
    }

    /**
     * @param tableName 表名
     * @param values 键值对 数据
     * @param where where条件（键=？）
     * @param wherevalues where条件（值）
     * @return
     */
    public int update(String tableName,ContentValues values,String where,String[] wherevalues){
        getDatabase();
        database.beginTransaction();  //开始事务
        try{
            int update = database.update(tableName, values, where, wherevalues);
            database.setTransactionSuccessful();  //设置事务成功完成
            return update;
        }catch (Exception e){

        }finally {
            database.endTransaction();
        }
        return 0;
    }

    /**
     * @param distinct 确定每一行数据是不是唯一的
     * @param tableName 表名
     * @param columns 返回的列的列表。如果为空返回的所有列，这是为了阻止readingdata存储，不会用
     * @param selection where条件（键=？）
     * @param selectionvalues where条件（值）
     * @param orderBy //<---DESC/ASC:降序/升序(格式是String orderBy = "_id desc")
     * @param limit 列数格式-》（0，10）
     */
    public Cursor query(boolean distinct,String tableName,String[] columns,String selection,String[] selectionvalues,String orderBy,String limit){
        getDatabase();
        Cursor query = database.query(distinct, tableName, columns, selection, selectionvalues, null, null, orderBy, limit);
        return query;
    }
    public void cloaseDB(){
        if(database.isOpen()){
            database.close();
        }
    }

    /**
     * @param tableName 表名
     * @param where 依据字段
     * @param wherevalues 依据条件
     * @return
     */
    public int delete(String tableName,String where,String[] wherevalues){
        getDatabase();
        database.beginTransaction();  //开始事务
        try{
            int delete = database.delete(tableName, where, wherevalues);
            database.setTransactionSuccessful();  //设置事务成功完成
            return delete;
        }catch (Exception e){

        }finally {
            database.endTransaction();
        }
        return 0;
    }

}
