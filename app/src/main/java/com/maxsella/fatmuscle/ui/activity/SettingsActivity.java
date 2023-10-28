package com.maxsella.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.databinding.ActivitySettingsBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;

public class SettingsActivity extends BaseActivity {

    private ActivitySettingsBinding settingsBinding;

    @Override
    protected void initView() {
        settingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        initClick();
    }

    private void initClick() {
        settingsBinding.checkUpdate.setOnClickListener(v -> {
            //检测更新
        });
        settingsBinding.lltAbout.setOnClickListener(v -> {
            //关于我们
            navToNoFinish(AboutActivity.class);
        });
        settingsBinding.lltAccount.setOnClickListener(v -> {
            //账户管理
            navTo(AccountManageActivity.class);
        });
        settingsBinding.lltProfile.setOnClickListener(v -> {
            //个人中心
            navTo(UserInfoActivity.class);
        });
        settingsBinding.lltUnit.setOnClickListener(v -> {
            //单位设置
        });
    }

}