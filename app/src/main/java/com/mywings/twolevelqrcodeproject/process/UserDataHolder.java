package com.mywings.twolevelqrcodeproject.process;

import com.mywings.twolevelqrcodeproject.model.User;

public class UserDataHolder {

    private User selfUser;
    private User otherUser;


    public static UserDataHolder getInstance() {
        return UserInstanceHelper.INSTANCE;
    }

    public User getSelfUser() {
        return selfUser;
    }

    public void setSelfUser(User selfUser) {
        this.selfUser = selfUser;
    }

    public User getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(User otherUser) {
        this.otherUser = otherUser;
    }

    private static class UserInstanceHelper {
        private final static UserDataHolder INSTANCE = new UserDataHolder();
    }

}
