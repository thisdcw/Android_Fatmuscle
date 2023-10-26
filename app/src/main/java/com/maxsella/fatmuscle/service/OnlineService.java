package com.maxsella.fatmuscle.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class OnlineService extends Service {

    private boolean isDestroy = false;

    public OnlineService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(() -> {
            while (!isDestroy) {
                //此处可添加需要定时上传的内容
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!isDestroy) {
            isDestroy = true;
        }
        //unDisposable();
    }
}
