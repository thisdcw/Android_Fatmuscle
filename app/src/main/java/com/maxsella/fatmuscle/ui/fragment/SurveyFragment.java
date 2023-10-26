package com.maxsella.fatmuscle.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.fatmuscle.common.base.BaseFragment;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.cw.fatmuscle.databinding.FragmentSurveyBinding;
import com.maxsella.fatmuscle.ui.activity.DeviceInfoActivity;
import com.maxsella.fatmuscle.ui.activity.WorkActivity;
import com.maxsella.fatmuscle.ui.dialog.SelectItemDialog;
import com.maxsella.fatmuscle.ui.dialog.SelectUserDialog;
import com.maxsella.fatmuscle.viewmodel.SurveyViewModel;
import com.maxsella.fatmuscle.viewmodel.UserInfoViewModel;

public class SurveyFragment extends BaseFragment {

    private String TAG = "SurveyFragment";

    private SurveyViewModel mViewModel = new SurveyViewModel();

    private FragmentSurveyBinding surveyBinding;

    private UserInfoViewModel userInfoViewModel = new UserInfoViewModel();
    private SelectUserDialog selectUserDialog;
    private SelectItemDialog selectItemDialog;


    public static SurveyFragment newInstance() {
        return new SurveyFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_survey;
    }

    @Override
    public void initEventAndData() {
        surveyBinding = (FragmentSurveyBinding) binding;
        mViewModel = new ViewModelProvider(this).get(SurveyViewModel.class);

        userInfoViewModel.user.observe(this,user -> {
            surveyBinding.setUserViewModel(userInfoViewModel);
        });

        LogUtil.d("这是一条测试");
        initDialog();
        surveyBinding.selectUserBtn.setOnClickListener(v -> {
            selectUserDialog.show();
            Log.d(TAG, "onClick: 点击了selectUserBtn");
        });
        surveyBinding.selectItemBtn.setOnClickListener(v -> {
            selectItemDialog.show();
            Log.d(TAG, "onClick: 点击了selectItemBtn");
        });
        surveyBinding.deviceInfo.setOnClickListener(v -> {
            navigateToActivity(getActivity(), DeviceInfoActivity.class);
        });
        surveyBinding.toWork.setOnClickListener(v->{
            navigateToActivity(getActivity(), WorkActivity.class);
        });
    }

    private void initDialog() {
        selectUserDialog = new SelectUserDialog(requireContext(), R.layout.dialog_select_user);
        selectItemDialog = new SelectItemDialog(requireContext(), R.layout.dialog_select_item);
    }
}