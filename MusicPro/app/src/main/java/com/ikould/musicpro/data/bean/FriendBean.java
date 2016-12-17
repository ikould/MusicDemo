package com.ikould.musicpro.data.bean;

/**
 * 好友实体类
 * <p>
 * Created by liudong on 2016/8/2.
 */
public class FriendBean {

    private int friendId;
    private String userName;
    private String friendName;
    private String friendSign;
    private String iconPath;

    public FriendBean(int friendId, String userName, String friendName, String friendSign, String iconPath) {
        this.friendId = friendId;
        this.userName = userName;
        this.friendName = friendName;
        this.friendSign = friendSign;
        this.iconPath = iconPath;
    }

    public FriendBean() {
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendSign() {
        return friendSign;
    }

    public void setFriendSign(String friendSign) {
        this.friendSign = friendSign;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    @Override
    public String toString() {
        return "FriendBean{" +
                "friendId=" + friendId +
                ", userName='" + userName + '\'' +
                ", friendName='" + friendName + '\'' +
                ", friendSign='" + friendSign + '\'' +
                ", iconPath='" + iconPath + '\'' +
                '}';
    }
}
