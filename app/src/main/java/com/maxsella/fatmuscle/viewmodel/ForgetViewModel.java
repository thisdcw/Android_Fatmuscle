package com.maxsella.fatmuscle.viewmodel;

import com.maxsella.fatmuscle.common.base.BaseViewModel;
import com.maxsella.fatmuscle.db.bean.User;
import com.maxsella.fatmuscle.repository.ForgetRepository;

import org.litepal.LitePal;

import java.util.regex.Pattern;

public class ForgetViewModel extends BaseViewModel {

    private ForgetRepository forgetRepository = new ForgetRepository();
    String patternPhone = "^1\\d{10}$";
    String patternEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+. [a-zA-Z]{2,}$";

    private String code = "";
    private User user = new User();
    public boolean fond(String contact, String password, String verifyCode){
        Pattern phone = Pattern.compile(patternPhone);
        Pattern email = Pattern.compile(patternEmail);

        if (phone.matcher(contact).find()) {
            if (code.equals("")){
                failed = "请输入验证码";
                return false;
            }
            user.setTelephone(contact);
            user.setPassword(password);
            if (!code.equals(verifyCode)) {
                failed = "验证码错误";
                return false;
            }
            forgetRepository.updateUser(user);
            return true;
        } else if (email.matcher(contact).find()) {
            user.setEmail(contact);
            user.setPassword(password);
            forgetRepository.updateUser(user);
            return true;
        }
        failed = "账号或密码不符合规则";
        return false;
    }

    public void getVerifyCode() {
        code = forgetRepository.getVerifyCode();
    }

}
