package com.ikould.musicpro.data.local.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类
 * <p>创建数据库和数据表
 * Created by liudong on 2016/8/25.
 */
public class SqliteHelper extends SQLiteOpenHelper {

    public static final String name = "ikould";
    public static final int version = 1;

    public SqliteHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +
                "if not exists friends (" +
                "friendId integer primary key autoincrement," +
                "userName varchar(20)," +
                "friendName varchar(20)," +
                "friendSign varchar(20)," +
                "iconPath varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            //no do
        }
    }
}
