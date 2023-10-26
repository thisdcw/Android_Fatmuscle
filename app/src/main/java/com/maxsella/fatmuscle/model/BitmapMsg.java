package com.maxsella.fatmuscle.model;

import android.graphics.Bitmap;

import androidx.lifecycle.Lifecycle;

import com.maxsella.fatmuscle.common.util.BitmapUtil;

public class BitmapMsg extends DeviceMsg {
    public static final int END = 1;
    public static final int RUN = 0;
    public static final int START = 16;
    private int[] array;
    private Bitmap mBitmap;
    private float mFatThickness;
    private Lifecycle.State mState;


}

