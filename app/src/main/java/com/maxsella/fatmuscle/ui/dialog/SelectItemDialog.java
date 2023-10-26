package com.maxsella.fatmuscle.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.maxsella.cw.fatmuscle.databinding.DialogSelectItemBinding;

public class SelectItemDialog extends Dialog {

    private DialogSelectItemBinding selectItemBinding;

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
        selectItemBinding = DialogSelectItemBinding.inflate(LayoutInflater.from(context));
        setContentView(selectItemBinding.getRoot());
        selectItemBinding.fatItem.setOnClickListener(v -> selectItemBinding.lytFat.setVisibility(View.VISIBLE));
        selectItemBinding.muscleItem.setOnClickListener(v -> selectItemBinding.lytFat.setVisibility(View.GONE));
    }

    @Override
    public void dismiss() {
        super.dismiss();
        // 清理资源
        selectItemBinding = null;
    }
}
