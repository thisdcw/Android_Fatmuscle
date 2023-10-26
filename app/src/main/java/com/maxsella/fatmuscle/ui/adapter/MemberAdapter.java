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
import com.maxsella.cw.fatmuscle.databinding.ItemMemberBinding;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.db.bean.Member;
import com.maxsella.fatmuscle.ui.activity.EditMemberActivity;

public class MemberAdapter extends BaseQuickAdapter<Member, DataBindingHolder<ItemMemberBinding>> {
    @Override
    protected void onBindViewHolder(@NonNull DataBindingHolder<ItemMemberBinding> itemMemberBindingDataBindingHolder, int i, @Nullable Member member) {
        ItemMemberBinding binding = itemMemberBindingDataBindingHolder.getBinding();
        binding.setMember(member);
        binding.setOnClick(new ClickBinding());
        binding.executePendingBindings();
    }

    @NonNull
    @Override
    protected DataBindingHolder<ItemMemberBinding> onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        ItemMemberBinding binding = ItemMemberBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        return new DataBindingHolder<>(binding);
    }

    public static class ClickBinding {
        public void itemClick(Member member, View view) {
            LogUtil.d("id: " + member.getId());
            Intent intent = new Intent(view.getContext(), EditMemberActivity.class);
            intent.putExtra("id", String.valueOf(member.getId()));
            view.getContext().startActivity(intent);
            ((Activity)view.getContext()).finish();
        }
    }
}
