package com.maxsella.fatmuscle.inter;

import com.maxsella.fatmuscle.entity.BlueMsg;

public interface BleReadOrWriteCallback {

    void onDiscoverServicesFail(int i);

    void onReadFail(int i);

    void onReadMessage(BlueMsg blueMsg);

    void onServicesDiscovered(int i);

    void onWriteFail(int i);

    void onWriteSuccess();

}
