package com.maxsella.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.cw.fatmuscle.databinding.ActivityRecordBinding;
import com.maxsella.cw.fatmuscle.databinding.ActivityReportBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;

public class RecordActivity extends BaseActivity {

    ActivityRecordBinding recordBinding;

    @Override
    protected void initView() {
        recordBinding = DataBindingUtil.setContentView(this, R.layout.activity_record);
    }

}