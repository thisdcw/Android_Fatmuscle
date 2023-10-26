package com.maxsella.fatmuscle.ui.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.cw.fatmuscle.databinding.ActivityUserManagerBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.ui.adapter.MemberAdapter;
import com.maxsella.fatmuscle.viewmodel.MemberViewModel;

public class UserManagerActivity extends BaseActivity {

    ActivityUserManagerBinding userManagerBinding;

    MemberAdapter memberAdapter = new MemberAdapter();

    MemberViewModel memberViewModel = new MemberViewModel();

    @Override
    protected void initView() {
        userManagerBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_manager);
        userManagerBinding.addUser.setOnClickListener(v -> {
            navToNoFinish(AddMemberActivity.class);
        });
        memberViewModel.members.observe(this, members -> {
            memberAdapter.submitList(members);
            LogUtil.d(members.get(0).toString());
            userManagerBinding.rv.setLayoutManager(new LinearLayoutManager(this));
            userManagerBinding.rv.setAdapter(memberAdapter);
        });
    }

}