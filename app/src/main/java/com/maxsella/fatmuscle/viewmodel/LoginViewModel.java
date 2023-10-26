package com.maxsella.fatmuscle.viewmodel;

import com.maxsella.fatmuscle.common.base.BaseViewModel;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.db.bean.User;
import com.maxsella.fatmuscle.repository.LoginRepository;

import java.util.regex.Pattern;

public class LoginViewModel extends BaseViewModel {

    private LoginRepository loginRepository = new LoginRepository();
    private User user = new User();
    String patternPhone = "^1\\d{10}$";
    String patternEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{1,}$";
    public boolean toLogin(String contact, String password) {
        LogUtil.d("toLogin: " + contact + password);
        if (contact.equals("")) {
            failed = "账户不能为空";
            return false;
        }
        if (password.equals("")) {
            failed = "密码不能为空";
            LogUtil.d("密码为空");
            return false;
        }
        user = getUser(contact);
        if (user == null) {
            failed = "账户不存在";
            return false;
        }
        if (user.getPassword().equals(password)) {
            changeLoginStatus(1);
            LogUtil.d(user.toString());
            return true;
        }
        failed = "账号或密码错误";
        return false;
    }

    public User getUser(String contact) {
        User localUser = loginRepository.getLocalUser(contact);
        if (localUser != null) {
            LogUtil.d("not empty");
            user = localUser;
            return user;
        }
        return null;
    }

    public void changeLoginStatus(int isLogin) {
        user.setIsLogin(isLogin);
        loginRepository.saveOrUpdate(user);
    }

}
