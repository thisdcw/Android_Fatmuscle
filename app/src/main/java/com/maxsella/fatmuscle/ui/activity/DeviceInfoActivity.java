package com.maxsella.fatmuscle.ui.activity;

import androidx.databinding.DataBindingUtil;

import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.common.base.BaseActivity;
import com.maxsella.fatmuscle.databinding.ActivityDeviceInfoBinding;
import com.maxsella.fatmuscle.common.util.ActivityStackManager;

public class DeviceInfoActivity extends BaseActivity {

    private ActivityDeviceInfoBinding deviceInfoBinding;

    @Override
    protected void initView() {
        deviceInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_device_info);
        deviceInfoBinding.lltHardware.setOnClickListener(v -> {
            navToNoFinish(HardwareActivity.class);
        });
    }
}