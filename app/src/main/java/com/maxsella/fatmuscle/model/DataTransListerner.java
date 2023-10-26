package com.maxsella.fatmuscle.model;

public interface DataTransListerner {
    void onCmdMessage(int i, byte[] bArr, int i2, ProtocolType protocolType);

    void onImageData(byte[] bArr, BitmapMsg state, ProtocolType protocolType);

    public enum ProtocolType {
        OTG,
        BLE
    }
}
