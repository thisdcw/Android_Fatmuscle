package com.maxsella.fatmuscle.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.maxsella.fatmuscle.db.helper.UserHelper;

public class Config {

    public static String deviceId = "";
    public static final boolean isDebug = true;
    public static final boolean writeLog = false;

    public static String DEFAULT_MEMBER = "unknown";
    public static final String DEFAULT_MODE = "fat_waist";
    public static SharedPreferences mainSP;
    public static final boolean isProduct = false;

    private static final String name = "sdk_config";
    public static boolean getBoolean(Context context, String str, boolean z) {
        return context.getSharedPreferences(name, 0).getBoolean(str, z);
    }
    public static void saveBoolean(Context context, String str, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(name, 0).edit();
        edit.putBoolean(str, z);
        edit.commit();
    }

    public static int getInt(Context context, String str, int i) {
        return context.getSharedPreferences(name, 0).getInt(str, i);
    }
    public static void saveOrUpdateSelectMode(String selectMode) {
        saveSync(Constant.SELECT_MODE, selectMode);
    }

    public static String getSelectMode() {
        return mainSP.getString(Constant.SELECT_MODE, DEFAULT_MODE);
    }
    public static void saveOrUpdateChooseMember(String nickname) {
        saveSync(Constant.CHOOSE_MEMBER, nickname);
    }

    public static String getChooseMember() {
        return mainSP.getString(Constant.CHOOSE_MEMBER, DEFAULT_MEMBER);
    }

    public static void saveSync(String key, Object value) {
        mainSP.edit().putString(key, String.valueOf(value)).apply();
    }

    public @interface Tag_MAIN_SP {
        String deviceId = "deviceId";
    }
}
