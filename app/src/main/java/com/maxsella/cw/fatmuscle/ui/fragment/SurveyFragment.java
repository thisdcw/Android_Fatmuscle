package com.maxsella.cw.fatmuscle.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.cw.fatmuscle.common.base.BaseFragment;
import com.maxsella.cw.fatmuscle.databinding.DialogSelectUserBinding;
import com.maxsella.cw.fatmuscle.databinding.FragmentSurveyBinding;
import com.maxsella.cw.fatmuscle.ui.dialog.SelectItemDialog;
import com.maxsella.cw.fatmuscle.ui.dialog.SelectUserDialog;
import com.maxsella.cw.fatmuscle.viewmodel.SurveyViewModel;

public class SurveyFragment extends BaseFragment {

    private String TAG = "SurveyFragment";

    private SurveyViewModel mViewModel;

    private FragmentSurveyBinding surveyBinding;

    private SelectUserDialog selectUserDialog;
    private SelectItemDialog selectItemDialog;


    public static SurveyFragment newInstance() {
        return new SurveyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        surveyBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_survey, container, false);
        return surveyBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SurveyViewModel.class);
        initDialog();
        surveyBinding.selectUserBtn.setOnClickListener(v -> {
            selectUserDialog.show();
            Log.d(TAG, "onClick: 点击了selectUserBtn");
        });
        surveyBinding.selectItemBtn.setOnClickListener(v -> {
            selectItemDialog.show();
            Log.d(TAG, "onClick: 点击了selectItemBtn");
        });

    }

    private void initDialog() {
        selectUserDialog = new SelectUserDialog(requireContext(), R.layout.dialog_select_user);
        selectItemDialog = new SelectItemDialog(requireContext(), R.layout.dialog_select_item);
    }


}