package com.maxsella.fatmuscle.sdk.fat.inter;

import com.maxsella.fatmuscle.sdk.fat.entity.BitmapMsg;

public interface DataTransListerner {
    public enum ProtocolType {
        OTG,
        BLE
    }

    void onCmdMessage(int i, byte[] bArr, int i2, ProtocolType protocolType);

    void onImageData(byte[] bArr, BitmapMsg.State state, ProtocolType protocolType);

}
