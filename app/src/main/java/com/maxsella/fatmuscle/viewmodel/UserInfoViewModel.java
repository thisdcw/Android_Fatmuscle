package com.maxsella.fatmuscle.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.maxsella.fatmuscle.common.base.BaseViewModel;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.db.bean.User;
import com.maxsella.fatmuscle.repository.AccountRepository;

import java.util.regex.Pattern;

public class UserInfoViewModel extends BaseViewModel {

    private AccountRepository accountRepository = new AccountRepository();

    public LiveData<User> user = getLiveData();

    public String msg = "";
    private String code = "";
    private String patternPhone = "^1\\d{10}$";
    private String patternEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{1,}$";

    private MutableLiveData _user;

    private LiveData<User> getLiveData() {
        if (_user == null) {
            _user = getLiveData("user");
            _user = accountRepository.getLoginUser();
        }
        return _user;
    }

    public void updateUser(User user) {
        accountRepository.updateUser(user);
        _user.setValue(user);
    }

    public void logout() {
        User user1 = user.getValue();
        user1.setIsLogin(0);
        updateUser(user1);
        LogUtil.d(user1.toString());
    }

    public boolean changeEmail(String email) {
        User user1 = user.getValue();
        Pattern email1 = Pattern.compile(patternEmail);
        if (email1.equals("")) {
            failed = "请输入邮箱";
            return false;
        }
        if (!email1.matcher(email).find()) {
            failed = "邮箱格式不正确";
            return false;
        }
        if (user1.getEmail() != null && user1.getEmail().equals(email)) {
            failed = "新邮箱不能与旧邮箱相同";
            return false;
        }
        //TODO 添加校验
        msg = "邮箱绑定成功";
        user1.setEmail(email);
        updateUser(user1);
        return true;
    }

    public boolean changePassword(String _old, String _new, String _new2) {
        User user1 = user.getValue();
        String old = user1.getPassword();
        if (!old.equals(_old)) {
            failed = "原密码错误";
            return false;
        }
        if (!_new.equals(_new2)) {
            failed = "两次输入的密码不一致,请重新输入";
            return false;
        }
        if (old.equals(_new)) {
            failed = "新密码不能与旧密码相同,请重新输入";
            return false;
        }
        user1.setPassword(_new);
        updateUser(user1);
        msg = "密码重置成功,请重新登陆";
        return true;
    }

    public boolean changeTel(String tel, String verifyCode) {
        Pattern phone = Pattern.compile(patternPhone);
        User user1 = user.getValue();
        if (verifyCode.equals("")) {
            failed = "请输入验证码";
            return false;
        }
        if (user1.getTelephone() != null && user1.getTelephone().equals(tel)) {
            failed = "新手机号不能与旧手机号相同";
            return false;
        }
        if (!phone.matcher(tel).find()) {
            failed = "手机号码格式不正确";
            return false;
        }
        if (!code.equals(verifyCode)) {
            failed = "验证码错误";
        }
        user1.setTelephone(tel);
        updateUser(user1);
        msg = "手机号更换成功";
        return true;
    }

    public void getVerify() {
        code = accountRepository.getVerifyCode();
    }

    public void deleteAllUser() {
        accountRepository.deleteAllUser();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
