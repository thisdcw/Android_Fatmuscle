package com.maxsella.fatmuscle.common.base;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.azhon.appupdate.manager.DownloadManager;
import com.maxsella.cw.fatmuscle.R;
import com.maxsella.fatmuscle.common.util.ActivityStackManager;
import com.maxsella.fatmuscle.common.util.PermissionUtils;
import com.maxsella.fatmuscle.ui.activity.AccountManageActivity;

public abstract class BaseActivity extends AppCompatActivity {

    private Context mContext;


    protected abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initView();
        ActivityStackManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackManager.getInstance().removeActivity(this);
        mContext = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityStackManager.getInstance().removeActivity(this);
    }

    public Context getmContext() {
        return mContext;
    }

    protected void showMsg(String msg) {
        if (mContext != null) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public void navTo(Class<?> cls) {
        ActivityStackManager.getInstance().startActivity(this, cls);
    }

    public void navToFinishAll(Class<?> cls) {
        ActivityStackManager.getInstance().finishAllAndStart(this, cls);
    }

    public void navToNoFinish(Class<?> cls) {
        ActivityStackManager.getInstance().startActivityNoFinish(this, cls);
    }

    public void navToWithParam(Class<?> cls, Bundle bundle) {
        ActivityStackManager.getInstance().startActivity(this, cls, bundle);
    }

    public void showUpdateDialog() {
        DownloadManager manager = new DownloadManager.Builder(this)
                .apkUrl("")
                .apkName("测试")
                .smallIcon(R.mipmap.ic_launcher)
                .apkVersionCode(2)
                .apkVersionName("1.0.0")
                .apkSize("100kb")
                .apkDescription("测试升级模块")
                .build();
        manager.download();
    }

    /**
     * 当前是否在Android11.0及以上
     */
    protected boolean isAndroid11() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }

    /**
     * 当前是否在Android10.0及以上
     */
    protected boolean isAndroid10() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }

    /**
     * 当前是否在Android7.0及以上
     */
    protected boolean isAndroid7() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }
    @RequiresApi(api = Build.VERSION_CODES.R)
    protected boolean isStorageManager() {
        return Environment.isExternalStorageManager();
    }
    protected void requestPermission(String permissionName) {
        PermissionUtils.getInstance().requestPermission(this, permissionName);
    }

    /**
     * 当前是否在Android6.0及以上
     */
    protected boolean isAndroid6() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
    protected boolean hasPermission(String permissionName) {
        return PermissionUtils.getInstance().hasPermission(this, permissionName);
    }

}
