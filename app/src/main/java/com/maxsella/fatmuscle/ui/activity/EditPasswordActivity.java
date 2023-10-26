package com.maxsella.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.cw.fatmuscle.databinding.ActivityEditPasswordBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;
import com.maxsella.fatmuscle.viewmodel.UserInfoViewModel;

public class EditPasswordActivity extends BaseActivity {

    ActivityEditPasswordBinding editPasswordBinding;

    private UserInfoViewModel userInfoViewModel = new UserInfoViewModel();

    @Override
    protected void initView() {
        editPasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_password);
        editPasswordBinding.btnSubmit.setOnClickListener(v -> {
            if (userInfoViewModel.changePassword(editPasswordBinding.etOld.getText().toString().trim(), editPasswordBinding.etNew.getText().toString().trim(), editPasswordBinding.etNew2.getText().toString().trim())) {
                navToFinishAll(LoginActivity.class);
                showMsg(userInfoViewModel.msg);
            } else {
                showMsg(userInfoViewModel.failed);
            }
        });
    }

}