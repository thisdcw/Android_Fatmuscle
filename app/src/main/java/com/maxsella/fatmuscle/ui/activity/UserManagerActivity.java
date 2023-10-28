package com.maxsella.fatmuscle.ui.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.databinding.ActivityUserManagerBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.ui.adapter.MemberAdapter;
import com.maxsella.fatmuscle.viewmodel.MemberViewModel;

public class UserManagerActivity extends BaseActivity {

    private ActivityUserManagerBinding userManagerBinding;

    private MemberAdapter memberAdapter = new MemberAdapter();

    private MemberViewModel memberViewModel = new MemberViewModel();

    @Override
    protected void initView() {
        userManagerBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_manager);
        userManagerBinding.addUser.setOnClickListener(v -> {
            navTo(AddMemberActivity.class);
        });
        memberViewModel.members.observe(this, members -> {
            if (members.size()!=0){
                memberAdapter.submitList(members);
                LogUtil.d("members[0]: "+members.get(0).toString());
            }
            userManagerBinding.rv.setLayoutManager(new LinearLayoutManager(this));
            userManagerBinding.rv.setAdapter(memberAdapter);
        });
    }

}