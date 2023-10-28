package com.maxsella.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.common.util.Config;
import com.maxsella.fatmuscle.databinding.ActivityWorkBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;
import com.maxsella.fatmuscle.viewmodel.RecordViewModel;

public class WorkActivity extends BaseActivity {

    private ActivityWorkBinding workBinding;

    private RecordViewModel recordViewModel = new RecordViewModel();

    @Override
    protected void initView() {
        workBinding = DataBindingUtil.setContentView(this, R.layout.activity_work);
        initClick();
    }

    private void initClick() {
        workBinding.imitateData.setOnClickListener(v -> {
            String[] s = Config.getSelectMode().split("_");
            String mode = s[0];
            String item = s[1];
            recordViewModel.imitateAddRecord(item, mode);
        });
    }

}