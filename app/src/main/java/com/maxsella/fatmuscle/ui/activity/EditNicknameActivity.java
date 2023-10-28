package com.maxsella.fatmuscle.ui.activity;

import androidx.databinding.DataBindingUtil;

import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.databinding.ActivityEditNicknameBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.common.util.TextWatchUtil;
import com.maxsella.fatmuscle.db.bean.User;
import com.maxsella.fatmuscle.viewmodel.UserInfoViewModel;

public class EditNicknameActivity extends BaseActivity {

    private ActivityEditNicknameBinding editNicknameBinding;

    private UserInfoViewModel userInfoViewModel = new UserInfoViewModel();

    private User user1;

    @Override
    protected void initView() {
        editNicknameBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_nickname);
        userInfoViewModel.user.observe(this, user -> {
            user1 = user;
            LogUtil.d("user: " + user);
            editNicknameBinding.setViewModel(userInfoViewModel);
        });
        initClick();
    }

    private void initClick() {
        TextWatchUtil.addTextWatcher(editNicknameBinding.nickname);
        editNicknameBinding.confirmBtn.setOnClickListener(v -> {
            user1.setNickname(editNicknameBinding.nickname.getText().toString().trim());
            userInfoViewModel.updateUser(user1);
            LogUtil.d(user1.toString());
            navTo(UserInfoActivity.class);
        });
    }
}