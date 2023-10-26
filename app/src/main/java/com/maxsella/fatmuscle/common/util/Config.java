package com.maxsella.fatmuscle.common.util;

import android.content.SharedPreferences;

public class Config {

    public static String deviceId = "";
    public static final boolean isDebug = true;
    public static final boolean writeLog = false;

    public static SharedPreferences mainSP;

    public @interface Tag_MAIN_SP {
        String deviceId = "deviceId";
    }
}
