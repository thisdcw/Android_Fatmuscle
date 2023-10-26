package com.maxsella.fatmuscle.db.helper;

import com.maxsella.fatmuscle.db.bean.User;


import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserHelper {

    private ExecutorService service = Executors.newSingleThreadExecutor();

    public static UserHelper getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static UserHelper INSTANCE = new UserHelper();
    }

    public void saveOrUpdate(User user) {
        service.execute(() -> user.saveOrUpdate("userId = ?", String.valueOf(user.getUserId())));
    }

    public List<String> getAllUserPhoneNumbers() {
        List<User> userList = LitePal.select("telephone").find(User.class);
        List<String> phoneNumbers = new ArrayList<>();
        for (User user : userList) {
            phoneNumbers.add(user.getTelephone());
        }
        return phoneNumbers;
    }

    public List<String> getAllUserEmails() {
        List<User> userList = LitePal.select("email").find(User.class);
        List<String> emails = new ArrayList<>();
        for (User user : userList) {
            emails.add(user.getEmail());
        }
        return emails;
    }
    public void clearAllUsers() {
        LitePal.deleteAll(User.class);
    }



    public User getUserByContact(String contact) {
        return LitePal.where("telephone = ? or email = ?", contact, contact).findFirst(User.class);
    }

    public User getLoginUser() {
        return LitePal.where("isLogin = 1").findFirst(User.class);
    }

}
