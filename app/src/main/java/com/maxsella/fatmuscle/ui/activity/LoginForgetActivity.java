package com.maxsella.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.fatmuscle.common.util.ActivityStackManager;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.cw.fatmuscle.databinding.ActivityLoginForgetBinding;
import com.maxsella.fatmuscle.viewmodel.ForgetViewModel;

public class LoginForgetActivity extends AppCompatActivity {

    private ActivityLoginForgetBinding forgetBinding;

    private ForgetViewModel forgetViewModel = new ForgetViewModel();

    private boolean isEmail = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget);
        ActivityStackManager.getInstance().addActivity(this);
        forgetBinding = DataBindingUtil.setContentView(this, R.layout.activity_login_forget);
        initView();
        LogUtil.d("这是一条测试");
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
            if (isEmail){
                if (forgetViewModel.fond(forgetBinding.edtEmail.getText().toString().trim(),forgetBinding.edtPassword.getText().toString().trim(),"")){
                    showMsg("重置密码成功");
                }else {
                   showMsg(forgetViewModel.failed);
                }
            }else{
                if (forgetViewModel.fond(forgetBinding.edtTel.getText().toString().trim(),forgetBinding.edtPassword.getText().toString().trim(),forgetBinding.edtVerifyCode.getText().toString().trim())){
                    showMsg("重置密码成功");
                }else {
                    showMsg(forgetViewModel.failed);
                }
            }
            ActivityStackManager.getInstance().startActivity(this,LoginActivity.class);
        });
        forgetBinding.backBtn.setOnClickListener(v->{
            ActivityStackManager.getInstance().startActivity(this,LoginActivity.class);
        });
        forgetBinding.tvGetVerifyCode.setOnClickListener(v->{
            forgetViewModel.getVerifyCode();
        });
    }
    protected void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
