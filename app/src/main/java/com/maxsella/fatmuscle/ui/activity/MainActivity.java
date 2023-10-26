package com.maxsella.fatmuscle.ui.activity;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maxsella.cw.fatmuscle.R;
import com.maxsella.fatmuscle.common.base.BaseActivity;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.cw.fatmuscle.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding mainBinding;

    @Override
    protected void initView() {
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.setLifecycleOwner(this);
        initEvent();
        LogUtil.d("这是一条测试");
    }

    private void initEvent() {
        LogUtil.d("这是一条测试");
        NavController nav = Navigation.findNavController(this, R.id.nav_host_fragment);
        mainBinding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.survey_fragment) {
                    nav.navigate(R.id.survey_fragment);
                    return true;
                } else if (item.getItemId() == R.id.curve_fragment) {
                    nav.navigate(R.id.curve_fragment);
                    return true;
                } else if (item.getItemId() == R.id.profile_fragment) {
                    nav.navigate(R.id.profile_fragment);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

}