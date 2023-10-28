package com.maxsella.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.databinding.ActivityLoginBinding;
import com.maxsella.fatmuscle.common.util.ActivityStackManager;
import com.maxsella.fatmuscle.common.util.Config;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.db.bean.User;
import com.maxsella.fatmuscle.db.helper.UserHelper;
import com.maxsella.fatmuscle.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding loginBinding;

    private LoginViewModel loginViewModel = new LoginViewModel();

    private User user;

    private boolean isEmail = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStackManager.getInstance().addActivity(this);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initView();

    }

    private void initView() {
        loginBinding.itemTel.setOnClickListener(v -> {
            isEmail = false;
            loginBinding.telUnder.setVisibility(View.VISIBLE);
            loginBinding.emailUnder.setVisibility(View.GONE);
            loginBinding.lytEmail.setVisibility(View.GONE);
            loginBinding.lytTel.setVisibility(View.VISIBLE);
            loginBinding.edtTel.requestFocus();
        });
        loginBinding.itemEmail.setOnClickListener(v -> {
            isEmail = true;
            loginBinding.emailUnder.setVisibility(View.VISIBLE);
            loginBinding.telUnder.setVisibility(View.GONE);
            loginBinding.lytEmail.setVisibility(View.VISIBLE);
            loginBinding.lytTel.setVisibility(View.GONE);
            loginBinding.edtEmail.requestFocus();
        });
        loginBinding.toRegister.setOnClickListener(v -> {
            ActivityStackManager.getInstance().startActivity(this, RegisterActivity.class);
        });
        loginBinding.toForget.setOnClickListener(v -> {
            ActivityStackManager.getInstance().startActivity(this, LoginForgetActivity.class);
        });
        loginBinding.loginBtn.setOnClickListener(v -> {
            if (isEmail) {
                if (loginViewModel.toLogin(loginBinding.edtEmail.getText().toString().trim(), loginBinding.edtPassword.getText().toString().trim())) {
                    showMsg("登录成功");
                    ActivityStackManager.getInstance().startActivity(this, MainActivity.class);
                } else {
                    showMsg(loginViewModel.failed);
                }
            } else {
                if (loginViewModel.toLogin(loginBinding.edtTel.getText().toString().trim(), loginBinding.edtPassword.getText().toString().trim())) {
                    showMsg("登录成功");
                    ActivityStackManager.getInstance().startActivity(this, MainActivity.class);
                } else {
                    showMsg(loginViewModel.failed);
                }
            }

        });
    }

    public void navTo(Class<?> cls) {
        ActivityStackManager.getInstance().startActivity(this, cls);
    }

    protected void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}