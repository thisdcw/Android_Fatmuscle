package com.maxsella.fatmuscle.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.cw.fatmuscle.databinding.DialogSelectUserBinding;

public class SelectUserDialog extends Dialog {

    private DialogSelectUserBinding selectUserBinding;

    public SelectUserDialog(@NonNull Context context) {
        super(context);
        initData(context);
    }

    // 在这个构造函数中绑定XML视图
    public SelectUserDialog(@NonNull Context context, int layoutResId) {
        super(context);
        setContentView(layoutResId);
        initData(context);
    }

    protected SelectUserDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initData(context);
    }
    private void initData(Context context) {
        selectUserBinding = DialogSelectUserBinding.inflate(LayoutInflater.from(context));
        setContentView(selectUserBinding.getRoot());
    }

    @Override
    public void dismiss() {
        super.dismiss();
        // 清理资源
        selectUserBinding = null;
    }
}
