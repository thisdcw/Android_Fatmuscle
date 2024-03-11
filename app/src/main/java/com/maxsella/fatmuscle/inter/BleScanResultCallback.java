package com.maxsella.fatmuscle.inter;

import android.bluetooth.BluetoothDevice;

public interface BleScanResultCallback {
    void end();

    void fail(int i, String str);

    void onFindDevice(BluetoothDevice bluetoothDevice, int i, byte[] bArr);

    void start();

}
