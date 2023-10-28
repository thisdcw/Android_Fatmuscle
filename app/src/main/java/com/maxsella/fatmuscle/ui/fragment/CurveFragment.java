package com.maxsella.fatmuscle.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.common.base.BaseFragment;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.databinding.FragmentCurveBinding;
import com.maxsella.fatmuscle.ui.adapter.CurveFragmentAdapter;
import com.maxsella.fatmuscle.ui.fragment.item.AbdomenFragment;
import com.maxsella.fatmuscle.ui.fragment.item.ArmFragment;
import com.maxsella.fatmuscle.ui.fragment.item.CrusFragment;
import com.maxsella.fatmuscle.ui.fragment.item.ThighFragment;
import com.maxsella.fatmuscle.ui.fragment.item.WaistFragment;
import com.maxsella.fatmuscle.viewmodel.CurveViewModel;

import java.util.ArrayList;
import java.util.List;

public class CurveFragment extends BaseFragment {

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
    public void initEventAndData() {
        curveBinding = (FragmentCurveBinding) binding;
        LogUtil.d("这是一条测试");
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_curve;
    }
}