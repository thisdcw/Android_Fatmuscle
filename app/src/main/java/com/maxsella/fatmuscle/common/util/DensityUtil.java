package com.maxsella.fatmuscle.common.util;

import android.content.Context;
import android.util.TypedValue;

import org.xutils.x;

public class DensityUtil {

    private static float density = -1.0f;
    private static int heightPixels = -1;
    private static int widthPixels = -1;

    private DensityUtil() {
    }

    public static float getDensity(Context context) {
        if (density <= 0.0f) {
            density = context.getResources().getDisplayMetrics().density;
        }
        return density;
    }

    public static int dip2px(Context context, float f) {
        return (int) ((f * getDensity(context)) + 0.5f);
    }

    public static int sp2px(Context context, int i) {
        return (int) TypedValue.applyDimension(2, i, context.getResources().getDisplayMetrics());
    }

    public static int px2dip(Context context, float f) {
        return (int) ((f / getDensity(context)) + 0.5f);
    }

    public static int getScreenWidth() {
        if (widthPixels <= 0) {
            widthPixels = x.app().getResources().getDisplayMetrics().widthPixels;
        }
        return widthPixels;
    }

    public static int getScreenHeight() {
        if (heightPixels <= 0) {
            heightPixels = x.app().getResources().getDisplayMetrics().heightPixels;
        }
        return heightPixels;
    }
}
