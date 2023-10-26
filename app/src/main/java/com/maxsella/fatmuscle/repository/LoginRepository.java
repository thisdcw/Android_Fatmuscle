package com.maxsella.fatmuscle.repository;

import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.db.bean.User;
import com.maxsella.fatmuscle.db.helper.UserHelper;

public class LoginRepository {

    private User user = new User();
    private String filed;

    public void saveOrUpdate(User user){
        UserHelper.getInstance().saveOrUpdate(user);
    }

    public User getLocalUser(String contact){
        user = UserHelper.getInstance().getUserByContact(contact);
        return user;
    }
    public User getNetworkUser(String contact){
        //此处使用网络请求
        return new User();
    }
}
