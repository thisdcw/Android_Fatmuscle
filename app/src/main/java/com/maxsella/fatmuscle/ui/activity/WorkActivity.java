package com.maxsella.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.cw.fatmuscle.databinding.ActivityWorkBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;

public class WorkActivity extends BaseActivity {

    ActivityWorkBinding workBinding;

    @Override
    protected void initView() {
        workBinding = DataBindingUtil.setContentView(this, R.layout.activity_work);
    }

}