package com.maxsella.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.databinding.ActivityReportBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;

public class ReportActivity extends BaseActivity {

    private ActivityReportBinding reportBinding;

    @Override
    protected void initView() {
        reportBinding = DataBindingUtil.setContentView(this, R.layout.activity_report);
    }

}