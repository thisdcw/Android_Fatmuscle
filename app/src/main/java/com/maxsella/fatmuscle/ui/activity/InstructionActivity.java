package com.maxsella.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.databinding.ActivityInstructionBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;

public class InstructionActivity extends BaseActivity {

    private ActivityInstructionBinding instructionBinding;
    @Override
    protected void initView() {
        instructionBinding = DataBindingUtil.setContentView(this,R.layout.activity_instruction);
    }

}