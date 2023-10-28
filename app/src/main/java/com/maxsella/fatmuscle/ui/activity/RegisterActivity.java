package com.maxsella.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.databinding.ActivityRegisterBinding;
import com.maxsella.fatmuscle.common.util.ActivityStackManager;
import com.maxsella.fatmuscle.db.bean.User;
import com.maxsella.fatmuscle.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {


    private ActivityRegisterBinding registerBinding;
    private RegisterViewModel registerViewModel = new RegisterViewModel();
    private boolean isEmail = false;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStackManager.getInstance().addActivity(this);
        registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        initView();
    }

    private void initView() {
        registerBinding.itemTel.setOnClickListener(v -> {
            isEmail = false;
            registerBinding.telUnder.setVisibility(View.VISIBLE);
            registerBinding.emailUnder.setVisibility(View.GONE);
            registerBinding.lytEmail.setVisibility(View.GONE);
            registerBinding.lytTel.setVisibility(View.VISIBLE);
            registerBinding.fltVerifyCode.setVisibility(View.VISIBLE);
            registerBinding.edtTel.requestFocus();
        });
        registerBinding.itemEmail.setOnClickListener(v -> {
            isEmail = true;
            registerBinding.emailUnder.setVisibility(View.VISIBLE);
            registerBinding.telUnder.setVisibility(View.GONE);
            registerBinding.lytEmail.setVisibility(View.VISIBLE);
            registerBinding.lytTel.setVisibility(View.GONE);
            registerBinding.fltVerifyCode.setVisibility(View.GONE);
            registerBinding.edtEmail.requestFocus();
        });
        registerBinding.toLogin.setOnClickListener(v -> {
            ActivityStackManager.getInstance().startActivity(this, LoginActivity.class);
        });

        registerBinding.registerBtn.setOnClickListener(v -> {
            if (isEmail) {
                if (registerViewModel.registerUser(registerBinding.edtEmail.getText().toString().trim(), registerBinding.edtPassword.getText().toString().trim(),"")) {
                    ActivityStackManager.getInstance().startActivity(this, LoginActivity.class);
                    showMsg(registerViewModel.msg);
                }else {
                    showMsg(registerViewModel.failed);
                }
            } else {
                if (registerViewModel.registerUser(registerBinding.edtTel.getText().toString().trim(), registerBinding.edtPassword.getText().toString().trim(),registerBinding.edtVerifyCode.getText().toString().trim())) {
                    ActivityStackManager.getInstance().startActivity(this, LoginActivity.class);
                    showMsg(registerViewModel.msg);
                }else {
                    showMsg(registerViewModel.failed);
                }
            }
        });
        registerBinding.tvGetVerifyCode.setOnClickListener(v->{
            //获取验证码
            registerViewModel.getVerifyCode();
        });
    }

    protected void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}