package com.maxsella.fatmuscle.viewmodel;

import com.maxsella.fatmuscle.common.base.BaseViewModel;
import com.maxsella.fatmuscle.common.util.Config;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.db.bean.User;
import com.maxsella.fatmuscle.db.helper.UserHelper;
import com.maxsella.fatmuscle.repository.AccountRepository;

public class LoginViewModel extends BaseViewModel {

    private AccountRepository accountRepository = new AccountRepository();
    private User user = new User();

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
        User localUser = accountRepository.getLocalUser(contact);
        if (localUser != null) {
            LogUtil.d("not empty"+localUser.toString());
            user = localUser;
            return user;
        }
        return null;
    }

    public void changeLoginStatus(int isLogin) {
        user.setIsLogin(isLogin);
        accountRepository.updateUser(user);
        Config.saveOrUpdateChooseMember(UserHelper.getInstance().getLoginUser().getNickname());
    }

    public boolean checkFirst() {
        LogUtil.d("checkFirst: "+user.isFirst());
        return user.isFirst();
    }

}
