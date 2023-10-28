package com.maxsella.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.databinding.ActivityBindEmailBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.db.bean.User;
import com.maxsella.fatmuscle.viewmodel.UserInfoViewModel;

import java.security.PrivateKey;
import java.util.PrimitiveIterator;

public class BindEmailActivity extends BaseActivity {

    private ActivityBindEmailBinding binding;

    private UserInfoViewModel userInfoViewModel = new UserInfoViewModel();
    private String email;
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bind_email);
        // 获取传递的 Bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // 获取具体的值
            email = bundle.getString("email"); // key 是您传递数据时的键值
            // 在这里可以对获取到的值进行操作
            LogUtil.d("email: " + email);
        }
        userInfoViewModel.user.observe(this, user -> {
            binding.setViewModel(userInfoViewModel);
        });
        initClick();
    }

    private void initClick() {

        if (email != null && !email.equals("")) {
            LogUtil.d("email not null" );
            binding.showNow.setVisibility(View.VISIBLE);
            binding.change.setVisibility(View.GONE);
        } else {
            LogUtil.d("email is null");
            binding.showNow.setVisibility(View.GONE);
            binding.change.setVisibility(View.VISIBLE);
        }
        binding.btnChange.setOnClickListener(v -> {
            binding.showNow.setVisibility(View.GONE);
            binding.change.setVisibility(View.VISIBLE);
        });
        binding.btnSubmit.setOnClickListener(v -> {
            if (userInfoViewModel.changeEmail(binding.etEmail.getText().toString().trim())) {
                showMsg(userInfoViewModel.msg);
                navTo(AccountManageActivity.class);
            } else {
                showMsg(userInfoViewModel.failed);
            }
        });
    }

}