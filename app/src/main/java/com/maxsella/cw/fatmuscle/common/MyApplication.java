package com.maxsella.cw.fatmuscle.common;

import android.app.Application;

import org.litepal.LitePal;

import me.jessyan.autosize.AutoSizeConfig;

public class MyApplication extends Application {

    private static MyApplication instance;
    private static final String TAG = "MyApplication";


    @Override
    public void onCreate() {
        super.onCreate();
        AutoSizeConfig.getInstance().setExcludeFontScale(true).setUseDeviceSize(true);
        instance = this;

        LitePal.initialize(instance);
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
