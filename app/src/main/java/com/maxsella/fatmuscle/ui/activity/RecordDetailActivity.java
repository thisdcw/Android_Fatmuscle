package com.maxsella.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.cw.fatmuscle.databinding.ActivityRecordDetailBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;

public class RecordDetailActivity extends BaseActivity {

    ActivityRecordDetailBinding detailBinding;

    @Override
    protected void initView() {
        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_record_detail);
    }
}