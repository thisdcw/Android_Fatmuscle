package com.maxsella.cw.fatmuscle.ui.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.cw.fatmuscle.viewmodel.CrusViewModel;

public class CrusFragment extends Fragment {

    private CrusViewModel mViewModel;

    public static CrusFragment newInstance() {
        return new CrusFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crus, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CrusViewModel.class);
        // TODO: Use the ViewModel
    }

}