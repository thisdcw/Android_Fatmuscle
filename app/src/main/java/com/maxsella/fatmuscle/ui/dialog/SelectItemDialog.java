package com.maxsella.fatmuscle.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.maxsella.fatmuscle.common.util.Config;
import com.maxsella.fatmuscle.common.util.Constant;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.databinding.DialogSelectItemBinding;

public class SelectItemDialog extends Dialog {

    private DialogSelectItemBinding binding;

    private String mode = Constant.FAT;
    private String item = Constant.MUSCLE;

    private String selectMode = mode + item;

    public SelectItemDialog(@NonNull Context context) {
        super(context);
        initData(context);
    }

    // 在这个构造函数中绑定XML视图
    public SelectItemDialog(@NonNull Context context, int layoutResId) {
        super(context);
        setContentView(layoutResId);
        initData(context);
    }

    protected SelectItemDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initData(context);
    }

    private void initData(Context context) {
        binding = DialogSelectItemBinding.inflate(LayoutInflater.from(context));
        setContentView(binding.getRoot());
        initClick();
    }

    private void initClick() {
        binding.fatItem.setOnClickListener(v -> {
            binding.lytFat.setVisibility(View.VISIBLE);
            mode = Constant.FAT;
        });
        binding.muscleItem.setOnClickListener(v -> {
            binding.lytFat.setVisibility(View.GONE);
            mode = Constant.MUSCLE;
        });
        binding.waist.setOnClickListener(v -> {
            if (mode.equals(Constant.FAT)) {
                item = Constant.WAIST;
            } else {
                item = Constant.ABDOMEN;
            }
            LogUtil.d("当前选择的是: " + item);
        });
        binding.arm.setOnClickListener(v -> {
            item = Constant.ARM;
            LogUtil.d("当前选择的是: " + item);
        });
        binding.thigh.setOnClickListener(v -> {
            item = Constant.THIGH;
            LogUtil.d("当前选择的是: " + item);
        });
        binding.crus.setOnClickListener(v -> {
            item = Constant.CRUS;
            LogUtil.d("当前选择的是: " + item);
        });
        binding.normal.setOnClickListener(v -> {
            item = Constant.NORMAL;
            LogUtil.d("当前选择的是: " + item);
        });
        binding.confirmBtn.setOnClickListener(v -> {
            selectMode = mode + item;
            LogUtil.d("当前选择的模式是: " + selectMode);
            Config.saveOrUpdateSelectMode(selectMode);
            dismiss();
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        // 清理资源
        binding = null;
    }
}
