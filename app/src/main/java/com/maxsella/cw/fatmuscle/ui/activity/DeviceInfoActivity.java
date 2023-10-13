package com.maxsella.cw.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.cw.fatmuscle.common.base.BaseActivity;
import com.maxsella.cw.fatmuscle.databinding.ActivityDeviceInfoBinding;

public class DeviceInfoActivity extends BaseActivity {

    private ActivityDeviceInfoBinding deviceInfoBinding;

    @Override
    protected void initView() {
        deviceInfoBinding = DataBindingUtil.setContentView(this,R.layout.activity_device_info);
    }
}