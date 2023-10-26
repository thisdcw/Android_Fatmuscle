package com.maxsella.fatmuscle.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.DataBindingHolder;
import com.maxsella.cw.fatmuscle.databinding.ItemRecordBinding;
import com.maxsella.fatmuscle.db.bean.Record;

public class RecordAdapter extends BaseQuickAdapter<Record, DataBindingHolder<ItemRecordBinding>> {
    @Override
    protected void onBindViewHolder(@NonNull DataBindingHolder<ItemRecordBinding> itemRecordBindingDataBindingHolder, int i, @Nullable Record record) {

    }

    @NonNull
    @Override
    protected DataBindingHolder<ItemRecordBinding> onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        return null;
    }
}
