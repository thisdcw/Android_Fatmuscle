package com.maxsella.fatmuscle.ui.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.common.util.Config;
import com.maxsella.fatmuscle.common.util.Constant;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.databinding.ActivityRecordBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;
import com.maxsella.fatmuscle.ui.adapter.RecordAdapter;
import com.maxsella.fatmuscle.view.dialog.SlideDialog;
import com.maxsella.fatmuscle.viewmodel.RecordViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends BaseActivity {

    private ActivityRecordBinding recordBinding;

    private RecordViewModel recordViewModel = new RecordViewModel();

    private RecordAdapter recordAdapter = new RecordAdapter();
    private String item = "";

    @Override
    protected void initView() {
        recordBinding = DataBindingUtil.setContentView(this, R.layout.activity_record);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            item = bundle.getString("item");
            LogUtil.d("获取到item为: " + item);
        }
        recordViewModel.getAllRecordByItem(Config.getChooseMember(), item);
        recordViewModel.records.observe(this, records -> {
            recordAdapter.submitList(records);
        });
        recordBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        recordBinding.rv.setAdapter(recordAdapter);
        recordBinding.order.setOnClickListener(v -> {
            show();
        });
    }

    private void show() {
        List<String> list = new ArrayList<>();
        list.add(Constant.ALL);
        list.add(Constant.ONLY_FAT);
        list.add(Constant.ONLY_MUSCLE);
        SlideDialog slideDialog = new SlideDialog(this, list, false, false);
        slideDialog.setOnSelectClickListener(new SlideDialog.OnSelectListener() {
            @Override
            public void onCancel() {
                showMsg("未选择");
            }

            @Override
            public void onAgree(String txt) {
                LogUtil.d("选择的是: " + txt);
                switch (txt) {
                    case Constant.ONLY_MUSCLE:
                        recordViewModel.getAllRecordByModeItem(Config.getChooseMember(), item,"muscle");
                        break;
                    case Constant.ONLY_FAT:
                        recordViewModel.getAllRecordByModeItem(Config.getChooseMember(), item, "fat");
                        break;
                    case Constant.ALL:
                    default:
                        recordViewModel.getAllRecordByItem(Config.getChooseMember(), item);
                        break;
                }
                recordBinding.executePendingBindings();
            }
        });
        slideDialog.show();

    }

}