package com.maxsella.fatmuscle.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.databinding.ActivityEditMemberNicknameBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;

public class EditMemberNicknameActivity extends BaseActivity {

    private ActivityEditMemberNicknameBinding binding;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_member_nickname);
        initClick();
    }

    private void initClick() {
        binding.confirmBtn.setOnClickListener(v->{
            Intent returnIntent = new Intent();
            returnIntent.putExtra("nickname",  binding.nickname.getText().toString().trim());
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });
    }

}