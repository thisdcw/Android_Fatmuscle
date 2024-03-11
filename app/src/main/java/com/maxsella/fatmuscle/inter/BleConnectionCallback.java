package com.maxsella.fatmuscle.inter;

public interface BleConnectionCallback {
    void onConnectionStateChange(int i, int i2);

    void onFail(int i);

}
