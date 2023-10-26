package com.maxsella.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.cw.fatmuscle.databinding.ActivityHardwareBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;

public class HardwareActivity extends BaseActivity {

    ActivityHardwareBinding hardwareBinding;

    @Override
    protected void initView() {
        hardwareBinding = DataBindingUtil.setContentView(this, R.layout.activity_hardware);
    }
}