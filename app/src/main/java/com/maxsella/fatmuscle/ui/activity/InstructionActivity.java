package com.maxsella.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.cw.fatmuscle.databinding.ActivityInstructionBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;

public class InstructionActivity extends BaseActivity {

    ActivityInstructionBinding instructionBinding;
    @Override
    protected void initView() {
        instructionBinding = DataBindingUtil.setContentView(this,R.layout.activity_instruction);
    }

}