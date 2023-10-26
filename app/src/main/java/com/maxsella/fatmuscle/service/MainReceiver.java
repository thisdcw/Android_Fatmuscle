package com.maxsella.fatmuscle.service;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.List;

public class MainReceiver extends BroadcastReceiver {

    private static final String TAG = "MainReceiver";
    private static final String action_boot = "android.intent.action.BOOT_COMPLETED";

    private Context mContext;

    @Override
    public void onReceive(final Context context, Intent intent) {
        mContext = context;
        //判断收到的是什么广播
        String action = intent.getAction();
        //TODO 这里需要判断设备开启的指令,如果开启,要跳转到工作页面进行工作
    }

    /**
     * 判断应用是否在前台运行
     */
    public static boolean isAppRunning(Context context, String packageName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = activityManager.getRunningTasks(1);
        return list.size() > 0 && list.get(0).topActivity.getClassName().equals(packageName);
    }

    /**
     * 判断应用是否启动
     */
    public boolean isAppAlive(Context context, String packageName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfo = activityManager.getRunningAppProcesses();
        for (int i = 0; i < processInfo.size(); i++) {
            if (processInfo.get(i).processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }
}
