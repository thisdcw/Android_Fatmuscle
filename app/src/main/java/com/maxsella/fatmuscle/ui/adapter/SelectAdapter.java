package com.maxsella.fatmuscle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.DataBindingHolder;
import com.maxsella.fatmuscle.databinding.ItemSelectUserBinding;
import com.maxsella.fatmuscle.db.bean.Member;

public class SelectAdapter extends BaseQuickAdapter<Member, DataBindingHolder<ItemSelectUserBinding>> {
    @Override
    protected void onBindViewHolder(@NonNull DataBindingHolder<ItemSelectUserBinding> itemSelectUserBindingDataBindingHolder, int i, @Nullable Member member) {
        ItemSelectUserBinding binding = itemSelectUserBindingDataBindingHolder.getBinding();
        binding.setMember(member);
        binding.executePendingBindings();
    }

    @NonNull
    @Override
    protected DataBindingHolder<ItemSelectUserBinding> onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        ItemSelectUserBinding binding = ItemSelectUserBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        return new DataBindingHolder<>(binding);
    }
}
