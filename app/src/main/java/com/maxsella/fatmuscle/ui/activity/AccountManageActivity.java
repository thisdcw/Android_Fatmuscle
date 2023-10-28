package com.maxsella.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.databinding.ActivityAccountManageBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;
import com.maxsella.fatmuscle.viewmodel.UserInfoViewModel;

public class AccountManageActivity extends BaseActivity {

    private ActivityAccountManageBinding accountManageBinding;
    private UserInfoViewModel userInfoViewModel = new UserInfoViewModel();

    @Override
    protected void initView() {
        accountManageBinding = DataBindingUtil.setContentView(this, R.layout.activity_account_manage);
        userInfoViewModel.setLifecycleOwner(this);
        userInfoViewModel.user.observe(this, user -> {
            accountManageBinding.setUserViewmodel(userInfoViewModel);
        });
        initClick();
    }

    private void initClick() {
        accountManageBinding.editPassword.setOnClickListener(v -> {
            navToNoFinish(EditPasswordActivity.class);
        });
        accountManageBinding.editEmail.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("email",accountManageBinding.tvEmail.getText().toString().trim());
            navToWithParam(BindEmailActivity.class, bundle);
        });
        accountManageBinding.lltTelephone.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("tel",accountManageBinding.tvTel.getText().toString().trim());
            navToWithParam(EditTelephoneActivity.class, bundle);
        });
        accountManageBinding.btnLogout.setOnClickListener(v -> {
            userInfoViewModel.logout();
            navTo(LoginActivity.class);
        });
        accountManageBinding.clearBtn.setOnClickListener(v->{
            userInfoViewModel.deleteAllUser();
            navTo(LoginActivity.class);
        });
    }

}