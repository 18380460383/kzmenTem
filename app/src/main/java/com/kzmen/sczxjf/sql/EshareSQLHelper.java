package com.kzmen.sczxjf.sql;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.kzmen.sczxjf.Constants;

/**
 * 创建者：杨操
 * 时间：2016/4/26
 * 功能描述： 本地数据库
 */
public class EshareSQLHelper extends SQLiteOpenHelper {


    public EshareSQLHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String news= "CREATE TABLE `" + Constants.DB_USER_NEWS + "` (\n" +
                "  `nid` int(11) DEFAULT '0',\n" +//新闻ID
                "  `typename` varchar(12) NOT NULL ,\n" +//类型名
                "  `title` varchar(255) NOT NULL ,\n" +//标题
                "  `keywords` varchar(125) DEFAULT NULL ,\n" +//关键字
                "  `description` varchar(255) DEFAULT NULL,\n" +//
                "  `collect` int(11) DEFAULT '0' ,\n" +//收藏数
                "  `relay` int(11) DEFAULT '0' ,\n" +//转发数
                "  `comment` int(11) DEFAULT '0',\n" +//评论数
                "  `image` varchar(125) DEFAULT NULL,\n" +//标题图
                "  `opinion` int(11) DEFAULT 0,\n" +
                "  `datetime` varchar(30) DEFAULT '暂无',\n" +//处理时间
                "  `source` varchar(55) DEFAULT '享一下',\n" +//来源
                "  `opinions` text DEFAULT NULL,\n" +//观点信息
                "  `opt_id` varchar(8),\n" +//我的观点
                "  `mycollect` int(11) DEFAULT 0,\n" +//自己是否收藏
                "  `relay_url` varchar(125),\n" +//分享链接
                "  `content` text,\n"+//类容
                "  `hits` int(11) DEFAULT 0,\n" +//浏览数
                "  `jumpurl` text,\n"+//跳转链接
                "  `jumptype` int(11) DEFAULT 0,\n" +//跳转类型0是本地1是网页
                "  `type_color` varchar(16) DEFAULT 'c5c9cb')";
        String comment= "CREATE TABLE `" + Constants.DB_USER_NEWS_COMMENT + "` (\n" +
                "  `comment_id` Integer PRIMARY KEY autoincrement,\n" +
                "  `nid` int(11) NOT NULL DEFAULT '0',\n" +
                "  `cid` int(11) NOT NULL DEFAULT '0',\n" +
                "  `uid` int(11) DEFAULT '0',\n" +
                "  `opt_id` char(15) DEFAULT 'NULL',\n" +
                "  `is_reply` tinyint(2) DEFAULT '0',\n" +
                "  `to_uid` int(11) DEFAULT '0',\n" +
                "  `to_username` varchar(16) DEFAULT '0',\n" +
                "  `username` varchar(16) DEFAULT '0',\n" +
                "  `content` varchar(255) DEFAULT NULL,\n" +
                "  `datetime` varchar(25) DEFAULT '暂无',\n" +
                "  `imageurl` varchar(255) DEFAULT NULL,\n" +
                "  `like` int(11) DEFAULT '0' ,\n" +
                "  `relay` int(11) DEFAULT '0',\n" +
                "  `report` int(11) DEFAULT '0',\n" +
                "  `is_z` int(1) DEFAULT '0',\n" +
                "  `relay_url` varchar(125) DEFAULT NULL,\n" +
                "  `reply` int(11) DEFAULT '0')";
        String pdf= "CREATE TABLE `" + Constants.DB_PDF + "` (\n" +
                "  `nid` int(11) NOT NULL DEFAULT '0',\n" +
                "  `pdf_id` Integer PRIMARY KEY autoincrement,\n" +
                "  `pdf_url` varchar(255) DEFAULT NULL,\n" +
                "  `dowid` int(11) DEFAULT '0')";
        db.execSQL(news);
        db.execSQL(comment);
        db.execSQL(pdf);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlupdatahits_Version_2="ALTER TABLE "+Constants.DB_USER_NEWS+" ADD COLUMN hits BLOB int(11) DEFAULT '0'";
        String sqlupdatacocle_Version_2="ALTER TABLE "+Constants.DB_USER_NEWS+" ADD COLUMN type_color BLOB varchar(16) DEFAULT 'c5c9cb'";
        String sqlupdatajumpurl_Version_3="ALTER TABLE "+Constants.DB_USER_NEWS+" ADD COLUMN jumpurl BLOB text";
        String sqlupdatajumptype_Version_3="ALTER TABLE "+Constants.DB_USER_NEWS+" ADD COLUMN jumptype BLOB int(11) DEFAULT '0'";
        if(oldVersion==1){
            db.execSQL(sqlupdatahits_Version_2);
            db.execSQL(sqlupdatacocle_Version_2);
            db.execSQL(sqlupdatajumpurl_Version_3);
            db.execSQL(sqlupdatajumptype_Version_3);
        }else if(oldVersion==2){
            db.execSQL(sqlupdatajumpurl_Version_3);
            db.execSQL(sqlupdatajumptype_Version_3);

        }

    }
}
