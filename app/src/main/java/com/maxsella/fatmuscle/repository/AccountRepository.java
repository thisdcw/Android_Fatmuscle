package com.maxsella.fatmuscle.repository;

import androidx.lifecycle.MutableLiveData;

import com.maxsella.fatmuscle.db.bean.User;
import com.maxsella.fatmuscle.db.helper.UserHelper;

import java.util.ArrayList;
import java.util.List;

public class AccountRepository {


    /**
     * 获取当前登录的用户
     *
     * @return
     */
    public MutableLiveData<User> getLoginUser() {
        MutableLiveData<User> user = new MutableLiveData<>();
        user.setValue(UserHelper.getInstance().getLoginUser());
        return user;
    }

    /**
     * 删除所有用户
     */
    public void deleteAllUser() {
        UserHelper.getInstance().clearAllUsers();
    }

    /**
     * 判断邮箱是否已经注册
     *
     * @param email
     * @return
     */
    public boolean verifyEmail(String email) {
        List<String> emails = getAllEmail();
        return emails.contains(email);
    }

    /**
     * 判断手机号是否已经注册
     *
     * @param tel
     * @return
     */
    public boolean verifyTel(String tel) {
        List<String> phone1 = getAllTel();
        if (phone1.contains(tel)) {
            return true;
        }
        //网络请求判断手机号是否已经存在
        return false;
    }

    /**
     * 获取本地所有已注册用户邮箱
     *
     * @return
     */
    private List<String> getAllEmail() {
        List<String> email1 = new ArrayList<>();
        email1 = UserHelper.getInstance().getAllUserEmails();
        return email1;
    }

    /**
     * 获取本地所有已注册用户手机号
     *
     * @return
     */
    private List<String> getAllTel() {
        List<String> tel1 = new ArrayList<>();
        tel1 = UserHelper.getInstance().getAllUserPhoneNumbers();
        return tel1;
    }

    /**
     * 更新用户
     *
     * @param user
     */
    public void updateUser(User user) {
        UserHelper.getInstance().saveOrUpdate(user);
    }

    /**
     * 获取本地用户
     *
     * @param contact
     * @return
     */
    public User getLocalUser(String contact) {
        return UserHelper.getInstance().getUserByContact(contact);
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public String getVerifyCode() {
        //获取验证码
        return "111";
    }
}
