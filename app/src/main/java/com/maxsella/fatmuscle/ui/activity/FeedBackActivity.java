package com.maxsella.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.cw.fatmuscle.databinding.ActivityFeedBackBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;

public class FeedBackActivity extends BaseActivity {

    ActivityFeedBackBinding feedBackBinding;
    @Override
    protected void initView() {
        feedBackBinding = DataBindingUtil.setContentView(this,R.layout.activity_feed_back);
    }
}