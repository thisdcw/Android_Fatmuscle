package com.maxsella.cw.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.cw.fatmuscle.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {


    private ActivityRegisterBinding registerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        initView();
    }

    private void initView() {
        registerBinding.itemTel.setOnClickListener(v -> {
            registerBinding.telUnder.setVisibility(View.VISIBLE);
            registerBinding.emailUnder.setVisibility(View.GONE);
            registerBinding.lytEmail.setVisibility(View.GONE);
            registerBinding.lytTel.setVisibility(View.VISIBLE);
            registerBinding.fltVerifyCode.setVisibility(View.VISIBLE);
            registerBinding.edtTel.requestFocus();
        });
        registerBinding.itemEmail.setOnClickListener(v -> {
            registerBinding.emailUnder.setVisibility(View.VISIBLE);
            registerBinding.telUnder.setVisibility(View.GONE);
            registerBinding.lytEmail.setVisibility(View.VISIBLE);
            registerBinding.lytTel.setVisibility(View.GONE);
            registerBinding.fltVerifyCode.setVisibility(View.GONE);
            registerBinding.edtEmail.requestFocus();
        });
        registerBinding.toLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        registerBinding.registerBtn.setOnClickListener(v->{
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}