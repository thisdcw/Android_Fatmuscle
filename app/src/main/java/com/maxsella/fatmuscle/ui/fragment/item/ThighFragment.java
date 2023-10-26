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
import com.maxsella.fatmuscle.viewmodel.ThighViewModel;

public class ThighFragment extends BaseFragment {

    private ThighViewModel mViewModel;

    public static ThighFragment newInstance() {
        return new ThighFragment();
    }

    @Override
    public void initEventAndData() {
        mViewModel = new ViewModelProvider(this).get(ThighViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_thigh;
    }

}