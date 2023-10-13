package com.maxsella.cw.fatmuscle.common;

import android.app.Application;
import android.util.Log;

import org.litepal.LitePal;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import me.jessyan.autosize.AutoSizeConfig;

public class MyApplication extends Application {

    private static MyApplication instance;
    private static final String TAG = "MyApplication";
    BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            if (status == LoaderCallbackInterface.SUCCESS) {
                Log.d("OpenCV", "OpenCV loaded successfully");
            } else {
                Log.e("OpenCV", "OpenCV failed to load. Error code: " + status);
                super.onManagerConnected(status);
            }
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        AutoSizeConfig.getInstance().setExcludeFontScale(true).setUseDeviceSize(true);
        instance = this;
        LitePal.initialize(instance);
        initOpenCV();
    }
    private void initOpenCV() {
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, mLoaderCallback);
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
