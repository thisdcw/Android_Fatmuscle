package com.maxsella.fatmuscle.repository;

import com.maxsella.fatmuscle.db.bean.User;
import com.maxsella.fatmuscle.db.helper.UserHelper;

public class ForgetRepository {


    public void updateUser(User user) {
        if (fondByNetwork(user)){
            //必须要网络请求成功才能保存本地
            User fondUser = UserHelper.getInstance().getUserByContact(user.getTelephone());
            fondUser.setPassword(user.getPassword());
            UserHelper.getInstance().saveOrUpdate(fondUser);
        }
    }

    private boolean fondByNetwork(User user) {
        if (user.getTelephone().equals("")) {
            //通过邮箱
            return true;
        }
        //通过手机号
        return true;

    }

    public String getVerifyCode() {
        //获取验证码
        return "111";
    }
}
