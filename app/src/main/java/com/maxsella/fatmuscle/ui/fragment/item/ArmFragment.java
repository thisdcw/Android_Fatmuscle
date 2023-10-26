package com.maxsella.fatmuscle.ui.fragment.item;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.fatmuscle.common.base.BaseFragment;
import com.maxsella.fatmuscle.viewmodel.ArmViewModel;

public class ArmFragment extends BaseFragment {

    private ArmViewModel mViewModel;

    public static ArmFragment newInstance() {
        return new ArmFragment();
    }

    @Override
    public void initEventAndData() {
        mViewModel = new ViewModelProvider(this).get(ArmViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_arm;
    }

}