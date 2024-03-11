package com.maxsella.fatmuscle.entity;

import com.maxsella.fatmuscle.common.util.ByteUtil;

import java.util.Arrays;

public class BlueMsg {
    private byte[] content;
    private int frameNum;
    private int imageState;
    private int msgId;
    protected String head = "23E4";
    private int value = -1;
    private int errorCode = -1;

    public int getFrameNum() {
        return this.frameNum;
    }

    public void setFrameNum(int i) {
        this.frameNum = i;
    }

    public int getImageState() {
        return this.imageState;
    }

    public void setImageState(int i) {
        this.imageState = i;
    }

    public String getHead() {
        return this.head;
    }

    public void setHead(String str) {
        this.head = str;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int i) {
        this.errorCode = i;
    }

    public int getMsgId() {
        return this.msgId;
    }

    public void setMsgId(int i) {
        this.msgId = i;
    }

    public byte[] getContent() {
        return this.content;
    }

    public void setContent(byte[] bArr) {
        this.content = bArr;
    }

    public void setValue(int i) {
        this.value = i;
    }

    public String toString() {
        return "BlueMsg{head='" + this.head + "', msgId=" + Integer.toHexString(this.msgId) + ", value=" + this.value + ", content=" + Arrays.toString(this.content) + ", imageState=" + this.imageState + ", errorCode=" + this.errorCode + '}';
    }

    public byte[] getProtocolBytes() {
        byte[] hexStringToByteArray = ByteUtil.hexStringToByteArray(this.head + String.format("%04x", Integer.valueOf(this.msgId)) + String.format("%04x", 0));
        int i = this.value;
        if (i != -1) {
            try {
                byte[] intToByteArray = ByteUtil.intToByteArray(i);
                hexStringToByteArray = ByteUtil.hexStringToByteArray(this.head + String.format("%04x", Integer.valueOf(this.msgId)) + String.format("%04x", Integer.valueOf(intToByteArray.length)));
                byte[] bArr = new byte[hexStringToByteArray.length + intToByteArray.length + 3];
                System.arraycopy(hexStringToByteArray, 0, bArr, 0, hexStringToByteArray.length);
                System.arraycopy(intToByteArray, 0, bArr, hexStringToByteArray.length, intToByteArray.length);
                System.arraycopy(new byte[]{-2, -2, -2}, 0, bArr, hexStringToByteArray.length + intToByteArray.length, 3);
                return bArr;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            byte[] bArr2 = this.content;
            if (bArr2 != null && bArr2.length > 0) {
                byte[] hexStringToByteArray2 = ByteUtil.hexStringToByteArray(this.head + String.format("%04x", Integer.valueOf(this.msgId)) + String.format("%04x", Integer.valueOf(this.content.length)));
                byte[] bArr3 = new byte[hexStringToByteArray2.length + this.content.length + 3];
                System.arraycopy(hexStringToByteArray2, 0, bArr3, 0, hexStringToByteArray2.length);
                byte[] bArr4 = this.content;
                System.arraycopy(bArr4, 0, bArr3, hexStringToByteArray2.length, bArr4.length);
                System.arraycopy(new byte[]{-2, -2, -2}, 0, bArr3, hexStringToByteArray2.length + this.content.length, 3);
                return bArr3;
            }
        }
        return hexStringToByteArray;
    }
}
