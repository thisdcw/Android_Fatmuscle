package com.maxsella.fatmuscle.common.util;

import androidx.annotation.NonNull;

import com.azhon.appupdate.listener.OnDownloadListener;

import java.io.File;

public abstract class DownloadListenerHelper implements OnDownloadListener {
    @Override
    public void cancel() {

    }

    @Override
    public void done(@NonNull File file) {

    }

    @Override
    public void downloading(int i, int i1) {

    }

    @Override
    public void error(@NonNull Throwable throwable) {

    }

    @Override
    public void start() {

    }
}
