package com.maxsella.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.cw.fatmuscle.databinding.ActivityUseProblemBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;

public class UseProblemActivity extends BaseActivity {

    ActivityUseProblemBinding useProblemBinding;
    @Override
    protected void initView() {
        useProblemBinding = DataBindingUtil.setContentView(this,R.layout.activity_use_problem);
    }

}