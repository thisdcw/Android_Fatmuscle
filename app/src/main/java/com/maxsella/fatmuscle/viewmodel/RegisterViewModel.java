package com.maxsella.fatmuscle.viewmodel;

import com.maxsella.fatmuscle.common.base.BaseViewModel;
import com.maxsella.fatmuscle.db.bean.User;
import com.maxsella.fatmuscle.repository.RegisterRepository;

import org.litepal.LitePal;

import java.util.regex.Pattern;

public class RegisterViewModel extends BaseViewModel {

    private RegisterRepository registerRepository = new RegisterRepository();
    String patternPhone = "^1\\d{10}$";
    String patternEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{1,}$";
    private User user = new User();

    private String verifyCode = "";
    public String msg = "";

    public boolean registerUser(String contact, String password, String code) {
        Pattern phone = Pattern.compile(patternPhone);
        Pattern email = Pattern.compile(patternEmail);
        User lastUser = LitePal.findLast(User.class);
        if (lastUser == null) {
            user.setUserId(1);
        } else {
            user.setUserId(lastUser.getUserId() + 1);
        }
        if (phone.matcher(contact).find()) {
            if (registerRepository.verifyTel(contact)){
                failed = "手机号已被其他账号绑定";
                return false;
            }
            if (code.equals("")){
                failed = "请输入验证码";
                return false;
            }
            if (password.equals("")){
                failed = "请输入密码";
                return false;
            }
            if (!code.equals(verifyCode)) {
                failed = "验证码错误";
                return false;
            }
            user.setTelephone(contact);
            user.setPassword(password);
            msg = registerRepository.saveUser(user);
            return true;
        } else if (email.matcher(contact).find()) {
            if (registerRepository.verifyEmail(contact)){
                failed = "邮箱已被其他邮箱绑定";
                return false;
            }
            if (password.equals("")){
                failed = "请输入密码";
                return false;
            }
            user.setEmail(contact);
            user.setPassword(password);
            msg = registerRepository.saveUser(user);
            return true;
        }
        failed = "账号或密码不符合规则";
        return false;
    }

    public void getVerifyCode() {
        verifyCode = registerRepository.getVerifyCode();
    }

}
