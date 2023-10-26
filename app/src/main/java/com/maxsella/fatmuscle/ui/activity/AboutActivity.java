package com.maxsella.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.cw.fatmuscle.databinding.ActivityAboutBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;

public class AboutActivity extends BaseActivity {

    ActivityAboutBinding aboutBinding;

    @Override
    protected void initView() {
        aboutBinding = DataBindingUtil.setContentView(this, R.layout.activity_about);
    }

}