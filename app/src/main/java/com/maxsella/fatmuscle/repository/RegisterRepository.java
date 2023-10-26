package com.maxsella.fatmuscle.repository;

import com.maxsella.fatmuscle.db.bean.User;
import com.maxsella.fatmuscle.db.helper.UserHelper;

import java.util.ArrayList;
import java.util.List;

public class RegisterRepository {

    private String msg;
    public String saveUser(User user){
        if (!registryUser(user)){
            msg = "注册失败";
            return msg;
        }
        UserHelper.getInstance().saveOrUpdate(user);
        msg = "注册成功";
        return msg;
    }

    public boolean registryUser(User user){
        if (user.getTelephone()==null){
            //通过邮箱注册
            return true;
        }
        //通过手机号注册用户
        return true;
    }

    public String getVerifyCode(){
        //获取验证码
        return "111";
    }
    private List<String> getAllTel(){
        List<String> tel1 = new ArrayList<>();
        tel1 = UserHelper.getInstance().getAllUserPhoneNumbers();
        return tel1;
    }
    private List<String> getAllEmail(){
        List<String> email1 = new ArrayList<>();
        email1 = UserHelper.getInstance().getAllUserEmails();
        return email1;
    }
    public boolean verifyEmail(String email){
        List<String> email1 = getAllEmail();
        if (email1.contains(email)) {
            return true;
        }
        //网络请求判断邮箱是否已经存在
        return false;
    }
    public boolean verifyTel(String tel){
        List<String> phone1 = getAllTel();
        if (phone1.contains(tel)) {
            return true;
        }
        //网络请求判断手机号是否已经存在
        return false;
    }
}
