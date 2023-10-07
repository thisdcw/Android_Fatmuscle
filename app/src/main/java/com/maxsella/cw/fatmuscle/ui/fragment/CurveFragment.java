package com.maxsella.cw.fatmuscle.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.maxsella.cw.fatmuscle.R;
import com.maxsella.cw.fatmuscle.databinding.FragmentCurveBinding;
import com.maxsella.cw.fatmuscle.ui.adapter.CurveFragmentAdapter;
import com.maxsella.cw.fatmuscle.viewmodel.CurveViewModel;

import java.util.ArrayList;
import java.util.List;

public class CurveFragment extends Fragment {

    private CurveViewModel mViewModel;

    private FragmentCurveBinding curveBinding;


    /**
     * 标题数组
     */
    private final String[] titles = {"腰部", "腹部", "上臂", "大腿", "小腿"};
    private final List<Fragment> fragmentList = new ArrayList<>();

    public static CurveFragment newInstance() {
        return new CurveFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        curveBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_curve, container, false);
        return curveBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CurveViewModel.class);
        //添加栏目
        fragmentList.add(new WaistFragment());
        fragmentList.add(new AbdomenFragment());
        fragmentList.add(new ArmFragment());
        fragmentList.add(new ThighFragment());
        fragmentList.add(new CrusFragment());
        curveBinding.vp.setAdapter(new CurveFragmentAdapter(getChildFragmentManager(), fragmentList, titles));
        curveBinding.tab.setupWithViewPager(curveBinding.vp);
    }

}