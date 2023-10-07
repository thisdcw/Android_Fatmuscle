package com.maxsella.cw.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.cw.fatmuscle.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding loginBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initView();
    }

    private void initView() {
        loginBinding.itemTel.setOnClickListener(v -> {
            loginBinding.telUnder.setVisibility(View.VISIBLE);
            loginBinding.emailUnder.setVisibility(View.GONE);
            loginBinding.lytEmail.setVisibility(View.GONE);
            loginBinding.lytTel.setVisibility(View.VISIBLE);
            loginBinding.edtTel.requestFocus();
        });
        loginBinding.itemEmail.setOnClickListener(v -> {
            loginBinding.emailUnder.setVisibility(View.VISIBLE);
            loginBinding.telUnder.setVisibility(View.GONE);
            loginBinding.lytEmail.setVisibility(View.VISIBLE);
            loginBinding.lytTel.setVisibility(View.GONE);
            loginBinding.edtEmail.requestFocus();
        });
        loginBinding.toRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        loginBinding.toForget.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this, LoginForgetActivity.class);
            startActivity(intent);
        });
        loginBinding.loginBtn.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}