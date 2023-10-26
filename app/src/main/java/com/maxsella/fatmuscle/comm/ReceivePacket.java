package com.maxsella.fatmuscle.comm;

import java.util.Arrays;

public class ReceivePacket {

    private static final String TAG = "ReceivePacket";
    public static final String TYPE_UNKNOWN = "unknown";
    private byte[] bytes;
    private String type;
    private byte from;
    private double data = -1;
    private int len = -1;
    private short mode; // 模式选择
    private String gbk;

    public void setData(double data) {
        this.data = data;
    }

    public String getGbk() {
        return gbk;
    }

    public void setGbk(String gbk) {
        this.gbk = gbk;
    }

    public ReceivePacket(byte[] bytes) {
        this.bytes = bytes;
        this.from = bytes[0];
        this.len = bytes.length;

        if (from != Protocol.DEVICE_TO_CLIENT) {
            this.type = TYPE_UNKNOWN;
            return;
        }
        byte[] addr = Arrays.copyOfRange(bytes, 1, 3);
        if (Protocol.endWith(addr, Protocol.short2Bytes(Protocol.HEAD))) {
            this.type = TYPE_UNKNOWN;
            this.mode = (short) Protocol.bytesToInt(Arrays.copyOfRange(bytes, len - 2, len));
        } else {
            this.type = TYPE_UNKNOWN;
        }
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static String getTypeUnknown() {
        return TYPE_UNKNOWN;
    }

    public byte getFrom() {
        return from;
    }

    public void setFrom(byte from) {
        this.from = from;
    }

    public double getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public short getMode() {
        return mode;
    }

    public void setMode(short mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "ReceivePacket{" +
                "bytes=" + Arrays.toString(bytes) +
                ", type='" + type + '\'' +
                ", from=" + from +
                ", data=" + data +
                ", len=" + len +
                ", mode=" + mode +
                '}';
    }
}
