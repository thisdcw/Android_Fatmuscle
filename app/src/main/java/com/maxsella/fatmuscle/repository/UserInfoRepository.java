package com.maxsella.fatmuscle.repository;

import androidx.lifecycle.MutableLiveData;

import com.maxsella.fatmuscle.db.bean.User;
import com.maxsella.fatmuscle.db.helper.UserHelper;

import java.util.ArrayList;

public class UserInfoRepository {
    private MutableLiveData<User> user = new MutableLiveData<>();
    public MutableLiveData<User> getLoginUser() {

        user.setValue(UserHelper.getInstance().getLoginUser());
        return user;
    }

    public void updateUser(User user1) {
        user.setValue(user1);
        UserHelper.getInstance().saveOrUpdate(user1);
    }

    public String getVerify(){
        return "111";
    }

    public void deleteAllUser(){
        UserHelper.getInstance().clearAllUsers();
    }
}
