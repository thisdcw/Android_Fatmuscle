package com.maxsella.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.cw.fatmuscle.databinding.ActivityEditTelephoneBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.db.bean.User;
import com.maxsella.fatmuscle.viewmodel.UserInfoViewModel;

public class EditTelephoneActivity extends BaseActivity {

    ActivityEditTelephoneBinding editTelephoneBinding;

    private UserInfoViewModel userInfoViewModel = new UserInfoViewModel();

    private String tel;

    @Override
    protected void initView() {
        editTelephoneBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_telephone);
        // 获取传递的 Bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // 获取具体的值
            tel = bundle.getString("tel"); // key 是您传递数据时的键值
            // 在这里可以对获取到的值进行操作
            LogUtil.d("value: " + tel);
        }
        userInfoViewModel.user.observe(this, user -> {
            editTelephoneBinding.setViewmodel(userInfoViewModel);
        });
        initClick();
    }

    private void initClick() {
        if (tel != null && !tel.equals("")) {
            LogUtil.d("tel not null");
            editTelephoneBinding.showNow.setVisibility(View.VISIBLE);
            editTelephoneBinding.change.setVisibility(View.GONE);
        } else {
            LogUtil.d("tel is null");
            editTelephoneBinding.showNow.setVisibility(View.GONE);
            editTelephoneBinding.change.setVisibility(View.VISIBLE);
        }
        editTelephoneBinding.btnChange.setOnClickListener(v -> {
            editTelephoneBinding.change.setVisibility(View.VISIBLE);
            editTelephoneBinding.showNow.setVisibility(View.GONE);
        });
        editTelephoneBinding.getVerifyCode.setOnClickListener(v -> {
            //获取验证码
            userInfoViewModel.getVerify();
        });
        editTelephoneBinding.btnSubmit.setOnClickListener(v -> {
            //提交更改
            if (userInfoViewModel.changeTel(editTelephoneBinding.etTel.getText().toString().trim(), editTelephoneBinding.etVerifyCode.getText().toString().trim())) {
                showMsg(userInfoViewModel.msg);
                navTo(AccountManageActivity.class);
            } else {
                showMsg(userInfoViewModel.failed);
            }
        });
    }
}