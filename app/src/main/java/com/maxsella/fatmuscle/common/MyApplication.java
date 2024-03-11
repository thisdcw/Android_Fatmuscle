package com.maxsella.fatmuscle.common;

import android.app.Application;
import android.util.Log;

import com.maxsella.fatmuscle.common.util.Config;
import com.maxsella.fatmuscle.common.util.LogUtil;

import org.litepal.LitePal;
//import org.opencv.android.BaseLoaderCallback;
//import org.opencv.android.LoaderCallbackInterface;
//import org.opencv.android.OpenCVLoader;
import org.xutils.x;
import me.jessyan.autosize.AutoSizeConfig;

public class MyApplication extends Application {

    private static MyApplication instance;
    private static final String TAG = "MyApplication";
//    BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
//        @Override
//        public void onManagerConnected(int status) {
//            if (status == LoaderCallbackInterface.SUCCESS) {
//                LogUtil.d("OpenCV loaded successfully");
//            } else {
//                LogUtil.e("OpenCV failed to load. Error code: " + status);
//                super.onManagerConnected(status);
//            }
//        }
//    };


    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        AutoSizeConfig.getInstance().setExcludeFontScale(true).setUseDeviceSize(true);
        instance = this;
        LitePal.initialize(instance);
        Config.mainSP = this.getSharedPreferences("main", 0);
        initOpenCV();
    }

    private void initOpenCV() {
//        if (!OpenCVLoader.initDebug()) {
//            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, mLoaderCallback);
//        } else {
//            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
//        }
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
