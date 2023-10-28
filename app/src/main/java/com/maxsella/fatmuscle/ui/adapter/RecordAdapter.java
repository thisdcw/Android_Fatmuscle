package com.maxsella.fatmuscle.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.DataBindingHolder;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.databinding.ItemRecordBinding;
import com.maxsella.fatmuscle.db.bean.Record;
import com.maxsella.fatmuscle.ui.activity.EditMemberActivity;
import com.maxsella.fatmuscle.ui.activity.RecordDetailActivity;

public class RecordAdapter extends BaseQuickAdapter<Record, DataBindingHolder<ItemRecordBinding>> {
    @Override
    protected void onBindViewHolder(@NonNull DataBindingHolder<ItemRecordBinding> itemRecordBindingDataBindingHolder, int i, @Nullable Record record) {
        ItemRecordBinding binding = itemRecordBindingDataBindingHolder.getBinding();
        binding.setRecord(record);
        binding.setOnClick(new ClickBinding());
        binding.executePendingBindings();
    }

    @NonNull
    @Override
    protected DataBindingHolder<ItemRecordBinding> onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        ItemRecordBinding binding = ItemRecordBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        return new DataBindingHolder<>(binding);
    }

    public static class ClickBinding {
        public void onItemClick(Record record, View view) {
            LogUtil.d("点击记录的Id是: " + record.getId());
            Intent intent = new Intent(view.getContext(), RecordDetailActivity.class);
            intent.putExtra("id", String.valueOf(record.getId()));
            view.getContext().startActivity(intent);
            ((Activity) view.getContext()).finish();
        }
    }
}
