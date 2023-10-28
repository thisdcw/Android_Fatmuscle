package com.maxsella.fatmuscle.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.maxsella.fatmuscle.common.util.ActivityStackManager;

public abstract class BaseFragment extends Fragment {

    protected AppCompatActivity context = null;
    protected ViewDataBinding binding = null;

    public abstract void initEventAndData();

    public abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AppCompatActivity) {
            this.context = (AppCompatActivity) context;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (binding != null) {
            initEventAndData();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
        binding = null;
    }

    protected void showMsg(String msg) {
        if (context != null) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    // 封装的跳转方法，接受上下文和目标Activity的类作为参数
    public static void navigateToActivity(Activity activity, Class<?> targetActivityClass) {
        ActivityStackManager.getInstance().startActivityNoFinish(activity, targetActivityClass);
    }

    public static void navigateToActivity(Activity activity, Class<?> targetActivityClass, Bundle bundle) {
        ActivityStackManager.getInstance().startActivityNoFinish(activity, targetActivityClass, bundle);
    }

    public static void navigateToActivityWithFinish(Activity activity, Class<?> targetActivityClass) {
        ActivityStackManager.getInstance().startActivity(activity, targetActivityClass);
    }
}
