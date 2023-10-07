package com.maxsella.cw.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.cw.fatmuscle.databinding.ActivityLoginForgetBinding;

public class LoginForgetActivity extends AppCompatActivity {

    private ActivityLoginForgetBinding forgetBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget);
        forgetBinding = DataBindingUtil.setContentView(this, R.layout.activity_login_forget);
        initView();
    }

    private void initView() {
        forgetBinding.foundItem.setOnClickListener(v -> {
            String text = forgetBinding.foundItem.getText().toString().trim();
            if (text.equals("邮箱找回")) {
                forgetBinding.foundItem.setText("手机找回");
                forgetBinding.lytEmail.setVisibility(View.VISIBLE);
                forgetBinding.lytTel.setVisibility(View.GONE);
                forgetBinding.lytPassword.setVisibility(View.GONE);
                forgetBinding.fltVerifyCode.setVisibility(View.GONE);
            }
            if (text.equals("手机找回")) {
                forgetBinding.foundItem.setText("邮箱找回");
                forgetBinding.lytEmail.setVisibility(View.GONE);
                forgetBinding.lytTel.setVisibility(View.VISIBLE);
                forgetBinding.lytPassword.setVisibility(View.VISIBLE);
                forgetBinding.fltVerifyCode.setVisibility(View.VISIBLE);
            }
        });
        forgetBinding.foundBtn.setOnClickListener(v->{
            Intent intent = new Intent(LoginForgetActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
