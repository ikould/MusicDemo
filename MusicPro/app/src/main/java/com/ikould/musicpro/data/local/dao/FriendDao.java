package com.ikould.musicpro.data.local.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ikould.musicpro.data.bean.FriendBean;
import com.ikould.musicpro.data.local.sqlite.SqliteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 好友数据库查询
 * <p>
 * Created by liudong on 2016/8/2.
 */
public class FriendDao {
    private SqliteHelper mSqliteHelper;

    public FriendDao(Context context) {
        mSqliteHelper = new SqliteHelper(context);
    }

    /**
     * 通过用户名获取所有好友信息
     *
     * @param userName
     * @return
     */
    public List<FriendBean> getFriendsByUser(String userName) {
        List<FriendBean> friends = new ArrayList<>();
        SQLiteDatabase db = mSqliteHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from friends where userName = ? ", new String[]{userName});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                FriendBean friend = new FriendBean(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                friends.add(friend);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return friends;
    }

    /**
     * 添加好友
     *
     * @param friend
     */
    public void addFriend(FriendBean friend) {
        SQLiteDatabase db = mSqliteHelper.getWritableDatabase();
        db.execSQL("insert into friends(friendId,userName,friendName,friendSign,iconPath) values(?,?)",
                new String[]{String.valueOf(friend.getFriendId()), friend.getUserName(), friend.getFriendName(), friend.getFriendSign(), friend.getIconPath()});
        db.close();
    }

    /**
     * 获取最大id
     */
    public int getMaxFriendId() {
        int maxId = 0;
        SQLiteDatabase db = mSqliteHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from friends", new String[]{});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                if (id > maxId) {
                    maxId = id;
                }
            }
            cursor.close();
        }
        return maxId;
    }
}
